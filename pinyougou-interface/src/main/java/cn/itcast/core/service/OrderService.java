package cn.itcast.core.service;


import cn.itcast.core.pojo.order.Order;
import entity.PageResult;
import vo.OrderVo;

public interface OrderService {
    void add(Order order);
}
