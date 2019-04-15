package cn.itcast.core.controller;

import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationCheck;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateCheck;
import cn.itcast.core.service.TypeTemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 模板管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/typeTemplateStatus")
public class TypeTemplateCheckController {


    @Reference
    private TypeTemplateService typeTemplateService;


    //查询分页对象 有条件
    @RequestMapping("/search")
    public PageResult searchStatus(Integer page, Integer rows, @RequestBody TypeTemplateCheck typeTemplateCheck) {
        List<TypeTemplateCheck> typeTemplateChecks = typeTemplateService.findStatusAll();
        for (TypeTemplateCheck templateCheck : typeTemplateChecks) {
            if (templateCheck.getTemplateStatus().equals(StatusCode.IS_PASS)) {
                TypeTemplate typeTemplate = new TypeTemplate();
                typeTemplate.setId(templateCheck.getId());
                typeTemplate.setName(templateCheck.getName());
                typeTemplate.setBrandIds(templateCheck.getBrandIds());
                typeTemplate.setSpecIds(templateCheck.getSpecIds());
                typeTemplate.setCustomAttributeItems(templateCheck.getCustomAttributeItems());
//                删除
                typeTemplateService.deleteStatus(templateCheck.getId());
//                新增
                typeTemplateService.addspecification(typeTemplate);
            }
        }


        return typeTemplateService.searchStatus(page, rows, typeTemplateCheck);

    }

    ///开始审核
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            typeTemplateService.updateStatus(ids, status);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }
}
