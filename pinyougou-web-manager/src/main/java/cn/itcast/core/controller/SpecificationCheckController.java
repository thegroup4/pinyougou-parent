package cn.itcast.core.controller;

import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationCheck;
import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

/**
 * 规格管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/specificationStatus")
public class SpecificationCheckController {


    @Reference
    private SpecificationService specificationService;

    /**
     * 规格审核
     *
     * @param page
     * @param rows
     * @param specification
     * @return
     */
    @RequestMapping("/search")
    public PageResult searchStatus(Integer page, Integer rows, @RequestBody SpecificationCheck specificationCheck) {
//                获取到specificationCheck所有的信息
        List<SpecificationCheck> specificationChecks = specificationService.findStatusAll();
        for (SpecificationCheck check : specificationChecks) {
            if (check.getSpecStatus().equals(StatusCode.IS_PASS)) {
                Specification specification = new Specification();
                specification.setId(check.getId());
                specification.setSpecName(check.getSpecName());
//                删除
                specificationService.deleteStatus(check.getId());
//                新增
                specificationService.addspecification(specification);
            }
        }
        return specificationService.searchStatus(page, rows, specificationCheck);
    }

    ///开始审核
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            specificationService.updateStatus(ids, status);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }
}
