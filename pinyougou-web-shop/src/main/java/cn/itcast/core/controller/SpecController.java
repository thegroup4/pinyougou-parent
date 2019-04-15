package cn.itcast.core.controller;

import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationCheck;
import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.SpecificationCheckVo;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@RestController
@RequestMapping("/spec")
public class SpecController {

    @Reference
    private SpecificationService specificationService;

    @RequestMapping("/search")
    public PageResult search(Integer page,Integer rows){
        PageResult search = specificationService.search1(page, rows);
        return search;
    }
    //规格添加
    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationCheckVo specificationCheckVo){
        try {
            specificationService.add1(specificationCheckVo);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
/*   @RequestMapping("/add")
    public Result add(@RequestBody SpecificationCheck specificationCheck){
        try {
            specificationService.add(specificationCheck);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }*/
    //规格修改
    @RequestMapping("/update")
    public Result update1(@RequestBody SpecificationCheckVo specificationCheckVo){
        try {
            specificationService.update1(specificationCheckVo);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    //查询一个规格包装对象  规格  规格选项
    @RequestMapping("/findOne")
    public SpecificationCheckVo findOne(Long id){
        return specificationService.findOne1(id);
    }

    //查询所有规格  返回值 List<Map
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return specificationService.selectOptionList1();
    }

}
