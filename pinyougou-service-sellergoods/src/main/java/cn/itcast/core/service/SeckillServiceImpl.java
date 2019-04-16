package cn.itcast.core.service;

import cn.itcast.common.utils.IdWorker;
import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import vo.GoodsVo;

import java.util.*;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private SeckillOrderDao seckillOrderDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private ItemDao itemDao;


    @Override
    public void add(String sellerId, Date startTime, Date endTime, GoodsVo goodsVo) {

        SeckillGoods seckillGoods = null;
        Goods goods = goodsVo.getGoods();

        List<Item> itemList = goodsVo.getItemList();

        for (Item item : itemList) {
            seckillGoods = new SeckillGoods();
            seckillGoods.setId(idWorker.nextId());
            seckillGoods.setGoodsId(goods.getId());
            seckillGoods.setItemId(item.getId());
            seckillGoods.setTitle(goodsVo.getTitle());
            seckillGoods.setSmallPic(goods.getSmallPic());
            seckillGoods.setPrice(item.getPrice());
            seckillGoods.setCostPrice(item.getCostPrice());
            seckillGoods.setSellerId(sellerId);
            seckillGoods.setCreateTime(new Date());
            seckillGoods.setStatus("0");
            seckillGoods.setStartTime(startTime);
            seckillGoods.setEndTime(endTime);
            seckillGoods.setNum(item.getStockCount());
            seckillGoods.setStockCount(item.getNum());
            seckillGoods.setIntroduction(goodsVo.getIntroduction());
            seckillGoodsDao.insertSelective(seckillGoods);
        }


    }



    @Override
    public List<Map<String, Object>> findSeckillGoodsListBySellerId(String sellerId) {

        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        seckillGoodsQuery.createCriteria().andSellerIdEqualTo(sellerId);
        List<SeckillGoods> list1 = seckillGoodsDao.selectByExample(seckillGoodsQuery);

        List<Map<String,Object>> list = new ArrayList<>();

        HashMap<Object, Object> map1 = new HashMap<>();
        HashMap<String, Object> map =null;
        for (SeckillGoods seckillGoods : list1) {
            if(map1.containsKey(seckillGoods.getGoodsId())){
                continue;
            }else{
                map = new HashMap<>();
                Goods goods = goodsDao.selectByPrimaryKey(seckillGoods.getGoodsId());
                if(null != goods){
                    map.put("goodsId",goods.getId());
                    map.put("goodsName",goods.getGoodsName());
                    map.put("status",seckillGoods.getStatus());
                    list.add(map);
                    map1.put(seckillGoods.getGoodsId(),null);
                }

            }


        }
        return list;

    }

    @Override
    public Map<String, Object> findSeckillGoodsVo(String sellerId, Long goodsId) {

        Map<String,Object> goodsVo =  new HashMap<String,Object>();

        Goods goods = goodsDao.selectByPrimaryKey(goodsId);


        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        seckillGoodsQuery.createCriteria().andSellerIdEqualTo(sellerId);
        List<SeckillGoods> seckillGoodsList = seckillGoodsDao.selectByExample(seckillGoodsQuery);

        Item item = null;
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            item =  itemDao.selectByPrimaryKey(seckillGoods.getItemId());
            seckillGoods.setItemSpec(item.getSpec());

        }

        goodsVo.put("goodsId",goods.getId());
        goodsVo.put("goodsName",goods.getGoodsName());
        goodsVo.put("brand",item.getBrand());
        goodsVo.put("title",seckillGoodsList.get(0).getTitle());
        goodsVo.put("introduction",seckillGoodsList.get(0).getIntroduction());
        goodsVo.put("seckillGoodsList",seckillGoodsList);
        Date startTime = seckillGoodsList.get(0).getStartTime();
        Date endTime = seckillGoodsList.get(0).getEndTime();
        goodsVo.put("startDate",startTime);
        goodsVo.put("endTime",endTime);
        return goodsVo;
    }

}
