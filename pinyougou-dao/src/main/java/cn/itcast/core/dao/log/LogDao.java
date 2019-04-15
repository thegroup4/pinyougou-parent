package cn.itcast.core.dao.log;

import entity.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface LogDao {
    @Insert("insert into sys_log values(log_seq.nextval, #{visitTime},#{username},#{ip},#{method})")
    void save(SysLog sysLog);

    @Select("select count(1) from (select count(1)  from sys_log where visitTime > #{date} group by username) as log")
    int selectCountGroupByUserName(Date date);
}
