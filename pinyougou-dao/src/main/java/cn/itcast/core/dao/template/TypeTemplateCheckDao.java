package cn.itcast.core.dao.template;


import cn.itcast.core.pojo.template.TypeTemplateCheck;
import cn.itcast.core.pojo.template.TypeTemplateCheckQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeTemplateCheckDao {
    int countByExample(TypeTemplateCheckQuery example);

    int deleteByExample(TypeTemplateCheckQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(TypeTemplateCheck record);

    int insertSelective(TypeTemplateCheck record);

    List<TypeTemplateCheck> selectByExample(TypeTemplateCheckQuery example);

    TypeTemplateCheck selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TypeTemplateCheck record, @Param("example") TypeTemplateCheckQuery example);

    int updateByExample(@Param("record") TypeTemplateCheck record, @Param("example") TypeTemplateCheckQuery example);

    int updateByPrimaryKeySelective(TypeTemplateCheck record);

    int updateByPrimaryKey(TypeTemplateCheck record);
}