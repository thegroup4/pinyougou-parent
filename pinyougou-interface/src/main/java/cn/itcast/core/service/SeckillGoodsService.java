package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import entity.PageResult;
import entity.Result;

import java.util.List;

public interface SeckillGoodsService {
    PageResult search(Integer page, Integer rows, SeckillGoods seckillGoods);

    void updateState(Long[] ids, String status);

    List<SeckillGoods> findList();

    SeckillGoods findOneFromRedis(Long id);

    Result submitOrder(Long seckillId);
}
