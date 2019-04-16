package cn.itcast.core.controller;

import cn.itcast.core.service.SeckillOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

    @Reference
    private SeckillOrderService seckillOrderService;


    @RequestMapping("findSeckillOrderByUserId")
    public List<Map<String, Object>> findSeckillOrderByUserId(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return seckillOrderService.findSckillOrderListByUserId(userId);

    }


    @RequestMapping("cancelSeckillOrder")
    public Result cancelSeckillOrder(Long id){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        seckillOrderService.cancelSeckillOrder(userId,id);
        return new Result(true,"取消成功");


    }


    @RequestMapping("findOrderCreateTimeById")
    public Date findOrderCreateTimeById(Long id){
        return seckillOrderService.findOrderCreateTimeById(id);



    }

}
