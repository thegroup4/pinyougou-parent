//控制层 
app.controller('typeTemplateController' ,function($scope,$controller,brandService ,specService,typeTempService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		typeTempService.findAll1().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
    $scope.status = ["未审核", "审核通过", "审核未通过", "关闭"];
	$scope.findPage=function(page,rows,status){
		typeTempService.findPage1(page,rows,status).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		typeTempService.findOne1(id).success(
			function(response){
				$scope.entity= response;	
				// eval()   JSON.parse();   
				$scope.entity.brandIds = JSON.parse($scope.entity.brandIds);
				
				$scope.entity.specIds = JSON.parse($scope.entity.specIds);
				
				$scope.entity.customAttributeItems = JSON.parse($scope.entity.customAttributeItems);
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=typeTempService.update1( $scope.entity ); //修改
		}else{
			serviceObject=typeTempService.add1( $scope.entity  );//增加
		}				
		serviceObject.success(
			function(response){
				if(response.flag){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		typeTempService.dele1( $scope.selectIds ).success(
			function(response){
				if(response.flag){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索

	$scope.search=function(page,rows){
		typeTempService.search1(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
	$scope.brandList={data:[]}
	// 查询关联的品牌信息:
    $scope.findBrandList = function(){
        brandService.selectOptionList().success(function(response){
            $scope.brandList = {data:response};
        });// response Java那边的返回值 是什么POJO呢？ 返回值:List<Map>    入参：无  URL
    }

    $scope.specList={data:[]}
    // 查询关联的规格信息:
    $scope.findSpecList = function(){
        specService.selectOptionList().success(function(response){
            $scope.specList = {data:response};
        });
    }
	
	//给扩展属性添加行
	//$scope.entity={customAttributeItems:[]};


	$scope.addTableRow = function(){
		$scope.entity.customAttributeItems.push({});
	}
	
	$scope.deleteTableRow = function(index){
		$scope.entity.customAttributeItems.splice(index,1);
	}
});	
