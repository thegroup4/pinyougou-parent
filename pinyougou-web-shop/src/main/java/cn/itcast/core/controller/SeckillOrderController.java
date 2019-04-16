package cn.itcast.core.controller;

import cn.itcast.core.service.SeckillOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

    @Reference
    private SeckillOrderService seckillOrderService;
    //查询个人用户的所有秒杀订单
    @RequestMapping("findSckillOrderListBySellerId")
    public List<Map> findSckillOrderListBySellerId(){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Map> list = seckillOrderService.findSckillOrderListBySellerId(sellerId);
        return list;
    }
    //取消订单
//    @RequestMapping()
//    public Result cancelSeckillOrder(Long id){
//        try {
//            seckillOrderService.cancelSeckillOrder(userId, id);
//            return new Result(true,"取消成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Result(false,"操作失败");
//        }
//    }


}
