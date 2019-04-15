package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@SuppressWarnings("all")
@RestController
@RequestMapping("/seckillgoods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    @RequestMapping("/search")
    public PageResult findBypage(Integer page, Integer rows, @RequestBody SeckillGoods seckillGoods){

        PageResult search = seckillGoodsService.search(page, rows, seckillGoods);

        List<SeckillGoods> rows1 = search.getRows();

        Map<Long, SeckillGoods> map = new HashMap<>();
        List<SeckillGoods> list=new ArrayList<>();

        for (SeckillGoods goods : rows1) {
            if (!map.containsKey(goods.getGoodsId())){
                map.put(goods.getGoodsId(),goods);
            }
        }
        Collection<SeckillGoods> values = map.values();
        for (SeckillGoods value : values) {
            list.add(value);
        }


        return new PageResult(search.getTotal(),list) ;

    }
    @RequestMapping("/updateStatus")
    public Result updataState(Long[] ids,String status){

        try {
            seckillGoodsService.updateState(ids,status);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"失败");
        }


    }
    @RequestMapping("/findByGooIds")
    public List<SeckillGoods> findByGooIds(Long goodsId){
      List<SeckillGoods> seckillGoods=  seckillGoodsService.findByGoodsId(goodsId);

      return seckillGoods;
    }
}
