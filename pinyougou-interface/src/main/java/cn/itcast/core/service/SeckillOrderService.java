package cn.itcast.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SeckillOrderService {
    List<Map> findSckillOrderListBySellerId(String sellerId);

    List<Map<String, Object>> findSckillOrderListByUserId(String userId);

    void cancelSeckillOrder(String userId, Long id);

    Date findOrderCreateTimeById(Long id);
}
