package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.core.dao.log.LogDao;
import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理
 */
@Service
@Transactional
public class UserServiceImpl implements  UserService {


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination smsDestination;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LogDao logDao;


    //发短信
    public void sendCode(String phone){
        //1:生成6位验证码
        String randomNumeric = RandomStringUtils.randomNumeric(6);
        //2:保存缓存一份
        redisTemplate.boundValueOps(phone).set(randomNumeric,5, TimeUnit.HOURS);

        //3:发消息
        jmsTemplate.send(smsDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("SignName","品优购商城");
                mapMessage.setString("TemplateCode","SMS_126462276");
                mapMessage.setString("TemplateParam","{\"number\":\""+randomNumeric+"\"}");
                mapMessage.setString("PhoneNumbers",phone);//17630593193

                return mapMessage;
            }
        });


    }


    //用户添加
    @Override
    public void add(String smscode, User user) {
        String code = (String) redisTemplate.boundValueOps(user.getPhone()).get();
        if(null != code){
        //1：判断验证码是否失效
            if(code.equals(smscode)){
        //2:判断验证码是否正确
                //3:先加密密码 再保存用户
                user.setCreated(new Date());
                user.setUpdated(new Date());
                userDao.insertSelective(user);
            }else{
                throw new RuntimeException("验证码错误");
            }

        }else{
            throw new RuntimeException("验证码失败");
        }

    }

    @Override
    public int findCount() {
        return userDao.countByExample(null);
    }

    @Override
    public PageResult search(Integer page, Integer rows, User user) {
        //分页插件
        PageHelper.startPage(page, rows);
        //添加条件对象
        UserQuery userQuery = new UserQuery();
        UserQuery.Criteria criteria = userQuery.createCriteria();
        //默认查询所有状态
        //按名称查询进行模糊查询
        if (null != user.getUsername() && !"".equals(user.getUsername().trim())) {
            criteria.andUsernameLike("%"+user.getUsername().trim()+"%");
        }
        //查询商家昵称
        if (null != user.getNickName() && !"".equals(user.getNickName().trim())) {
            criteria.andNickNameEqualTo(user.getNickName());
        }
        if (null!=user.getStatus()&&!"".equals(user.getStatus())){
            criteria.andStatusEqualTo(user.getStatus());
        }
        //按手机
        if (null != user.getPhone() && !"".equals(user.getPhone().trim())) {
            criteria.andPhoneEqualTo(user.getPhone());
        }
        Page<User> p = (Page<User>) userDao.selectByExample(userQuery);

        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public User findOne(Long id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * 统计活跃用户的个数
     * 过去48小时内登录过1次就算是活跃用户
     * @return
     */
    @Override
    public int findActiveUser() {
        //通过用户名查询一个用户
        Date date = DateUtils.dateAddOrSubtract(new Date(), -2);
        //查询过去两天内登录过一次的用户个数
        int num = logDao.selectCountGroupByUserName(date);
        return num;
    }


    @Override
    public void updateStatus(Long[] ids, String status) {
        User user = new User();

        user.setStatus(status);
        for (Long id : ids) {
            user.setId(id);
            userDao.updateByPrimaryKeySelective(user);
        }
    }



}
