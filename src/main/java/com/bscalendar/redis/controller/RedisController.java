
package com.bscalendar.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bscalendar.fcm.service.FcmPushService;
import com.bscalendar.redis.service.RedisService;

@RestController
@RequestMapping("/redis-test")
public class RedisController {

    @Autowired
    private RedisService redisService;
    
    @Autowired
    private FcmPushService fcmPushService;

    // http://localhost:9090/redis-test/set?key=mykey&value=hello
    @GetMapping("/set")
    public String setRedisData(@RequestParam String key, @RequestParam String value) {
        redisService.saveData(key, value);
        return "Redis 저장 완료 (Key: " + key + ", Value: " + value + ")";
    }

    // http://localhost:9090/redis-test/get?key=mykey
    @GetMapping("/get")
    public String getRedisData(@RequestParam String key) {
        String value = redisService.getData(key);
        return "Redis 조회 결과 (Key: " + key + ") => " + value;
    }
}

//https://headf1rst.github.io/TIL/push-notification 블로그 주소
