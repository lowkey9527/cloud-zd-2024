package com.atzd.cloud.controller;





import com.atzd.cloud.apis.PayFeignApi;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderCircuitController {
    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping("/feign/pay/circuit/{id}")
    @CircuitBreaker(name = "cloud-payment-service", fallbackMethod = "myCircuitFallback")
    public String myCircuitBreaker(@PathVariable("id") Integer id){
        return payFeignApi.myCircuit(id);
    }


    //服务降级之后的兜底方法
    public String myCircuitFallback(Integer id, Throwable t){
        return "myCircuitFallback, 系统繁忙，请稍后再试---";
    }


    @GetMapping("/feign/pay/bulkhead/{id}")
    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "myBulkHeadFallback", type = Bulkhead.Type.SEMAPHORE)
    public String myBulkHead(@PathVariable("id") Integer id){
        return payFeignApi.myBulkHead(id);
    }

    public String myBulkHeadFallback(Integer id, Throwable t){
        return "myBulkheadFallback 超出最大数量限制， 系统繁忙";
    }



    @GetMapping(value = "/feign/pay/ratelimit/{id}")
    @RateLimiter(name = "cloud-payment-service",fallbackMethod = "myRatelimitFallback")
    public String myRateLimit(@PathVariable("id") Integer id) {

        return payFeignApi.myRateLimit(id);
    }
    public String myRatelimitFallback(Integer id,Throwable t)
    {
        return "你被限流了，禁止访问/(ㄒoㄒ)/~~";
    }


}

