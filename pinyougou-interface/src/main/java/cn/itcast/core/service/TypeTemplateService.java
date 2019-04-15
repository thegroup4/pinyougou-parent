package cn.itcast.core.service;

import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateCheck;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
    PageResult search(Integer page, Integer rows, TypeTemplate tt);

    void add(TypeTemplate tt);

    TypeTemplate findOne(Long id);

    void update(TypeTemplate tt);

    List<Map> findBySpecList(Long id);
    List<Map> findBySpecList1(Long id);

    PageResult search1(Integer page, Integer rows, TypeTemplateCheck ttc);

    void add1(TypeTemplateCheck ttc);

    TypeTemplateCheck findOne1(Long id);

    void update1(TypeTemplateCheck tt);


    PageResult searchStatus(Integer page, Integer rows, TypeTemplateCheck typeTemplateCheck);

    void updateStatus(Long[] ids, String status);

    List<TypeTemplateCheck> findStatusAll();

    void deleteStatus(Long id);

    void addspecification(TypeTemplate typeTemplate);

}
