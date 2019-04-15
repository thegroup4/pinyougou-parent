package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandCheck;
import com.github.pagehelper.Page;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService {

    List<Brand> findAll();

    PageResult findPage(Integer pageNo, Integer pageSize);

    void add(Brand brand);

    Brand findOne(Long id);

    void update(Brand brand);

    void delete(Long[] ids);

    PageResult search(Integer pageNo, Integer pageSize, Brand brand);

    List<Map> selectOptionList();

    /*
     * Brand审核
     * */
    List<BrandCheck> findStatusAll();

    PageResult searchStatus(Integer pageNo, Integer pageSize, BrandCheck brandCheck);

    void deleteStatus(Long id);

    void updateStatus(Long[] ids, String status);

    void save(BrandCheck brandCheck);

    PageResult searchSeller(Integer pageNo, Integer pageSize, BrandCheck brandCheck);

}
