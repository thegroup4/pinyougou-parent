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
                $scope.seckillOrderList = response;
            }
        );

        // $http({
        //     method: 'GET',
        //     url: 'http://localhost:9105/seckillOrder/findSeckillOrderByUserId.do',
        //     headers:{'Content-Type': 'application/json',
        //         "Access-Control-Allow-Origin": "*",
        //         'Accept': 'application/json'},
			// 	'withCredentials':true
        //
        // }).then(function successCallback(response) {
        //     alert(1);
        //     $scope.seckillOrderList = response;
        // }, function errorCallback(response) {
        //     // 请求失败执行代码
        //     console.log("--------2");
        // });




    }




});	
