package com.atzd.cloud.controller;

import com.atzd.cloud.apis.PayFeignApi;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderMicrometerController {

    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping("/feign/micrometer/{id}")
    public String myMicrometer(@PathVariable("id") Integer id){
        return payFeignApi.myMicrometer(id);
    }
}
