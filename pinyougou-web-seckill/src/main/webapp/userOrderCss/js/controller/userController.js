 //控制层 
app.controller('userController' ,function($scope,$controller,$http   ,userService){
	$scope.entity = {};
	//注册用户
	$scope.reg=function(){
		
		//比较两次输入的密码是否一致
		if($scope.password!=$scope.entity.password){
			alert("两次输入密码不一致，请重新输入");
			$scope.entity.password="";
			$scope.password="";
			return ;			
		}
		//新增
		userService.add($scope.entity,$scope.smscode).success(
			function(response){
				alert(response.message);
			}		
		);
	}
    
	//发送验证码
	$scope.sendCode=function(){
		if($scope.entity.phone==null || $scope.entity.phone==""){
			alert("请填写手机号码");
			return ;
		}//
		//商家后台
		//运营商后台 能前端就前端判断 后端不判断  员工用
		//网站前台  消费者 前端 美  后端 安全
		
		userService.sendCode($scope.entity.phone  ).success(
			function(response){
				alert(response.message);
			}
		);		
	}

    $scope.findSeckillOrderByUserId=function(){

        userService.findSeckillOrderByUserId( ).success(
            function(response){
                $scope.seckillOrderList = response;
            }
        );
    }

    $scope.findSeckillOrderByUserId=function(){
        $http.get('http://localhost:9105/seckillOrder/findSeckillOrderByUserId.do',{'withCredentials':true} ).success(function(response){
            	alert(1);
                $scope.seckillOrderList = response;
            }
        );

    }

    $scope.cancelSeckillOrder=function(id){
		alert("取消订单事件触发成功");
        userService.cancelSeckillOrder(id).success(function (response) {
			if(response.flag){


				location.reload();
			}else{
				alert("操作异常");
			}
        })
    }
});
