package com.atzd.cloud.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.atzd.cloud.apis.PayFeignApi;
import com.atzd.cloud.entities.PayDTO;
import com.atzd.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.List;

@RestController
public class OrderController {


    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping("/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){
        ResultData resultData = payFeignApi.addPay(payDTO);
        return resultData;
    }

    @GetMapping("/feign/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        System.out.println("------支付微服务远程调用，按照id查询支付信息");
        ResultData resultData = null;
        try{
            System.out.println("调用开始" + DateUtil.now());
            resultData = payFeignApi.getById(id);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("调用结束" + DateUtil.now());
        }
        return resultData;
    }

    @GetMapping("/feign/pay/mylb")
    public String myLoadBalance(){
        return payFeignApi.myLoadBalance();
    }
}
