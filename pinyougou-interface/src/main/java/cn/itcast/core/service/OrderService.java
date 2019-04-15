package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;
import entity.PageResult;

public interface OrderService {
    void add(Order order);

    PageResult search(Integer page, Integer rows, String sellerId, Order order);

    void update(Long id,String  status);

    Order findByOrderId(Long id);
}
