package com.atzd.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.atzd.cloud.mapper")
public class SeataAccountService2003 {
    public static void main(String[] args) {
        SpringApplication.run(SeataAccountService2003.class, args);
    }
}
