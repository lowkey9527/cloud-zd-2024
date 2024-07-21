package com.atzd.cloud.mygateway;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {

    public static final String BEGIN_VISIT_TIME = "begin_visit_time";

    //利用过滤器 记录访问各个接口的时间
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1. 记录下访问接口的开始时间
        exchange.getAttributes().put(BEGIN_VISIT_TIME, System.currentTimeMillis());

        //2.返回统计结果
        return chain.filter(exchange).then(Mono.fromRunnable( ()->{
            Long begin = exchange.getAttribute(BEGIN_VISIT_TIME);
            if (begin != null){
                log.info("访问接口主机："+ exchange.getRequest().getURI().getHost());
                log.info("访问接口端口: " + exchange.getRequest().getURI().getPort());
                log.info("访问接口URL: " + exchange.getRequest().getURI().getPath());
                log.info("访问接口URL参数: " + exchange.getRequest().getURI().getRawQuery());
                log.info("访问接口时长: " + (System.currentTimeMillis() - begin) + "ms");
                log.info("###################################################");
                System.out.println();
            }
        }));
    }



    //排序， 数字越小优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
