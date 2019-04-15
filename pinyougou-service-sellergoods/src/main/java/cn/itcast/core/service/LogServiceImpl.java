package cn.itcast.core.service;

import cn.itcast.core.dao.log.LogDao;
import com.alibaba.dubbo.config.annotation.Service;
import entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Override
    public void save(SysLog sysLog) {
        logDao.save(sysLog);
    }
}
