package com.atzd.cloud.service.impl;

import com.atzd.cloud.apis.AccountFeignApi;
import com.atzd.cloud.apis.StorageFeignApi;
import com.atzd.cloud.entities.Order;
import com.atzd.cloud.mapper.OrderMapper;
import com.atzd.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StorageFeignApi storageFeignApi;
    @Resource
    private AccountFeignApi accountFeignApi;


    @Override
    @GlobalTransactional(name = "zzdd-create-order", rollbackFor = Exception.class)
    public void create(Order order) {

        //xid全局事务id的检查
        String xid = RootContext.getXID();
        //1.新建订单
        log.info("----------开始新建订单："+"\t"+"xid:"+xid);
        order.setStatus(0);
        int result = orderMapper.insertSelective(order);
        Order orderFromDB = null;

        if(result > 0){
            orderFromDB = orderMapper.selectOne(order);
            log.info("新建订单成功， orderFromDB info:" + orderFromDB);
            System.out.println();
            //2.扣减库存
            log.info("-------> 订单微服务开始调用Storage库存，做扣减count");
            storageFeignApi.decrease(orderFromDB.getProductId(), orderFromDB.getCount());
            log.info("-------> 订单微服务结束调用Storage库存，做扣减完成");
            System.out.println();
            //3. 扣减账号余额
            log.info("-------> 订单微服务开始调用Account账号，做扣减money");
            accountFeignApi.decrease(orderFromDB.getUserId(), orderFromDB.getMoney());
            log.info("-------> 订单微服务结束调用Account账号，做扣减完成");
            System.out.println();
            //4. 修改订单状态
            //订单状态status：0：创建中；1：已完结
            log.info("-------> 修改订单状态");
            orderFromDB.setStatus(1);


            Example condition = new Example(Order.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("userId", orderFromDB.getUserId());
            criteria.andEqualTo("status", 0);

            int update = orderMapper.updateByExampleSelective(orderFromDB, condition);
            log.info("-------> 修改订单状态完成"+"\t"+update);
            log.info("-------> orderFromDB info: "+orderFromDB);
        }
        System.out.println();
        log.info("----------结束新建订单："+"\t"+"xid:"+xid);
    }
}
