 //控制层 
app.controller('userController' ,function($scope,$controller,userService){
	
	$controller('baseController',{$scope:$scope});//继承

	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
        userService.dele($scope.selectIds).success(
			function(response){
				if(response.flag){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	};
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){
        userService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
	// 显示状态
	$scope.status = ["冻结","激活"];
    $scope.sourceType = ["","PC","H5","Android","IOS","WeChat"]; // 会员来源：1:PC，2：H5，3：Android，4：IOS，5：WeChat
    $scope.sex=["","男","女"];

	
	// 用户冻结方法:
	$scope.updateStatus = function(status){
        userService.updateStatus($scope.selectIds,status).success(function(response){
			if(response.flag){
				$scope.reloadList();//刷新列表
				$scope.selectIds = [];
			}else{
				alert(response.message);
			}
		});
	}
});	
