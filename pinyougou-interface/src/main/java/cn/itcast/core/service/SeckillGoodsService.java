package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import entity.PageResult;

import java.util.List;

public interface SeckillGoodsService {
    PageResult search(Integer page, Integer rows, SeckillGoods seckillGoods);

    void updateState(Long[] ids, String status);

    List<SeckillGoods> findByGoodsId(Long goodsId);

}
