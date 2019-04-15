package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.core.pojo.order.Order;
import entity.PageResult;
import com.ctc.wstx.util.DataUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {
    void add(Order order);

    PageResult search(Integer page, Integer rows, String sellerId, Order order);

    void update(Long id,String  status);

    Order findByOrderId(Long id);

    List<Map> orderStatistics(Date startDate, Date endDate,String name);
}
