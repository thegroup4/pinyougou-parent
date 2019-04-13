package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import entity.PageResult;

import java.util.List;

public interface SeckillOrdersService {
   PageResult search(Integer page, Integer rows, SeckillOrder seckillOrder);

}
