package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.common.utils.IdWorker;
import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import vo.Cart;

import java.math.BigDecimal;
import java.util.*;

/**
 * 订单管理
 */
@Service
@Transactional
@SuppressWarnings("all")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PayLogDao payLogDao;

    @Autowired
    private GoodsDao goodsDao;
    @Override
    public void add(Order order) {

        //缓存中购物车
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("CART").get(order.getUserId());


        //日志表  支付金额
        long total = 0;
        //订单集合
        List<Long> ids = new ArrayList<>();


        for (Cart cart : cartList) {
            //保存订单

            //订单ID  分布式ID
            long id = idWorker.nextId();
            ids.add(id);
            order.setOrderId(id);
            //金额
            double totalPrice = 0;

            List<OrderItem> orderItemList = cart.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                //库存ID
                Item item = itemDao.selectByPrimaryKey(orderItem.getItemId());

                //数量
                //订单详情ID
                orderItem.setId(idWorker.nextId());
                //商品id
                orderItem.setGoodsId(item.getGoodsId());
                //订单ID 外键
                orderItem.setOrderId(id);
                //标题
                orderItem.setTitle(item.getTitle());
                //价格
                orderItem.setPrice(item.getPrice());
                //小计
                orderItem.setTotalFee(item.getPrice().multiply(new BigDecimal(orderItem.getNum())));

                totalPrice += orderItem.getTotalFee().doubleValue();

                //图片路径
                orderItem.setPicPath(item.getImage());
                //商家ID
                orderItem.setSellerId(item.getSellerId());

                //保存订单详情表
                orderItemDao.insertSelective(orderItem);

            }
            //给订单表设置金额
            order.setPayment(new BigDecimal(totalPrice));

            total += order.getPayment().longValue();

            //订单状态
            order.setStatus("1");
            //时间
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());

            //订单来源
            order.setSourceType("2");
            //商家ID
            order.setSellerId(cart.getSellerId());
            //保存订单
            orderDao.insertSelective(order);

        }

        //保存日志表
        PayLog payLog = new PayLog();
        //ID 支付订单ID
        payLog.setOutTradeNo(String.valueOf(idWorker.nextId()));

        //时间
        payLog.setCreateTime(new Date());
        //付款时间
        //总金额 分
        payLog.setTotalFee(total*100);

        //用户id
        payLog.setUserId(order.getUserId());


        //支付状态
        payLog.setTradeState("0");


        //订单集合 [123,456,6787]
        payLog.setOrderList(ids.toString().replace("[","").replace("]",""));

        //付款方式
        payLog.setPayType("1");

        //保存完成
        payLogDao.insertSelective(payLog);

        //保存缓存一份
        redisTemplate.boundHashOps("payLog").put(order.getUserId(),payLog);

        //清空
        //redisTemplate.boundHashOps("CART").delete(order.getUserId());

    }

    @Override
    public List<Map> orderStatistics(Date startDate, Date endDate,String name) {
        List<Map> listMap = new ArrayList<>();



        //  list<Map<goodsName, Map<date,money>>>



        //如果传入的名字不为空则表示是商家进行查询的
        if (name!=null) {
            //传入的日期相差多少天
            int i = DateUtils.compareWithTwoDate(startDate, endDate);
            //从选择的日期进行开始查询
            for (int j = 0; j < i; j++) {
                HashMap<String, Object> dayMap = new HashMap<>();
                HashMap<String, Object> goodsResult = new HashMap<>();
                HashMap<String, Object> goodsMap = new HashMap<>();

                //每天的总价
                BigDecimal dayTotal = new BigDecimal(0);
                BigDecimal multiply = new BigDecimal(0);
                //每种货物的总价
                int goodsTotal = 0;
                Date startDate1 = DateUtils.dateAddOrSubtract(startDate, j);
                Date endDate1 = DateUtils.dateAddOrSubtract(startDate, j + 1);

                //查询一天的所有订单
                OrderQuery orderQuery = new OrderQuery();
                OrderQuery.Criteria criteria = orderQuery.createCriteria();
                criteria.andSellerIdEqualTo(name);
                //已经得到每天的订单结果集
                List<Order> orders = orderDao.selectByExample(orderQuery);

                //遍历每天的订单结果集得到每一个订单
                for (Order order : orders) {
                    //通过订单ID查询所有的订单
                    Long orderId = order.getOrderId();
                    OrderItemQuery orderItemQuery = new OrderItemQuery();
                    OrderItemQuery.Criteria criteria1 = orderItemQuery.createCriteria();
                    criteria1.andItemIdEqualTo(orderId);

                    //已经得到每个订单
                    List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);
                    //遍历每个订单，获取购物项
                    for (OrderItem orderItem : orderItems) {
                        //获取每个购物项的名称
                        Goods goods = goodsDao.selectByPrimaryKey(orderItem.getGoodsId());
                        //判断是否有该goods的名称,
                        if(goodsMap.get(goods.getGoodsName())!=null) {
                            //如果有 则取出来 增加 再设置回去
                             BigDecimal money = (BigDecimal) goodsMap.get(goods.getGoodsName());
                            goodsMap.put(goods.getGoodsName(), orderItem.getTotalFee().multiply(money));
                        }else {
                            //如果没有则增加
                            goodsMap.put(goods.getGoodsName(),orderItem.getTotalFee());
                        }
                        //总销售量增加
                         multiply = dayTotal.multiply(orderItem.getTotalFee());
                    }
                }
                //添加每种商品的每日的销售量
                goodsResult.put(String.valueOf(startDate1),goodsMap);
                //加入到list集合中去
                listMap.add(goodsResult);
                dayMap.put(String.valueOf(startDate1),multiply);

                listMap.add(dayMap);
            }
        }
        return listMap;
    }

    @Override
    public PageResult search(Integer page, Integer rows, String sellerId, Order order) {

        PageHelper.startPage(page,rows);

        OrderQuery orderQuery = new OrderQuery();

        OrderQuery.Criteria criteria = orderQuery.createCriteria();
        criteria.andSellerIdEqualTo(sellerId);

        if (null!=order.getStatus()&&!"".equals(order.getStatus())){
            criteria.andStatusEqualTo(order.getStatus());
        }
        if (null!=order.getCreateTime()&&!"".equals(order.getCreateTime())){
            criteria.andCreateTimeEqualTo(order.getCreateTime());
        }
        Page<Order> pageList = (Page<Order>) orderDao.selectByExample(orderQuery);


        return new PageResult(pageList.getTotal(),pageList.getResult());

        }

    @Override
    public void update(Long id,String  status) {

        Order order = new Order();
        order.setStatus(status);

        order.setOrderId(id);
        orderDao.updateByPrimaryKeySelective(order);


    }

    @Override
    public Order findByOrderId(Long id) {

        Order order = orderDao.selectByPrimaryKey(id);

        return order;
    }



}
