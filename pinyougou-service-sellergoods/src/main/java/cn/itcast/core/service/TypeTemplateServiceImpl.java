package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.dao.template.TypeTemplateCheckDao;
import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateCheck;
import cn.itcast.core.pojo.template.TypeTemplateCheckQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 模板管理
 *
 * @Transactional : 单机版事务  分布式事务  基于Mysql或是oracle 毫无意义
 * Spring事务？  Mysql的事务  必须有事务  begin transation   Sql Mysql不执行或执行也不显示数据  commit rollback
 */
@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Autowired
    private TypeTemplateCheckDao typeTemplateCheckDao;

    @Autowired
    private RedisTemplate redisTemplate;

    //查询分页对象
    @Override
    public PageResult search1(Integer page, Integer rows, TypeTemplateCheck ttc) {

        //查询的所有模板结果集
/*        List<TypeTemplateCheck> typeTemplates = typeTemplateCheckDao.selectByExample(null);
        for (TypeTemplate typeTemplate : typeTemplates) {


            //品牌结果集字符串
            // [{"id":1,"text":"联想"},{"id":3,"text":"三星"},{"id":9,"text":"苹果"},{"id":4,"text":"小米"}]
            List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
            redisTemplate.boundHashOps("brandList").put(typeTemplate.getId(), brandList);


            List<Map> specList = findBySpecList(typeTemplate.getId());
            redisTemplate.boundHashOps("specList").put(typeTemplate.getId(), specList);
        }*/
        PageHelper.startPage(page, rows);
        Page<TypeTemplate> p = (Page<TypeTemplate>) typeTemplateDao.selectByExample(null);
        return new PageResult(p.getTotal(), p.getResult());
    }


    //添加
    @Override
    public void add1(TypeTemplateCheck ttc) {
        ttc.setTemplateStatus("0");
        typeTemplateCheckDao.insertSelective(ttc);
    }

    //查询一个模板
    @Override
    public TypeTemplateCheck findOne1(Long id) {
        return typeTemplateCheckDao.selectByPrimaryKey(id);
    }

    //修改
    @Override
    public void update1(TypeTemplateCheck ttc) {
        typeTemplateCheckDao.updateByPrimaryKeySelective(ttc);
    }


    ////根据模板ID查询规格List<Map> 每一个Map要有规格选项结果集
    @Override
    public List<Map> findBySpecList1(Long id) {

        //模板对象
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        // [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        String specIds = typeTemplate.getSpecIds();
        //转成List<Map>
        List<Map> listMap = JSON.parseArray(specIds, Map.class);
        for (Map map : listMap) {
            SpecificationOptionQuery query = new SpecificationOptionQuery();
            query.createCriteria().andSpecIdEqualTo(Long.parseLong((String.valueOf(map.get("id")))));
            //报错：Object --> Integer String  基本类型 --> Long特殊类型 长整
            List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(query);
            map.put("options", specificationOptions);
        }
        return listMap;
    }
    /**
     * 商品审核查询分页条件查询
     *
     * @param page
     * @param rows
     * @param typeTemplateCheck
     * @return
     */
    @Override
    public PageResult searchStatus(Integer page, Integer rows, TypeTemplateCheck typeTemplateCheck) {
        PageHelper.startPage(page, rows);
        TypeTemplateCheckQuery typeTemplateCheckQuery = new TypeTemplateCheckQuery();
        TypeTemplateCheckQuery.Criteria criteria = typeTemplateCheckQuery.createCriteria();
        if (null != typeTemplateCheck.getName() && !"".equals(typeTemplateCheck.getName().trim())) {
            criteria.andNameLike("%" + typeTemplateCheck.getName().trim() + "%");
        }
        Page<TypeTemplateCheck> typeTemplateChecks = (Page<TypeTemplateCheck>) typeTemplateCheckDao.selectByExample(typeTemplateCheckQuery);
        return new PageResult(typeTemplateChecks.getTotal(), typeTemplateChecks.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        TypeTemplateCheck typeTemplateCheck = new TypeTemplateCheck();
        typeTemplateCheck.setTemplateStatus(status);
        for (Long id : ids) {
            typeTemplateCheck.setId(id);
            typeTemplateCheckDao.updateByPrimaryKeySelective(typeTemplateCheck);
        }
    }

    @Override
    public List<TypeTemplateCheck> findStatusAll() {
        return typeTemplateCheckDao.selectByExample(null);
    }

    @Override
    public void deleteStatus(Long id) {
        if (id != null) {
            typeTemplateCheckDao.deleteByPrimaryKey(id);
        }
    }

    @Override
    public void addspecification(TypeTemplate typeTemplate) {
        typeTemplateDao.insertSelective(typeTemplate);
    }

    //查询分页对象
    @Override
    public PageResult search(Integer page, Integer rows, TypeTemplate tt) {
        //查询的所有模版结果集
        List<TypeTemplate> typeTemplates = typeTemplateDao.selectByExample(null);
        for (TypeTemplate typeTemplate : typeTemplates) {

            //品牌结果集字符串
            List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
            redisTemplate.boundHashOps("brandList").put(typeTemplate.getId(),brandList);

            //规格结果集字符串
            List<Map> specList = findBySpecList(typeTemplate.getId());
            redisTemplate.boundHashOps("specList").put(typeTemplate.getId(),specList);


        }

        PageHelper.startPage(page,rows);
        Page<TypeTemplate> p = (Page<TypeTemplate>) typeTemplateDao.selectByExample(null);
        return new PageResult(p.getTotal(),p.getResult());
    }

    //添加
    @Override
    public void add(TypeTemplate tt) {
        typeTemplateDao.insertSelective(tt);
    }

    //查询一个模板
    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateDao.selectByPrimaryKey(id);
    }

    //修改
    @Override
    public void update(TypeTemplate tt) {
        typeTemplateDao.updateByPrimaryKeySelective(tt);
    }

    @Override
    public List<Map> findBySpecList(Long id) {
        //根据模版id查出来一个模版对象
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        //下方查询出来的结果[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        String specIds = typeTemplate.getSpecIds();
        //转成list<map>
        List<Map> maps = JSON.parseArray(specIds, Map.class);
        for (Map map : maps) {

            SpecificationOptionQuery query = new SpecificationOptionQuery();
            query.createCriteria().andSpecIdEqualTo(Long.parseLong(String.valueOf(map.get("id"))));

            List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(query);

            map.put("options",specificationOptions);
        }
        return maps;
    }

}
