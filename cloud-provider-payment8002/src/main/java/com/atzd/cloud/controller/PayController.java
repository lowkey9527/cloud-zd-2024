package com.atzd.cloud.controller;

import com.atzd.cloud.entities.Pay;
import com.atzd.cloud.entities.PayDTO;
import com.atzd.cloud.resp.ResultData;
import com.atzd.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pay")
@Tag(name = "支付微服务模块" ,description = "支付CRUD")
public class PayController {

    @Resource
    private PayService payService;

    @PostMapping("/add")
    @Operation(summary = "新增",description = "新增支付流水方法,json串做参数")
    public ResultData addPay(@RequestBody Pay pay){
        System.out.println(pay.toString());
        int rows = payService.add(pay);
        return ResultData.success("成功插入记录，返回"+rows);
    }

    @DeleteMapping("/del/{id}")
    @Operation(summary = "删除",description = "删除支付流水方法")
    public ResultData deletePay(@PathVariable("id") Integer id){
        int i = payService.delete(id);

        return ResultData.success(i);
    }

    @PostMapping("/update")
    @Operation(summary = "修改",description = "修改支付流水方法")
    public ResultData updatePay(@RequestBody PayDTO payDTO){
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);
        int row = payService.update(pay);
        return ResultData.success("成功修改记录， 返回 "+row);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "按照ID查流水",description = "查询支付流水方法")
    public ResultData getById(@PathVariable("id") Integer id){

        Pay pay = payService.getById(id);
        return ResultData.success(pay);
    }


    @GetMapping("/getAllPayRecords")
    @Operation(summary = "查询所有",description = "查询所有支付流水")
    public ResultData getAll(){
        //int i = 1/0;
        List<Pay> payList = payService.getAll();
        return ResultData.success(payList);
    }

    @Value("${server.port}")
    private String port;


    @GetMapping("/get/info")
    public String getInfoByConsul(@Value("${atzd.info}") String atzdInfo){
        return "atzdInfo:" + atzdInfo + "port:" + port;
    }
}
