package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @RequestMapping("/search.do")
    public PageResult search(Integer page , Integer rows, @RequestBody Order order){
        //1.将商户的用户名 也就是 sellerId 获取到
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();


        //2.返回该商户下所有的订单信息
        PageResult pageResult = orderService.search(page,rows,sellerId,order);

        return pageResult;

    }

    @RequestMapping("/updateStatus.do")
    public Result update(Long[] ids,String status){

        try {


            for (Long id : ids) {
               Order order= orderService.findByOrderId(id);
               if (!order.getStatus().equals("1")){
                   return new Result(false,"未支付不可发货");
               }
                orderService.update(id,status);
            }

            return new Result(true,"发货成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"发货失败");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果出问题
        return null;
    }



}
