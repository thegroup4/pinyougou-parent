package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public PageResult search(Integer page, Integer rows, SeckillGoods seckillGoods) {
        PageHelper.startPage(page,rows);

        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();

        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();

        if (null!=seckillGoods.getStatus()&&!"".equals(seckillGoods.getStatus())){
            criteria.andStatusEqualTo(seckillGoods.getStatus());
        }
        if (null!=seckillGoods.getSellerId()&&!"".equals(seckillGoods.getSellerId())){
            criteria.andSellerIdEqualTo(seckillGoods.getSellerId());
        }

        Page<SeckillGoods> p = (Page<SeckillGoods>) seckillGoodsDao.selectByExample(seckillGoodsQuery);




        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void updateState(Long[] ids, String status) {

        SeckillGoods seckillGoods = new SeckillGoods();

        seckillGoods.setStatus(status);

        for (Long id : ids) {

            seckillGoods.setId(id);
            seckillGoodsDao.updateByPrimaryKeySelective(seckillGoods);
        }
    }

    @Override
    public List<SeckillGoods> findList() {
        return seckillGoodsDao.selectByExample(null);
    }

    @Override
    public SeckillGoods findOneFromRedis(Long id) {
        return (SeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(id);
    }

    @Override
    public Result submitOrder(Long seckillId) {

        return null;
    }
}
