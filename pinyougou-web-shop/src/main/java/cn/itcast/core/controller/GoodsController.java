package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.service.GoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.GoodsVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Reference
    private GoodsService goodsService;

    //商品添加
    @RequestMapping("/add")
    public Result add(@RequestBody GoodsVo vo){
        try {
            //商家ID
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            vo.getGoods().setSellerId(name);
            goodsService.add(vo);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    //商品修改
    @RequestMapping("/update")
    public Result update(@RequestBody GoodsVo vo){
        try {

            goodsService.update(vo);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    //根据条件查询 分页对象
    @RequestMapping("/search")
    public PageResult search(Integer page,Integer rows ,@RequestBody Goods goods){
        //商家ID
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(name);
        return  goodsService.search(page,rows,goods);
    }
    //查询一个包装对象GoodsVo
    @RequestMapping("/findOne")
    public GoodsVo findOne(Long id){
        return goodsService.findOne(id);
    }


    @RequestMapping("/findGoodsListBySellerId")
    public List<GoodsVo> findGoodsListBySellerId(){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        return goodsService.findGoodsListBySellerId(sellerId);
    }

    @RequestMapping("/findGoodsById")
    public Map findGoodsById(Long goodsId){

        GoodsVo goodsVo = goodsService.findOne(goodsId);
        Map<String, Object> map = new HashMap<>();
        map.put("name",goodsVo.getGoods().getGoodsName());
        map.put("goodsId",goodsVo.getGoods().getId());
        map.put("price",goodsVo.getGoods().getPrice());
        map.put("stockCount",goodsVo.getItemList().size());
        return map;

    }

}
