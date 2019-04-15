package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationCheckDao;
import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.specification.*;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

/**
 * 规格管理
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationDao specificationDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Autowired
    private SpecificationCheckDao specificationCheckDao;

    //查询分页 有条件
    @Override
    public PageResult search(Integer page, Integer rows, Specification specification) {
        //插件
        PageHelper.startPage(page, rows);

        Page<Specification> p = (Page<Specification>) specificationDao.selectByExample(null);


        return new PageResult(p.getTotal(), p.getResult());
    }

    //添加
    @Override
    public void add(SpecificationVo specificationVo) {
        //规格表
        specificationDao.insertSelective(specificationVo.getSpecification());

        //规格选项表 多
        List<SpecificationOption> specificationOptionList = specificationVo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            //外键
            specificationOption.setSpecId(specificationVo.getSpecification().getId());
            specificationOptionDao.insertSelective(specificationOption);
        }

    }

    //查询一个规格
    @Override
    public SpecificationVo findOne(Long id) {
        SpecificationVo vo = new SpecificationVo();
        //规格
        vo.setSpecification(specificationDao.selectByPrimaryKey(id));
        //规格选项结果集
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        query.createCriteria().andSpecIdEqualTo(id);
        vo.setSpecificationOptionList(specificationOptionDao.selectByExample(query));
        return vo;
    }

    //修改

    @Override
    public void update(SpecificationVo specificationVo) {
        //规格表 修改
        specificationDao.updateByPrimaryKeySelective(specificationVo.getSpecification());
        //规格选项表
        //1:删除  通过外键 批量删除
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        query.createCriteria().andSpecIdEqualTo(specificationVo.getSpecification().getId());
        specificationOptionDao.deleteByExample(query);
        //2:添加
        List<SpecificationOption> specificationOptionList = specificationVo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            //外键
            specificationOption.setSpecId(specificationVo.getSpecification().getId());
            specificationOptionDao.insertSelective(specificationOption);
        }

    }

    @Override
    public List<Map> selectOptionList() {
        return specificationDao.selectOptionList();
    }

    /**
     * 获取所有specificationCheck并分页条件查询
     *
     * @param page
     * @param rows
     * @param specificationCheck
     * @return
     */
    @Override
    public PageResult searchStatus(Integer page, Integer rows, SpecificationCheck specificationCheck) {
        PageHelper.startPage(page, rows);

        SpecificationCheckQuery specificationCheckQuery = new SpecificationCheckQuery();
        SpecificationCheckQuery.Criteria criteria = specificationCheckQuery.createCriteria();
        if (null != specificationCheck.getSpecName() && !"".equals(specificationCheck.getSpecName().trim())) {
            criteria.andSpecNameLike("%" + specificationCheck.getSpecName().trim() + "%");
        }
        Page<SpecificationCheck> specificationChecks = (Page<SpecificationCheck>) specificationCheckDao.selectByExample(specificationCheckQuery);
        return new PageResult(specificationChecks.getTotal(), specificationChecks.getResult());
    }

    /*
     * 规格审核查询所有
     * */
    @Override
    public List<SpecificationCheck> findStatusAll() {
        return specificationCheckDao.selectByExample(null);
    }


    /*
     * 更改审核状态
     * */
    @Override
    public void updateStatus(Long[] ids, String status) {
        SpecificationCheck check = new SpecificationCheck();
        check.setSpecStatus(status);
        for (Long id : ids) {
            if (id != null) {
                check.setId(id);
                specificationCheckDao.updateByPrimaryKeySelective(check);
            }
        }
    }

    /*
     *删除
     * */
    @Override
    public void deleteStatus(Long id) {
        specificationCheckDao.deleteByPrimaryKey(id);
    }

    /*
     * 添加规格
     * */
    @Override
    public void addspecification(Specification specification) {
        specificationDao.insertSelective(specification);
    }


}
