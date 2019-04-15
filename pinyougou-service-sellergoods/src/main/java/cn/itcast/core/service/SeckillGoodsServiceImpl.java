package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.PageResult;
import org.apache.solr.common.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.*;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;


    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination topicPageSeckillDestination;
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

        //List<SeckillGoods> seckillGoodsList = seckillGoodsDao.selectByExample(seckillGoodsQuery);
        Page<SeckillGoods> page1 = (Page<SeckillGoods>) seckillGoodsDao.selectByExample(seckillGoodsQuery);


        /*Map<Long, SeckillGoods> map = new HashMap<>();
        for (SeckillGoods goods : seckillGoodsList) {
            if (!map.containsKey(goods.getGoodsId())){
                map.put(goods.getGoodsId(),goods);
            }
        }*/
        //Page<SeckillGoods> page1= (Page<SeckillGoods>) map.values();
        //Collection<SeckillGoods> values = map.values();
        /*List<SeckillGoods> list=new ArrayList<>();
        for (SeckillGoods value : values) {
            list.add(value);
        }*/
        return new PageResult(page1.getTotal(),page1.getResult());
    }

    @Override
    public void updateState(Long[] ids, String status) {

        SeckillGoods seckillGoods = new SeckillGoods();

        seckillGoods.setStatus(status);
        seckillGoods.setCheckTime(new Date());

        for (Long id : ids) {

            //seckillGoods.setGoodsId(id);
           // seckillGoodsDao.updateByPrimaryKeySelective(seckillGoods);
            SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
            SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();
            criteria.andGoodsIdEqualTo(id);
            seckillGoodsDao.updateByExampleSelective(seckillGoods,seckillGoodsQuery);
            //seckillGoods = seckillGoodsDao.selectByPrimaryKey(id);
            if ("1".equals(status)){
                redisTemplate.boundHashOps("seckillGoods").put(id,seckillGoods);
                jmsTemplate.send(topicPageSeckillDestination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(String.valueOf(id));
                    }
                });
            }
        }
    }

    @Override
    public List<SeckillGoods> findByGoodsId(Long goodsId) {

        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);

        List<SeckillGoods> list = seckillGoodsDao.selectByExample(seckillGoodsQuery);


        return list;
    }
}
