package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandCheckDao;
import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandCheck;
import cn.itcast.core.pojo.good.BrandCheckQuery;
import cn.itcast.core.pojo.good.BrandQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 品牌管理
 */
@Service
@SuppressWarnings("all")
public class BrandServiceImpl implements BrandService {

    //直接 Controller Service
    @Autowired
    private BrandDao brandDao;

    @Autowired
    private BrandCheckDao brandCheckDao;


    @Override
    public List<Brand> findAll() {
        // 5个字段
        // update tb_tb set name = #{name} where ... 选择性
        // update tb_tb set name = #{name} , age = null, where ... 选择性
        //
        return brandDao.selectByExample(null);
    }

    //查询分页对象
    @Override
    public PageResult findPage(Integer pageNo, Integer pageSize) {

        //Mybatis分页插件
        PageHelper.startPage(pageNo, pageSize);
        Page<Brand> page = (Page<Brand>) brandDao.selectByExample(null);
        //PageInfo<Brand> info = new PageInfo<>(brandList);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //添加
    @Override
    public void add(Brand brand) {
        brandDao.insertSelective(brand);
        //insert into tb_tb (id,name,98个都是null) values (1,haha,null,null 98个null           100个字段
        //insert into tb_tb (id,name) values (1,haha)
    }

    @Override
    public Brand findOne(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    //修改
    @Override
    public void update(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);
    }

    //删除
    @Override
    public void delete(Long[] ids) {
      /*  for (Long id : ids) {
            brandDao.deleteByPrimaryKey(id);
        }*/
        //delete from tb_brand where id = 5;

        BrandQuery brandQuery = new BrandQuery();//入参

        //条件对象中 内部对象  保存条件  放在  where id in (3,5,7,1)
        brandQuery.createCriteria().andIdIn(Arrays.asList(ids));
        brandDao.deleteByExample(brandQuery);

        //delete from tb_brand where id in (3,5,7,1)
    }

    //查询分页对象  有条件
    @Override
    public PageResult search(Integer pageNo, Integer pageSize, Brand brand) {
        //Mybatis分页插件
        PageHelper.startPage(pageNo, pageSize);
        //条件对象
        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();

        //判断品牌中 名称是否为空
        if (null != brand.getName() && !"".equals(brand.getName().trim())) {
            criteria.andNameLike("%" + brand.getName().trim() + "%");
        }
        //首字母
        if (null != brand.getFirstChar() && !"".equals(brand.getFirstChar().trim())) {
            criteria.andFirstCharEqualTo(brand.getFirstChar().trim());
        }

        //分页对象
        Page<Brand> page = (Page<Brand>) brandDao.selectByExample(brandQuery);


        return new PageResult(page.getTotal(), page.getResult());


    }

    //查询所有品牌
    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }

    /*
     *
     * 品牌审核
     * */

    /*
     * 新增
     * */
    @Override
    public List<BrandCheck> findStatusAll() {
        return brandCheckDao.selectByExample(null);
    }

    /**
     * 分页查询并查询所有
     *
     * @param pageNo
     * @param pageSize
     * @param brandCheck
     * @return
     */
    @Override
    public PageResult searchStatus(Integer pageNo, Integer pageSize, BrandCheck brandCheck) {
        //Mybatis分页插件
        PageHelper.startPage(pageNo, pageSize);
        //条件对象
        BrandCheckQuery brandCheckQuery = new BrandCheckQuery();
        BrandCheckQuery.Criteria criteria = brandCheckQuery.createCriteria();

        //判断品牌中 名称是否为空
        if (null != brandCheck.getName() && !"".equals(brandCheck.getName().trim())) {
            criteria.andNameLike("%" + brandCheck.getName().trim() + "%");
        }
        //首字母
        if (null != brandCheck.getFirstChar() && !"".equals(brandCheck.getFirstChar().trim())) {
            criteria.andFirstCharEqualTo(brandCheck.getFirstChar().trim());
        }

        //分页对象
        Page<BrandCheck> page = (Page<BrandCheck>) brandCheckDao.selectByExample(brandCheckQuery);

        return new PageResult(page.getTotal(), page.getResult());

    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void deleteStatus(Long id) {
        if (id != null) {
            brandCheckDao.deleteByPrimaryKey(id);
        }
    }

    /**
     * 修改审核状态
     *
     * @param ids
     * @param status
     */
    @Override
    public void updateStatus(Long[] ids, String status) {
        BrandCheck brandCheck = new BrandCheck();
        brandCheck.setBrandStatus(status);

        for (Long id : ids) {
            brandCheck.setId(id);
            brandCheckDao.updateByPrimaryKeySelective(brandCheck);

        }
    }

    @Override
    public void save(BrandCheck brandCheck) {
        brandCheckDao.insertSelective(brandCheck);
    }

    @Override
    public PageResult searchSeller(Integer pageNo, Integer pageSize, BrandCheck brandCheck) {
        //Mybatis分页插件
        PageHelper.startPage(pageNo, pageSize);
        //条件对象
        BrandCheckQuery brandCheckQuery = new BrandCheckQuery();
        BrandCheckQuery.Criteria criteria = brandCheckQuery.createCriteria();

        criteria.andBrandStatusEqualTo("1");

        //判断品牌中 名称是否为空
        if (null != brandCheck.getName() && !"".equals(brandCheck.getName().trim())) {
            criteria.andNameLike("%" + brandCheck.getName().trim() + "%");
        }
        //首字母
        if (null != brandCheck.getFirstChar() && !"".equals(brandCheck.getFirstChar().trim())) {
            criteria.andFirstCharEqualTo(brandCheck.getFirstChar().trim());
        }

        //分页对象
        Page<BrandCheck> page = (Page<BrandCheck>) brandCheckDao.selectByExample(brandCheckQuery);

        return new PageResult(page.getTotal(), page.getResult());








    }


}
