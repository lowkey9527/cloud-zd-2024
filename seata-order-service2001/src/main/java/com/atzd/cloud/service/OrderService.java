package com.atzd.cloud.service;

import com.atzd.cloud.entities.Order;

public interface OrderService {


    /**
     * 创建订单
     *
     */

    void create(Order order);
}
