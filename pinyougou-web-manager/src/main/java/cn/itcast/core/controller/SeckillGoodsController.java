package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")
@RestController
@RequestMapping("/seckillgoods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    @RequestMapping("/search")
    public PageResult findBypage(Integer page, Integer rows, @RequestBody SeckillGoods seckillGoods){

      return  seckillGoodsService.search(page,rows,seckillGoods);

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
}
