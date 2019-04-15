package cn.itcast.core.service;

import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationCheck;
import entity.PageResult;
import vo.SpecificationCheckVo;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    PageResult search(Integer page, Integer rows, Specification specification);

    PageResult search(Integer page, Integer rows);

    void add(SpecificationCheckVo specificationCheckVo);




    SpecificationCheckVo findOne(Long id);

    void update(SpecificationCheckVo specificationCheckVo);

    List<Map> selectOptionList();


}
