app.controller('specController', function ($scope, $controller, specService) {

    $controller('baseController', {$scope: $scope});//继承

    //分页
    $scope.status = ["未审核", "审核通过", "审核未通过", "关闭"];
    $scope.search = function (page, rows,status) {
        specService.search(page, rows,status).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }


    $scope.addTableRow = function () {
        $scope.entity.specificationOptionList.push({});
    }

    $scope.deleteTableRow = function (index) {
        $scope.entity.specificationOptionList.splice(index, 1);
    }
    //保存
        $scope.save=function(){
            var serviceObject;//服务层对象
            if($scope.entity.id!=null){//如果有ID
                serviceObject=specService.update( $scope.entity ); //修改
            }else{
                serviceObject=specService.add( $scope.entity  );//增加
            }
            serviceObject.success(
                function(response){
                    if(response.flag == true){
                        alert(response.message);
                        //重新查询
                        $scope.reloadList();//重新加载
                    }else{
                        alert(response.message);
                    }
                }
            );
        }
/*    $scope.add = function () {
        specService.add($scope.entity).success(
            function (response) {
                if (response.flag == true) {
                    alert(response.message)
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            })
    }*/

    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        specService.dele($scope.selectIds).success(
            function (response) {
                if (response.flag) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        specService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        specService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        specService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        specService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

})