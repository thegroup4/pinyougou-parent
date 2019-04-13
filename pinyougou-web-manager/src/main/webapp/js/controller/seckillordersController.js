 //控制层 
app.controller('seckillordersController' ,function($scope,$controller,seckillordersService){
	
	$controller('baseController',{$scope:$scope});//继承

	//分页
	$scope.findPage=function(page,rows){
        seckillordersService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
        seckillordersService.dele( $scope.selectIds ).success(
			function(response){
				if(response.flag){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	}

    $scope.status = ["未支付","已支付","已超时"];
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){
        seckillordersService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};
});	
