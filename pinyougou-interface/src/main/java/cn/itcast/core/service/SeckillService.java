package cn.itcast.core.service;

import vo.GoodsVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SeckillService {

    List<Map<String, Object>> findSeckillGoodsListBySellerId(String sellerId);

    void add(String sellerId, Date startTime1, Date endTime1, GoodsVo goodsVo);

    Map<String, Object> findSeckillGoodsVo(String sellerId, Long goodsId);
}
