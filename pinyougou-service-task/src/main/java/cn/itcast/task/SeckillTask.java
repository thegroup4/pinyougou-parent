package cn.itcast.task;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 秒杀商品增量同步
 * @Author 66999aa@gmail.com
 * @Date 2019/4/12
 */

@Component
public class SeckillTask {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    /**
     * 同步秒杀商品
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void SeckillTask() {
        System.out.println("执行任务调度" + new Date());
        //查询缓存中所有的秒杀商品ID集合
        List goodsIdList = new ArrayList(redisTemplate.boundHashOps("seckillGoods").keys());
        System.out.println(goodsIdList);
        //查询正在秒杀的商品列表
        SeckillGoodsQuery goodsQuery = new SeckillGoodsQuery();
        Criteria criteria = goodsQuery.createCriteria();
        // 审核通过的商品
        criteria.andStatusEqualTo("1");
        //库存数大于0
        criteria.andStockCountGreaterThan(0);
        //开始日期小于等于当前日期
        criteria.andStartTimeLessThanOrEqualTo(new Date());
        //截止日期大于等于当前日期
        criteria.andEndTimeGreaterThanOrEqualTo(new Date());
        if (goodsIdList.size() > 0) {
            //排除缓存中已经存在的商品ID集合
            criteria.andIdNotIn(goodsIdList);
        }
        List<SeckillGoods> seckillGoodsList = seckillGoodsDao.selectByExample(goodsQuery);
        //将列表数据装入缓存
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId(), seckillGoods);
            System.out.println("增量同步秒杀商品ID:" + seckillGoods.getId());
        }
        System.out.println("....end....");
    }

    /**
     * 移除秒杀商品
     */
    @Scheduled(cron = "* * * * * ?")
    public void removeSeckillGoods() {
        //查询出缓存中的数据，扫描每条记录，判断时间，如果当前时间超过了截止时间，移除此记录
        List<SeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("seckillGoods").values();
        System.out.println("执行了清除秒杀商品的任务" + new Date());
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            //如果结束日期小于当前日期，则表示过期;
            if (seckillGoods.getEndTime().getTime() < System.currentTimeMillis()) {
                //同步到数据库
                seckillGoodsDao.updateByPrimaryKey(seckillGoods);
                //清除缓存
                redisTemplate.boundHashOps("seckillGoods").delete(seckillGoods.getId());
                System.out.println("秒杀商品" + seckillGoods.getId() + "已过期");
            }
        }
        System.out.println("执行了清除秒杀商品的任务...end");
    }
}
