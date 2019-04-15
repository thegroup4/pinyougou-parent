//服务层
app.service('itemCatServices', function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../itemCat/findAll.do');
    }
    //分页
    this.findPage = function (page, rows) {
        return $http.get('../itemCat/findPage.do?page=' + page + '&rows=' + rows);
    }
    //查询实体
    this.findOne = function (id) {
        return $http.get('../itemCat/findOne.do?id=' + id);
    }
    //增加
    this.add = function (entity) {
        return $http.post('../itemCat/add.do', entity);
    }
    //修改
    this.update = function (entity) {
        return $http.post('../itemCat/update.do', entity);
    }
    //删除
    this.dele = function (ids) {
        return $http.get('../itemCat/delete.do?ids=' + ids);
    }
    //搜索
    this.search = function (page, rows, searchEntity) {
        return $http.post('../itemCat/search.do?page=' + page + "&rows=" + rows, searchEntity);
    }
    //展示3级分类
    this.findByParentId = function (parentId) {
        return $http.get("../itemCat/findByParentId.do?parentId=" + parentId);
    }

    //分类添加保存
    this.save = function (entity, parent2) {
        return $http.post('../itemCat/save.do?parent2=' + parent2, entity);
    }


});
