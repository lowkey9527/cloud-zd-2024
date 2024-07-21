package com.atzd.cloud.apis;



import cn.hutool.core.util.IdUtil;
import com.atzd.cloud.entities.PayDTO;
import com.atzd.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(value = "cloud-payment-service")
@FeignClient(value = "cloud-gateway")
public interface PayFeignApi {


    @PostMapping("/pay/add")
    public ResultData addPay(@RequestBody PayDTO payDTO);


    @GetMapping("/pay/get/{id}")
    public ResultData getById(@PathVariable("id") Integer id);


    @GetMapping("/pay/get/info")
    public String myLoadBalance();


    @GetMapping("/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id);


    //bulkhead
    @GetMapping("/pay/bulkhead/{id}")
    public String myBulkHead(@PathVariable("id") Integer id);


    @GetMapping("/pay/ratelimit/{id}")
    public String myRateLimit(@PathVariable("id") Integer id);

    @GetMapping(value = "/pay/micrometer/{id}")
    public String myMicrometer(@PathVariable("id") Integer id);


    @GetMapping(value = "/pay/gateway/get/{id}")
    public ResultData getByIdGateWay(@PathVariable("id") Integer id);

    @GetMapping(value = "/pay/gateway/info")
    public ResultData<String> getGatewayInfo();

}
