package cn.itcast.core.service;

import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vo.OrderVo;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderVoServiceImpl implements OrderVoService {
   @Autowired
   private OrderDao orderDao;
   @Autowired
   private OrderItemDao orderItemDao;
   @Autowired
   private GoodsDao goodsDao;
    @Override
    public PageResult findPage(Integer page, Integer rows, String name) {
        //分页查询
        PageHelper.startPage(page, rows);
        //组装ordervo
        OrderItemQuery orderItemQuery = new OrderItemQuery();
        orderItemQuery.createCriteria().andSellerIdEqualTo(name);
        //查询订单项列表
        Page<OrderItem> p = (Page<OrderItem>) orderItemDao.selectByExample(orderItemQuery);
        List<OrderItem> results = p.getResult();
        List<OrderVo> orderVos = findVo(results);

        return new PageResult(p.getTotal(),  orderVos);


    }
    //组装ordervo
    public List<OrderVo> findVo( List<OrderItem> orderItems) {
        OrderVo orderVo = new OrderVo();
        ArrayList<OrderVo> orderVos = new ArrayList<>();
        for (OrderItem item : orderItems) {
            Long goodsId = item.getGoodsId();
            Goods goods1 = goodsDao.selectByPrimaryKey(goodsId);
            orderVo.setGoods(goods1);

            Long orderId = item.getOrderId();
            Order order1 = orderDao.selectByPrimaryKey(orderId);
            orderVo.setOrder(order1);
            orderVo.setOrderItem(item);
            orderVos.add(orderVo);
        }

        return orderVos;
    }
}
