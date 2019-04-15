package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandCheck;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.service.BrandService;
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
 * 品牌审核
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/brandStatus")
public class BrandCheckController {


    @Reference
    private BrandService brandService;

    /*
     * 审核
     * */
    @RequestMapping("/search")
    public PageResult searchStatus(Integer pageNo, Integer pageSize, @RequestBody BrandCheck brandCheck) {//空指针异常
//        获取到brandcheck所有的信息
        List<BrandCheck> list = brandService.findStatusAll();
        for (BrandCheck brandStatus : list) {
//            商品通过审核
            if (brandStatus.getBrandStatus().equals(StatusCode.IS_PASS)) {
//        将获取到的brandcheck添加到brand中
                Brand brand = new Brand();
                brand.setId(brandStatus.getId());
                brand.setName(brandStatus.getName());
                brand.setFirstChar(brandStatus.getFirstChar());
//        先删除再添加
                brandService.deleteStatus(brandStatus.getId());
                brandService.add(brand);
            }
        }
        return brandService.searchStatus(pageNo, pageSize, brandCheck);
    }


    ///开始审核
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            brandService.updateStatus(ids, status);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }
}
