package cn.itcast.core.controller;

import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ContentService;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 广告管理
 */
@RestController
@RequestMapping("/content")
public class ContentController {


    @Reference
    private ContentService contentService;
    @Reference
    private ItemCatService itemCatService;


    //根据广告类型的ID 查询此类型下所有广告结果集
    @RequestMapping("/findByCategoryId")
    public List<Content> findByCategoryId(Long categoryId){
        return contentService.findByCategoryId(categoryId);
    }

    @RequestMapping("/findAllItemCatList")
    public List<ItemCat> findAllItemCatList() {
        return itemCatService.findAllItemCatList();
    }
}
