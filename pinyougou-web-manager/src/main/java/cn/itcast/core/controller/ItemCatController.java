package cn.itcast.core.controller;

import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCat1;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    //根据父ID 查询商品分类结果集
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId){

        return itemCatService.findByParentId(parentId);
    }

    //查询所有
    @RequestMapping("/findAll")
    public List<ItemCat> findAll(){
        return itemCatService.findAll();
    }

    //根据条件查询 分页对象
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows , @RequestBody ItemCat1 itemCat1){
        //运营商后台管理商品的时候 对所有商家进行统一处理

        return  itemCatService.search(page,rows,itemCat1);
    }

    ///开始审核
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status){
        try {
            itemCatService.updateStatus(ids,status);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }

}
