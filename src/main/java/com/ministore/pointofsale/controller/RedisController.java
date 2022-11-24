package com.ministore.pointofsale.controller;

import com.ministore.pointofsale.dto.RedisDto;
import com.ministore.pointofsale.service.impl.RedisService;
import com.ministore.pointofsale.vo.ResponseHelper;
import com.ministore.pointofsale.vo.ResponseVO;
import com.ministore.pointofsale.vo.ServiceStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/add")
    public ResponseVO<ServiceStatusCode> add(@RequestBody RedisDto redisDto) {
        redisService.set(redisDto.getKey(), redisDto.getValue());
        return ResponseHelper.success();
    }

    @PostMapping("/get")
    public ResponseVO<String> get(@RequestBody RedisDto redisDto) {
        return ResponseHelper.success(redisService.get(redisDto.getKey()));
    }

}
