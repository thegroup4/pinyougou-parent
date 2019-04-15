//购物车服务层
app.service('addressService',function($http){

    //获取当前登录账号的收货地址
    this.findAddressList=function(){
        return $http.get('address/findListByLoginUser.do');
    }

    //用户删除地址
    this.deleAddr=function(id){
        return $http.get('address/deleAddr.do?id='+ id);
    }

    //省市联动
    this.findAllAddress=function () {
        return $http.get('address/findAllAddress.do')
    }
    //查找市
    this.findCityById=function (provinceid) {
        return $http.get('address/findCityById.do?provinceid='+provinceid)
    }

    //查找县
    this.findAreaById=function (cityid) {
        return $http.get('address/findAreaById.do?cityid='+cityid)
    }

    //新增地址
    this.save = function (entity) {
        return $http.post('address/add.do',entity)
    }
});