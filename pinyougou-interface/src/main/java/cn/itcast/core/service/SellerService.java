package cn.itcast.core.service;

import cn.itcast.core.pojo.seller.Seller;

public interface SellerService {
    void add(Seller seller);

    Seller findBySellerId(String username);


    /**
     * 查询商家总数
     */
    int findCount();

}
