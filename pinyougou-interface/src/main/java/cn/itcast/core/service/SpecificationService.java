package cn.itcast.core.service;

import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationCheck;
import entity.PageResult;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    PageResult search(Integer page, Integer rows, Specification specification);

    void add(SpecificationVo specificationVo);

    SpecificationVo findOne(Long id);

    void update(SpecificationVo specificationVo);

    List<Map> selectOptionList();

    /*
     * 规格审核查询所有并分页查询
     * */
    PageResult searchStatus(Integer page, Integer rows, SpecificationCheck specificationCheck);

    /*
     * 规格查询
     * */
    List<SpecificationCheck> findStatusAll();

    /**
     * 更改状态
     *
     * @param ids
     * @param status
     */
    void updateStatus(Long[] ids, String status);

    void deleteStatus(Long id);

    void addspecification(Specification specification);
}
