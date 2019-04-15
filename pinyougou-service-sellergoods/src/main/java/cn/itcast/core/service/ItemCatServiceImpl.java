package cn.itcast.core.service;

import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.dao.item.ItemCatDao1;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCat1;
import cn.itcast.core.pojo.item.ItemCatQuery;
import cn.itcast.core.pojo.item.ItemCatQuery1;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;

/**
 * 商品分类管理
 */
@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ItemCatDao1 itemCatDao1;

    @Override
    public List<ItemCat> findByParentId(Long parentId) {

        //查询所有商品分类结果集 保存到缓存中
        List<ItemCat> itemCatList = itemCatDao.selectByExample(null);


        //遍历
        for (ItemCat itemCat : itemCatList) {

            //缓存 数据类型 Hash类型
            redisTemplate.boundHashOps("itemCatList").put(itemCat.getName(), itemCat.getTypeId());

        }
        //添加
        //修改
        //删除
        //redisTemplate.boundHashOps("itemCatList").delete(itemCat.getName())
        //从Mysql数据查询
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        itemCatQuery.createCriteria().andParentIdEqualTo(parentId);
        return itemCatDao.selectByExample(itemCatQuery);
    }

    //查询一个
    @Override
    public ItemCat findOne(Long id) {
        return itemCatDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);

    }

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination topicPageAndSolrDestination;
    @Autowired
    private Destination queueSolrDeleteDestination;

    @Override
    public PageResult search(Integer page, Integer rows, ItemCat1 itemCat1) {

        PageHelper.startPage(page, rows);

        ItemCatQuery1 itemCatQuery1 = new ItemCatQuery1();

        ItemCatQuery1.Criteria criteria = itemCatQuery1.createCriteria();

        if (null != itemCat1.getStatus() && !"".equals(itemCat1.getStatus())) {
            criteria.andStatusEqualTo(itemCat1.getStatus());
        }
        if (null != itemCat1.getTypeId() && !"".equals(itemCat1.getTypeId())) {
            criteria.andParentIdEqualTo(itemCat1.getTypeId());
        }

        Page<ItemCat1> p = (Page<ItemCat1>) itemCatDao1.selectByExample(itemCatQuery1);

        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {

        ItemCat1 itemCat1 = new ItemCat1();

        itemCat1.setStatus(status);
        for (Long id : ids) {
            itemCat1.setId(id);
            itemCatDao1.updateByPrimaryKeySelective(itemCat1);
        }
    }

    @Override
    public void saveStatus(ItemCat1 itemCat1) {
        itemCatDao1.insertSelective(itemCat1);
    }


}
