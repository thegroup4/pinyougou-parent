package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SuppressWarnings("all")
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

//    $http.get('seckillGoods/findList.do');
    @RequestMapping("findList")
    public List<SeckillGoods> findList(){
        return seckillGoodsService.findList();
    }
//    $http.get('seckillGoods/findOneFromRedis.do?id='+id);
    @RequestMapping("findOneFromRedis")
    public SeckillGoods findOneFromRedis(Long id){
        return seckillGoodsService.findOneFromRedis(id);
    }
//    $http.get('seckillOrder/submitOrder.do?seckillId='+seckillId);
    @RequestMapping("submitOrder")
    public Result submitOrder(Long seckillId){
        return seckillGoodsService.submitOrder(seckillId);
    }


}
