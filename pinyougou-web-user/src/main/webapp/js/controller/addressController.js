//首页控制器
app.controller('addressController',function($scope,loginService,addressService){
    //获取当前用户的地址列表
    $scope.findAddressList=function(){
        addressService.findAddressList().success(
            function(response){
                $scope.addressList=response;
                /*                for(var i=0;i<$scope.addressList.length;i++){
                                    if($scope.addressList[i].isDefault=='1'){
                                        $scope.address=$scope.addressList[i];
                                        break;
                                    }
                                }*/

            }
        );
    }
    //获取当前用户的地址列表
    $scope.deleAddr = function(id){
        addressService.deleAddr(id).success(function(response){
            // 判断保存是否成功:
            if(response.flag == true){
                alert(response.message)
                $scope.findAddressList();
            }else{
                // 删除失败
                alert(response.message);
            }
        });
    }
    //省
    $scope.findAllAddress = function(){
            addressService.findAllAddress().success(function(response){
                $scope.provinces = response;
            });
    }
    // 市
    $scope.$watch("entity.provinceid",function(newValue,oldValue){
        addressService.findCityById(newValue).success(function(response){
            $scope.cities = response;
        });
    });
    //  县
    $scope.$watch("entity.cityid",function(newValue,oldValue){
        addressService.findAreaById(newValue).success(function(response){
            $scope.areas = response;
        });
    });

    $scope.save = function () {
        addressService.save($scope.entity).success(function(response){
            if (response.flag = true ){
                alert(response.message)
                //重新查一下数据库刷新页面
                $scope.findAddressList();
            }else {
                alert(response.message)
            }
        })
    }

    $scope.showName=function(){
        loginService.showName().success(
            function(response){
                $scope.loginName=response.loginName;
            }
        );
    }

});