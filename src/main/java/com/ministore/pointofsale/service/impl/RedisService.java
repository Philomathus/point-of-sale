package com.ministore.pointofsale.service.impl;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Service
public class RedisService {

    @Value("${com.ministore.pointofsale.service.redis.url}")
    private String url;

    private RedisClient redisClient;

    private StatefulRedisConnection<String, String> statefulRedisConnection;

    private RedissonClient redisson;

    @PostConstruct
    private void init() {
        redisClient = RedisClient.create(url);
        statefulRedisConnection = redisClient.connect();
        Config config = new Config();
        config.useSingleServer().setAddress(url);
        redisson = Redisson.create(config);
    }

    @PreDestroy
    private void destroy() {
        if(statefulRedisConnection != null) {
            statefulRedisConnection.close();
        }

        if(redisClient != null) {
            redisClient.shutdown();
        }

        if(redisson != null) {
            redisson.shutdown();
        }
    }

    public RedisCommands<String, String> opsForValue() {
        return statefulRedisConnection.sync();
    }

    public void expire(String key, long keyExpireSeconds) {
        opsForValue().expire(key, keyExpireSeconds);
    }

    public void del(String key) {
        opsForValue().del(key);
    }

    public void set(String key, String value) {
        opsForValue().set(key, value);
    }

    public String get(String key) {
        return opsForValue().get(key);
    }

    public void set(String key, String value, long keyExpireSeconds) {
        set(key, value);
        expire(key, keyExpireSeconds);
    }

    public boolean setIfAbsent(String key, String value) {
        return opsForValue().setnx(key, value);
    }

    public boolean setIfAbsent(String key, String value, long keyExpireSeconds) {
        if(setIfAbsent(key, value)) {
            expire(key, keyExpireSeconds);
            return true;
        }

        return false;
    }

    public RedissonClient getRedisson() {
        return redisson;
    }

    private static final String listKey = "thread_queue";

    public void lock(String key, long keyExpireSeconds, long retrySeconds, long acquireLockMaxSeconds) throws InterruptedException, TimeoutException {

        final long startTimeMillis = System.currentTimeMillis();

        final String threadName = Thread.currentThread().getName();
        statefulRedisConnection.sync().rpush(listKey, threadName);

        while(Objects.equals(statefulRedisConnection.sync().lindex(listKey, 0), threadName) && !setIfAbsent(key, key, keyExpireSeconds)) {

            Thread.sleep(retrySeconds);

            if(System.currentTimeMillis() - startTimeMillis > acquireLockMaxSeconds * 1000) {
                throw new TimeoutException("Failed to acquire lock in " + acquireLockMaxSeconds + " seconds");
            }
        }
    }

    public void unlock(String key) {
        del(key);
        statefulRedisConnection.sync().lpop(listKey);
    }
}
