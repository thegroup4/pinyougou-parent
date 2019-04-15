package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单管理
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @Reference
    private OrderService orderService;
    //提交订单
    @RequestMapping("/add")
    public Result add(@RequestBody Order order){
        try {

            //当前登陆人 买家
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            order.setUserId(name);

            orderService.add(order);
            return new Result(true,"提交订单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"提交订单失败");
        }
    }

    @RequestMapping("/sellNum")
    public List<Map> orderStatistics(String startDateStr,String endDateStr){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        SimpleDateFormat startFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat endFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = startFormat.parse(startDateStr);
            Date endDate = endFormat.parse(endDateStr);
            return orderService.orderStatistics(startDate,endDate,name);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //如果出问题
        return null;
    }
}
