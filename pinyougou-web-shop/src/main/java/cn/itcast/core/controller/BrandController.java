package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandCheck;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import entity.PageResult;
import entity.Result;

import org.apache.ibatis.annotations.ResultMap;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 品牌管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;


    @RequestMapping("/add")
    public Result save(@RequestBody BrandCheck brandCheck){

        try {
            brandCheck.setBrandStatus("0");
            brandService.save(brandCheck);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"成功");
        }

    }
    @RequestMapping("/search")
    public PageResult search(Integer  pageNo, Integer pageSize, @RequestBody BrandCheck brandCheck) {
        return brandService.searchSeller(pageNo, pageSize, brandCheck);
    }


    //查询所有品牌
    @RequestMapping("/findAll")
    public List<Brand> findAll(){


        return brandService.findAll();

    }
    //查询分页对象
    @RequestMapping("/findPage")
    public PageResult findPage(Integer pageNo, Integer pageSize){
        //总条数  结果集

       return brandService.findPage(pageNo,pageSize);

    }
    //查询分页对象  有条件  页面传递的是id
    /*@RequestMapping("/search")
    public PageResult search(
           // Integer pageNo, Integer pageSize,@RequestBody(required = false) Brand brand){//空指针异常
            Integer pageNo, Integer pageSize, @RequestBody Brand brand){//空指针异常
        //总条数  结果集



       return brandService.search(pageNo,pageSize,brand);

    }*/
    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand){

        try {
            brandService.update(brand);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }
    //修改
    @RequestMapping("/delete")
    public Result delete(Long[] ids){

        try {
            brandService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }

    }
    //查询一个品牌
    @RequestMapping("/findOne")
    public Brand findOne(Long id){
        return brandService.findOne(id);
    }

    //查询所有品牌 返回值 List<Map>
    @RequestMapping("/selectOptionList")

    @Update("update" )
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }
}
