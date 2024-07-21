package com.atzd.cloud.controller;

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

    public static final String PaymentSrv_URL = "http://cloud-payment-service";//服务注册中心中微服务的名称

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/consumer/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL+"/pay/add",payDTO,ResultData.class);
    }

    //根据id查询
    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfoById(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL+"/pay/get/"+id, ResultData.class);
    }


    //删除
    @DeleteMapping("/consumer/pay/del/{id}")
    public ResultData deleteById(@PathVariable("id") Integer id){

        restTemplate.delete(PaymentSrv_URL+"/pay/del/"+id, id);
        return ResultData.success("成功删除");
    }

    //修改
    @PutMapping("/consumer/pay/update")
    public ResultData updateById(@RequestBody PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL+"/pay/update", payDTO, ResultData.class);
    }


    @GetMapping(value = "/consumer/pay/get/info")
    public String getInfoByConsul(){
        return restTemplate.getForObject(PaymentSrv_URL+"/pay/get/info", String.class);
    }


    @GetMapping(value = "/consumer/discovery")
    public String discovery(){
        List<String> services = discoveryClient.getServices();
        for (String element: services) {
            System.out.println(element);
        }

        System.out.println("================================");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");

        for(ServiceInstance element : instances){
            System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t"+ element.getPort()+"\t" + element.getUri());

        }

        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();
    }


}
