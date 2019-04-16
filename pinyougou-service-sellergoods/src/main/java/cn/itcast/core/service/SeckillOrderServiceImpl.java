package cn.itcast.core.service;

import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillOrderDao seckillOrderDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ItemDao itemDao;

    @Override
    public List<Map> findSckillOrderListBySellerId(String sellerId) {
        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        seckillOrderQuery.createCriteria().andSellerIdEqualTo(sellerId);
        List<SeckillOrder> seckillOrders = seckillOrderDao.selectByExample(seckillOrderQuery);
        ArrayList<Map> maps = new ArrayList<>();
        Map map = null;
        for (SeckillOrder seckillOrder : seckillOrders) {
            map = new HashMap();
            map.put("id",seckillOrder.getId()+"");
            map.put("createTime",seckillOrder.getCreateTime());
            map.put("status",seckillOrder.getStatus());
            Long seckillId = seckillOrder.getSeckillId();
            SeckillGoods seckillGoods = seckillGoodsDao.selectByPrimaryKey(seckillId);
            Long goodsId = seckillGoods.getGoodsId();
            Goods goods = goodsDao.selectByPrimaryKey(goodsId);
            map.put("goodsName",goods.getGoodsName());
            maps.add(map);
        }

        return maps;
    }

    @Override
    public List<Map<String, Object>> findSckillOrderListByUserId(String userId) {

        List<Map<String,Object>> list = new ArrayList<>();
        //List<SeckillOrder> list1 = redisTemplate.opsForHash().values(userId + "seckillOrderList");
        List<SeckillOrder> list1 = new ArrayList<>();
        if(null ==list1 || list1.size() == 0) {
            SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
            seckillOrderQuery.createCriteria().andUserIdEqualTo(userId);
            list1 = seckillOrderDao.selectByExample(seckillOrderQuery);
        }

        HashMap<String,Object> map = null;
        SeckillGoods seckillGoods = null;
        Item item = null;

        for (SeckillOrder seckillOrder : list1) {
            map = new HashMap<>();
            map.put("createTime",seckillOrder.getCreateTime());
            map.put("id",seckillOrder.getId()+"");
            map.put("sellerName",seckillOrder.getSellerId());
            seckillGoods = seckillGoodsDao.selectByPrimaryKey(seckillOrder.getSeckillId());
            item = itemDao.selectByPrimaryKey(seckillGoods.getItemId());
            map.put("ima",item.getImage());
            map.put("num",null);
            map.put("introduction",seckillGoods.getIntroduction());
            map.put("spec",item.getSpec());
            map.put("price",seckillGoods.getPrice());
            map.put("costPrice",seckillGoods.getCostPrice());
            map.put("status",seckillOrder.getStatus());
            list.add(map);
        }
        return list;
    }

    @Override
    public void cancelSeckillOrder(String userId, Long id) {

        SeckillOrder seckillOrder = null ;

        seckillOrder = (SeckillOrder) redisTemplate.boundHashOps(userId + "seckillOrderList").get(id);
        if(null == seckillOrder){
            seckillOrder = seckillOrderDao.selectByPrimaryKey(id);
        }
        //设置订单状态为失效状态 0待支付 1已支付 2订单已取消 3订单支付超时
        seckillOrder.setStatus("2");
       // redisTemplate.boundHashOps(userId + "seckillOrderList").put(id,seckillOrder);
        seckillOrderDao.updateByPrimaryKey(seckillOrder);
    }

    @Override
    public Date findOrderCreateTimeById(Long id) {

        return seckillOrderDao.selectByPrimaryKey(id).getCreateTime();

    }
}
