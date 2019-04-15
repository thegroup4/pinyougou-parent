//控制层
app.controller('itemCatController', function ($scope, $controller, itemCatServices) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        itemCatServices.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        itemCatServices.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        itemCatServices.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = itemCatServices.update($scope.entity); //修改
        } else {
            serviceObject = itemCatServices.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.flag) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        itemCatServices.dele($scope.selectIds).success(
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
        itemCatServices.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    // 根据父ID查询分类
    $scope.findByParentId = function (parentId) {
        itemCatServices.findByParentId(parentId).success(function (response) {
            $scope.list = response;
        });
    }

    // 定义一个变量记录当前是第几级分类
    $scope.grade = 1;

    $scope.setGrade = function (value) {
        $scope.grade = value;
    }

    $scope.selectList = function (p_entity) {

        if ($scope.grade == 1) {
            $scope.entity_1 = null;
            $scope.entity_2 = null;
        }
        if ($scope.grade == 2) {
            $scope.entity_1 = p_entity;
            $scope.entity_2 = null;
        }
        if ($scope.grade == 3) {
            $scope.entity_2 = p_entity;
        }

        $scope.findByParentId(p_entity.id);
    }

    // 查询一级分类列表:
    $scope.selectItemCat1List = function () {
        itemCatServices.findByParentId(0).success(function (response) {
            $scope.itemCat1List = response;
        });

    }

    // 查询二级分类列表:
    $scope.$watch("entity.goods.category1Id", function (newValue, oldValue) {
        itemCatServices.findByParentId(newValue).success(function (response) {
            $scope.itemCat2List = response;
        });
    });

    /*// 查询模块ID
    $scope.$watch("entity.goods.category3Id",function(newValue,oldValue){
        itemCatServices.findOne(newValue).success(function(response){
            $scope.entity.goods.typeTemplateId = response.typeId;
        });
    });*/

    $scope.save = function () {
        itemCatServices.save($scope.entity,$scope.entity.goods.category2Id).success(function (response) {
                if (response.flag) {
                    // 保存成功
                    alert(response.message);
                    /* $scope.reloadList();*/
                } else {
                    // 保存失败
                    alert(response.message);
                }
            }
        );
    }


});
