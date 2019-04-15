package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.Areas;
import cn.itcast.core.pojo.address.Cities;
import cn.itcast.core.pojo.address.Provinces;

import javax.naming.Name;
import java.util.List;

public interface AddressService {
    List<Address> findListByLoginUser(String name);

    void deleAddr(Long id);

    List<Provinces> findAllAddress();

    List<Cities> findCityById(String provinceid);

    List<Areas> findAreaById(String cityid);

    void save(Address entity,String name);
}
