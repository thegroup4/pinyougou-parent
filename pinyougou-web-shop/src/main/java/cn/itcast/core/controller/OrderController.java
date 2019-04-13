package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrderVoService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import entity.PageResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Reference
    private OrderVoService orderVoService;

    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows,@RequestBody(required = false) Order order) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        PageResult pageResult = orderVoService.findPage(page, rows,name);
        //禁止循环引用
        String s = JSON.toJSONString(pageResult, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(s);
        //把json再转化为对象
        //PageResult result = JSON.parseObject(s, PageResult.class);
        return pageResult;

    }

}
