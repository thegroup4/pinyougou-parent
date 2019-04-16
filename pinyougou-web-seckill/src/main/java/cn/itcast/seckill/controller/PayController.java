/*
package cn.itcast.seckill.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.seckill.service.SeckillOrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itcast.core.service.PayService;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.seckill.service.SeckillOrderService;


import entity.Result;
import cn.itcast.common.utils.IdWorker;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private PayService payService;
    @Reference
    private SeckillOrderService seckillOrderService;

    @RequestMapping("/createNative")
    public Map createNative() {
        //1.获取当前登录用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //2.提取秒杀订单（从缓存 ）
        SeckillOrder seckillOrder = seckillOrderService.searchOrderFromRedisByUserId(username);
        //3.调用微信支付接口
        if (seckillOrder != null) {
            return payService.createNative(seckillOrder.getId() + "", (long) (seckillOrder.getMoney().doubleValue() * 100) + "");
        } else {
            return new HashMap<>();
        }
    }

    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no) {
        //1.获取当前登录用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Result result = null;
        int x = 0;
        while (true) {
            //调用查询
            Map<String, String> map = payService.queryPayStatus(out_trade_no);
            if (map == null) {
                result = new Result(false, "支付发生错误");
                break;
            }
            //支付成功
            if (map.get("trade_state").equals("SUCCESS")) {
                result = new Result(true, "支付成功");
                //保存订单
                seckillOrderService.saveOrderFromRedisToDb(username, Long.valueOf(out_trade_no), map.get("transaction_id"));
                break;
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            x++;
            if (x >= 100) {
                result = new Result(false, "二维码超时");
                // 关闭支付
                Map<String, String> payResult = payService.closePay(out_trade_no);
                if (payResult != null && "FAIL".equals(payResult.get("return_code"))) {
                    if ("ORDERPAID".equals(payResult.get("err_code"))) {
                        result = new Result(true, "支付成功");
                        //保存订单
                        seckillOrderService.saveOrderFromRedisToDb(username, Long.valueOf(out_trade_no), map.get("transaction_id"));
                    }
                }
                //删除订单
                if (result.isSuccess() == false) {
                    seckillOrderService.deleteOrderFromRedis(username, Long.valueOf(out_trade_no));
                }
                break;
            }
        }
        return result;
    }
}
*/
