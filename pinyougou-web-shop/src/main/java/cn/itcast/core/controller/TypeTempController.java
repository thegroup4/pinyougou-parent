package cn.itcast.core.controller;

import cn.itcast.core.pojo.template.TypeTemplateCheck;
import cn.itcast.core.service.TypeTemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 模板管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/typeTemp")
public class TypeTempController {

    @Reference
    private TypeTemplateService typeTemplateService;
    //查询分页对象 有条件
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody TypeTemplateCheck ttc){

        return typeTemplateService.search1(page,rows,ttc);

    }
    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody TypeTemplateCheck ttc){
        try {
            typeTemplateService.add1(ttc);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    //添加
    @RequestMapping("/update")
    public Result update(@RequestBody TypeTemplateCheck ttc){
        try {
            typeTemplateService.update1(ttc);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    //查询一个模板
    @RequestMapping("/findOne")
    public TypeTemplateCheck findOne(Long id){
        return typeTemplateService.findOne1(id);
    }
    //根据模板ID查询规格List<Map> 每一个Map要有规格选项结果集
    @RequestMapping("/findBySpecList")
    public List<Map> findBySpecList(Long id){
        return typeTemplateService.findBySpecList1(id);
    }


}
