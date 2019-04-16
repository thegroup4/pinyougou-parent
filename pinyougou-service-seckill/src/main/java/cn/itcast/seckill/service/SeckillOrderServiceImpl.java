package cn.itcast.seckill.service;

import java.util.Date;
import java.util.List;

import cn.itcast.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery.Criteria;
import cn.itcast.seckill.service.SeckillOrderService;

import entity.PageResult;
import cn.itcast.common.utils.IdWorker;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillOrderDao seckillOrderDao;

    /**
     * 查询全部
     */
    @Override
    public List<SeckillOrder> findAll() {
        return seckillOrderDao.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<SeckillOrder> page = (Page<SeckillOrder>) seckillOrderDao.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(SeckillOrder seckillOrder) {
        seckillOrderDao.insert(seckillOrder);
    }

    /**
     * 修改
     */
    @Override
    public void update(SeckillOrder seckillOrder) {
        seckillOrderDao.updateByPrimaryKey(seckillOrder);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public SeckillOrder findOne(Long id) {
        return seckillOrderDao.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            seckillOrderDao.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult findPage(SeckillOrder seckillOrder, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SeckillOrderQuery example = new SeckillOrderQuery();
        Criteria criteria = example.createCriteria();
        if (seckillOrder != null) {
            if (seckillOrder.getUserId() != null && seckillOrder.getUserId().length() > 0) {
                criteria.andUserIdLike("%" + seckillOrder.getUserId() + "%");
            }
            if (seckillOrder.getSellerId() != null && seckillOrder.getSellerId().length() > 0) {
                criteria.andSellerIdLike("%" + seckillOrder.getSellerId() + "%");
            }
            if (seckillOrder.getStatus() != null && seckillOrder.getStatus().length() > 0) {
                criteria.andStatusLike("%" + seckillOrder.getStatus() + "%");
            }
            if (seckillOrder.getReceiverAddress() != null && seckillOrder.getReceiverAddress().length() > 0) {
                criteria.andReceiverAddressLike("%" + seckillOrder.getReceiverAddress() + "%");
            }
            if (seckillOrder.getReceiverMobile() != null && seckillOrder.getReceiverMobile().length() > 0) {
                criteria.andReceiverMobileLike("%" + seckillOrder.getReceiverMobile() + "%");
            }
            if (seckillOrder.getReceiver() != null && seckillOrder.getReceiver().length() > 0) {
                criteria.andReceiverLike("%" + seckillOrder.getReceiver() + "%");
            }
            if (seckillOrder.getTransactionId() != null && seckillOrder.getTransactionId().length() > 0) {
                criteria.andTransactionIdLike("%" + seckillOrder.getTransactionId() + "%");
            }
        }
        Page<SeckillOrder> page = (Page<SeckillOrder>) seckillOrderDao.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void submitOrder(Long seckillId, String userId) {
        //1.查询缓存中的商品
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(seckillId);
        if (seckillGoods == null) {
            throw new RuntimeException("商品不存在");
        }
        if (seckillGoods.getStockCount() <= 0) {
            throw new RuntimeException("商品已经被抢光");
        }
        //2.减少库存
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);//减库存
        redisTemplate.boundHashOps("seckillGoods").put(seckillId, seckillGoods);//存入缓存
        if (seckillGoods.getStockCount() == 0) {
            seckillGoodsDao.updateByPrimaryKey(seckillGoods);    //更新数据库
            redisTemplate.boundHashOps("seckillGoods").delete(seckillId);
            System.out.println("商品同步到数据库...");
        }
        //3.存储秒杀订单 (不向数据库存 ,只向缓存中存储 )
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setId(idWorker.nextId());
        seckillOrder.setSeckillId(seckillId);
        seckillOrder.setMoney(seckillGoods.getCostPrice());
        seckillOrder.setUserId(userId);
        seckillOrder.setSellerId(seckillGoods.getSellerId());//商家ID
        seckillOrder.setCreateTime(new Date());
        seckillOrder.setStatus("0");//状态
        redisTemplate.boundHashOps("seckillOrder").put(userId, seckillOrder);
        System.out.println("保存订单成功(redis)");
    }

    @Override
    public SeckillOrder searchOrderFromRedisByUserId(String userId) {
        return (SeckillOrder) redisTemplate.boundHashOps("seckillOrder").get(userId);
    }

    @Override
    public void saveOrderFromRedisToDb(String userId, Long orderId, String transactionId) {
        //1.从缓存中提取订单数据
        SeckillOrder seckillOrder = searchOrderFromRedisByUserId(userId);
        if (seckillOrder == null) {
            throw new RuntimeException("不存在订单");
        }
        if (seckillOrder.getId().longValue() != orderId.longValue()) {
            throw new RuntimeException("订单号不符");
        }
        //2.修改订单实体的属性
        seckillOrder.setPayTime(new Date());//支付日期
        seckillOrder.setStatus("1");//已支付 状态
        seckillOrder.setTransactionId(transactionId);
        //3.将订单存入数据库
        seckillOrderDao.insert(seckillOrder);
        //4.清除缓存中的订单
        redisTemplate.boundHashOps("seckillOrder").delete(userId);
    }

    @Override
    public void deleteOrderFromRedis(String userId, Long orderId) {
        //1.查询出缓存中的订单
        SeckillOrder seckillOrder = searchOrderFromRedisByUserId(userId);
        if (seckillOrder != null) {
            //2.删除缓存中的订单
            redisTemplate.boundHashOps("seckillOrder").delete(userId);
            //3.库存回退
            SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(seckillOrder.getSeckillId());
            if (seckillGoods != null) { //如果不为空
                seckillGoods.setStockCount(seckillGoods.getStockCount() + 1);
                redisTemplate.boundHashOps("seckillGoods").put(seckillOrder.getSeckillId(), seckillGoods);
            } else {
                seckillGoods = new SeckillGoods();
                seckillGoods.setId(seckillOrder.getSeckillId());
                //属性要设置。。。。省略
                seckillGoods.setStockCount(1);//数量为1
                redisTemplate.boundHashOps("seckillGoods").put(seckillOrder.getSeckillId(), seckillGoods);
            }
            System.out.println("订单取消：" + orderId);
        }
    }
}
