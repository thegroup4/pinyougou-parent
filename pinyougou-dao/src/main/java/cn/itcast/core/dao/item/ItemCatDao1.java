package cn.itcast.core.dao.item;

import cn.itcast.core.pojo.item.ItemCat1;
import cn.itcast.core.pojo.item.ItemCatQuery1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemCatDao1 {
    int countByExample(ItemCatQuery1 example);

    int deleteByExample(ItemCatQuery1 example);

    int deleteByPrimaryKey(Long id);

    int insert(ItemCat1 record);

    int insertSelective(ItemCat1 record);

    List<ItemCat1> selectByExample(ItemCatQuery1 example);

    ItemCat1 selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ItemCat1 record, @Param("example") ItemCatQuery1 example);

    int updateByExample(@Param("record") ItemCat1 record, @Param("example") ItemCatQuery1 example);

    int updateByPrimaryKeySelective(ItemCat1 record);

    int updateByPrimaryKey(ItemCat1 record);
}