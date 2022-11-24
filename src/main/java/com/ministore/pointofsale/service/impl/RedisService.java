package com.ministore.pointofsale.service.impl;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class RedisService {

    @Value("${com.ministore.pointofsale.service.redis.ttl}")
    private Integer ttl;

    @Value("${com.ministore.pointofsale.service.redis.url}")
    private String url;

    private RedisClient redisClient;

    private StatefulRedisConnection<String, String> statefulRedisConnection;

    @PostConstruct
    private void init() {
        redisClient = RedisClient.create(url);
        statefulRedisConnection = redisClient.connect();
    }

    @PreDestroy
    private void destroy() {
        if(statefulRedisConnection != null) {
            statefulRedisConnection.close();
        }

        if(redisClient != null) {
            redisClient.close();
        }
    }

    public String get(String key) {
        return statefulRedisConnection.sync().get(key);
    }

    public void set(String key, String value) {
        statefulRedisConnection.sync().set(key, value);
    }

}
