package cn.itcast.core.dao.user;

import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserQuery;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserDao {
    int countByExample(UserQuery example);

    int deleteByExample(UserQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserQuery example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserQuery example);

    int updateByExample(@Param("record") User record, @Param("example") UserQuery example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<Map<String,Object>> findUserForLine(String beginDate, String endDate);
}