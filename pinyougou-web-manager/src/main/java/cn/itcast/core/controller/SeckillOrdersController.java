package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillOrdersService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SuppressWarnings("all")
@RestController
@RequestMapping("/seckillorders")
public class SeckillOrdersController {

    @Reference
    private SeckillOrdersService  seckillOrdersService;

    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody SeckillOrder seckillOrder){


     PageResult pagebean= seckillOrdersService.search(page,rows,seckillOrder);


        return pagebean;
    }
}
