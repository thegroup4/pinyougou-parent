package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationCheckDao;
import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationCheck;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vo.SpecificationCheckVo;
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
        PageHelper.startPage(page,rows);

        Page<Specification> p = (Page<Specification>) specificationDao.selectByExample(null);


        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public PageResult search(Integer page, Integer rows) {
        //插件
        PageHelper.startPage(page,rows);

        Page<SpecificationCheck> p = (Page<SpecificationCheck>) specificationCheckDao.selectByExample(null);


        return new PageResult(p.getTotal(),p.getResult());
    }

 /*   @Override
    public void add(SpecificationCheck specificationCheck) {
        specificationCheck.setSpecStatus("0");
        specificationCheckDao.insertSelective(specificationCheck);
    }
*/
    //添加
    @Override
    public void add(SpecificationCheckVo specificationCheckVo) {

        //规格表
        specificationCheckVo.getSpecificationCheck().setSpecStatus("0");
        specificationCheckDao.insertSelective(specificationCheckVo.getSpecificationCheck());


        //规格选项表 多
        List<SpecificationOption> specificationOptionList = specificationCheckVo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            //外键
            specificationOption.setSpecId(specificationCheckVo.getSpecificationCheck().getId());
            specificationOptionDao.insertSelective(specificationOption);
        }
    }

    //查询一个规格
    @Override
    public SpecificationCheckVo findOne(Long id) {
        SpecificationCheckVo cvo = new SpecificationCheckVo();
        //规格
        cvo.setSpecificationCheck(specificationCheckDao.selectByPrimaryKey(id));
        //规格选项结果集
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        query.createCriteria().andSpecIdEqualTo(id);
        cvo.setSpecificationOptionList(specificationOptionDao.selectByExample(query));
        return cvo;
    }

    //修改

    @Override
    public void update(SpecificationCheckVo specificationCheckVo) {
        //规格表 修改
        specificationCheckDao.updateByPrimaryKeySelective(specificationCheckVo.getSpecificationCheck());
        //规格选项表
        //1:删除  通过外键 批量删除
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        query.createCriteria().andSpecIdEqualTo(specificationCheckVo.getSpecificationCheck().getId());
        specificationOptionDao.deleteByExample(query);
        //2:添加
        List<SpecificationOption> specificationOptionList = specificationCheckVo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            //外键
            specificationOption.setSpecId(specificationCheckVo.getSpecificationCheck().getId());
            specificationOptionDao.insertSelective(specificationOption);
        }

    }

    @Override
    public List<Map> selectOptionList() {
        return specificationDao.selectOptionList();
    }


}
