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

    PageResult search1(Integer page, Integer rows);
    void add1(SpecificationCheckVo specificationCheckVo);

    void add(SpecificationVo specificationVo);


    SpecificationCheckVo findOne1(Long id);
    SpecificationVo findOne(Long id);

    void update1(SpecificationCheckVo specificationCheckVo);
    void update(SpecificationVo specificationVo);

    List<Map> selectOptionList();
    List<Map> selectOptionList1();


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
