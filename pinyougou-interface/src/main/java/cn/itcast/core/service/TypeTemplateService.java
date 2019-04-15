package cn.itcast.core.service;

import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateCheck;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
    PageResult search(Integer page, Integer rows, TypeTemplate tt);

    void add(TypeTemplateCheck ttc);

    TypeTemplateCheck findOne(Long id);

    void update(TypeTemplateCheck tt);

    List<Map> findBySpecList(Long id);
}
