package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows , @RequestBody Seller seller){
        //运营商后台管理商品的时候 对所有商家进行统一处理

        return  sellerService.search(page,rows,seller);
    }

    @RequestMapping("/findOne")
    public Seller findOne(String sellerId){
        return sellerService.findBySellerId(sellerId);
    }
}
