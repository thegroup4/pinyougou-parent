package cn.itcast.core.service;

import cn.itcast.core.dao.address.AddressDao;
import cn.itcast.core.dao.address.AreasDao;
import cn.itcast.core.dao.address.CitiesDao;
import cn.itcast.core.dao.address.ProvincesDao;
import cn.itcast.core.pojo.address.*;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 收货地址
 */
@Service
@Transactional
public class AddressServiceImpl implements  AddressService {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private ProvincesDao provincesDao;
    @Autowired
    private CitiesDao citiesDao;
    @Autowired
    private AreasDao areasDao;
    @Override
    public List<Address> findListByLoginUser(String name) {
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(name);
        return addressDao.selectByExample(addressQuery);
    }

    @Override
    public void deleAddr(Long id) {
        addressDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Provinces> findAllAddress() {
        return provincesDao.selectByExample(null);

    }

    @Override
    public List<Cities> findCityById(String provinceid) {
        CitiesQuery citiesQuery = new CitiesQuery();
        citiesQuery.createCriteria().andProvinceidEqualTo(provinceid);
        List<Cities> cities = citiesDao.selectByExample(citiesQuery);
        return cities;
    }

    @Override
    public List<Areas> findAreaById(String cityid) {
        AreasQuery areasQuery = new AreasQuery();
        areasQuery.createCriteria().andCityidEqualTo(cityid);
        List<Areas> areas = areasDao.selectByExample(areasQuery);
        return areas;
    }

    @Override
    public void save(Address entity,String name) {
        entity.setUserId(name);
        entity.setCreateDate(new Date());
        entity.setIsDefault("0");
        addressDao.insertSelective(entity);
    }

}
