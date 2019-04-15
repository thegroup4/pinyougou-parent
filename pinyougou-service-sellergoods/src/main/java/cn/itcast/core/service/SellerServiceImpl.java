package cn.itcast.core.service;

import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.pojo.seller.SellerQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商家管理
 */
@Service
@Transactional
public class SellerServiceImpl implements  SellerService {


    @Autowired
    private SellerDao sellerDao;
    //添加
    @Override
    public void add(Seller seller) {

        //判断商家名称是否为null
        if(null != seller.getSellerId() && !"".equals(seller.getSellerId())) {
            //判断商家名称是否含有空格
            if(!seller.getSellerId().contains(" ")){

                //保存
                 seller.setPassword(new BCryptPasswordEncoder().encode(seller.getPassword()));
                 //默认是未审核
                seller.setStatus("0");
                sellerDao.insertSelective(seller);

            }else{
                throw new RuntimeException("商家名称不能有空格");
            }

        }else{
            throw new RuntimeException("商家名称不能为空");
        }

    }

    //根据用户名查询一个商家 要求此商家必须审核通过
    @Override
    public Seller findBySellerId(String username) {

        return sellerDao.selectByPrimaryKey(username);
    }

    /**
     * 查询上级的总数
     * @return
     */
    @Override
    public int findCount() {
        return sellerDao.findCount();
    }

    /**
     * 分页查询商家
     * @param page
     * @param rows
     * @param seller
     * @return
     */
    @Override
    public PageResult search(Integer page, Integer rows, Seller seller) {
        //分页插件
        PageHelper.startPage(page,rows);
        //添加条件对象
        SellerQuery sellerQuery = new SellerQuery();
        SellerQuery.Criteria criteria = sellerQuery.createCriteria();
        //默认查询所有状态
        //按名称查询进行模糊查询
        if(null!=seller.getName()&&!"".equals(seller.getName().trim())){
            criteria.andNameLike("%"+seller.getName().trim()+"%");
        }
        //查询商家昵称
        if(null!=seller.getNickName()&&!"".equals(seller.getNickName().trim())){
            criteria.andNameEqualTo(seller.getNickName());
        }
        //按地址查询
        if(null!=seller.getAddressDetail()&&!"".equals(seller.getAddressDetail().trim())){
            criteria.andAddressDetailEqualTo(seller.getAddressDetail());
        }
        Page<Seller> p = (Page<Seller>) sellerDao.selectByExample(sellerQuery);

        return new PageResult(p.getTotal(),p.getResult());
    }
}
