package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SeckillOrdersServiceImpl implements SeckillOrdersService {

    @Autowired
    private SeckillOrderDao seckillOrderDao;
    @Override
    public PageResult search(Integer page, Integer rows, SeckillOrder seckillOrder) {

        PageHelper.startPage(page,rows);

        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();

        SeckillOrderQuery.Criteria criteria = seckillOrderQuery.createCriteria();

        if (null!=seckillOrder.getStatus()&&!"".equals(seckillOrder.getStatus())){
            criteria.andStatusEqualTo(seckillOrder.getStatus());
        }
        if (null!=seckillOrder.getSellerId()&&!"".equals(seckillOrder.getSellerId())){
            criteria.andSellerIdEqualTo(seckillOrder.getSellerId());
        }

        Page<SeckillOrder> page1 = (Page<SeckillOrder>) seckillOrderDao.selectByExample(seckillOrderQuery);




        return new PageResult(page1.getTotal(),page1.getResult());
    }
}
