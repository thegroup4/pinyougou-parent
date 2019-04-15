package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.user.UserDao;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SummaryGraphServiceImpl implements SummaryGraphService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    /**
     * 折线图销售订单按年月日显示
     * @return
     */
    @Override
    public List<Map<String, Object>> findOrderForLine(String beginDate,String endDate) {
        return orderDao.findOrderForLine(beginDate,endDate);
    }

    /**
     * 饼状销售订单按年月显示
     * @return
     */
    @Override
    public List<Map<String, Object>> findOrderForPie(String beginDate, String endDate) {
        return orderDao.findOrderForPie(beginDate,endDate);
    }

    @Override
    public List<Map<String, Object>> findUserForLine(String beginDate, String endDate) {
        return userDao.findUserForLine(beginDate,endDate);
    }


}
