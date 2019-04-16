package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.core.pojo.order.Order;
import com.ctc.wstx.util.DataUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {
    void add(Order order);

    List<Map> orderStatistics(Date startDate, Date endDate,String name);
}
