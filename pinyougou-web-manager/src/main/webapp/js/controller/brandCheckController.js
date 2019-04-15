// 定义控制器:
app.controller("brandCheckController", function ($scope, $controller, $http, brandService) {
    // AngularJS中的继承:伪继承
    $controller('baseController', {$scope: $scope});


    // 假设定义一个查询的实体：searchEntity
    $scope.searchEntity = {};
    $scope.search = function (page, rows) {
        // 向后台发送请求获取数据:
        brandService.searchStatus(page, rows, $scope.searchEntity).success(function (response) {
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    }
    /*  // 假设定义一个查询的实体：searchEntity
      $scope.search = function (page, rows) {
          var object;
          // 向后台发送请求获取数据:
          if ($scope.entity.brandStatus != null) {
              object = brandService.searchStatus(page, rows, $scope.searchEntity);
          } else {
              object = brandService.search(page, rows, $scope.searchEntity);
          }
          object.success(function (response) {
              $scope.paginationConf.totalItems = response.total;
              $scope.list = response.rows;
          });
      }
  */
    // 审核的方法:
    // 显示状态
    $scope.status = ["未审核", "审核通过", "审核未通过", "关闭"];
    $scope.updateStatus = function (status) {
        brandService.updateStatus($scope.selectIds, status).success(function (response) {
            if (response.flag) {
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
                alert(response.message)
            } else {
                alert(response.message);
            }
        });
    }

});
