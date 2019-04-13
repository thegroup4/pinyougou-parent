package cn.itcast.core.service;

import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCat1;
import entity.PageResult;

import java.util.List;

public interface ItemCatService {
    List<ItemCat> findByParentId(Long parentId);

    ItemCat findOne(Long id);

    List<ItemCat> findAll();

    PageResult search(Integer page, Integer rows, ItemCat1 itemCat1);

    void updateStatus(Long[] ids, String status);

}
