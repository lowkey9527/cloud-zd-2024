package com.atzd.cloud.controller;


import cn.hutool.core.util.IdUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class PayCircuitController {


    //Resilience4j CircuitBroker例子  熔断器案例
    @GetMapping("/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id){

        if(id == -4){
            throw new RuntimeException("----circuit id 不能为负数");
        }
        if(id == 9999){
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "Hello circuit! INPUTID:"+id+"\t"+ IdUtil.simpleUUID();
    }

    //bulkhead 限流器
    @GetMapping("/pay/bulkhead/{id}")
    public String myBulkHead(@PathVariable("id") Integer id){
        if(id == -4) throw new RuntimeException("----bulkhead id 不能为负数");

        if(id == 9999){
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return "Hello bulkhead! INPUTID:"+id+"\t"+ IdUtil.simpleUUID();
    }

    @GetMapping("/pay/ratelimit/{id}")
    public String myRateLimit(@PathVariable("id") Integer id){
        return "Hello rateLimit! INPUTID:"+id+"\t"+ IdUtil.simpleUUID();
    }

}
