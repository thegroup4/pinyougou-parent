package cn.itcast.core.controller;

import cn.itcast.core.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/seller")
@RestController
public class SellerController {
    @Reference
    private SellerService sellerService;

    @RequestMapping("/findCount")
    public int findCount(){
        return sellerService.findCount();
    }
}
