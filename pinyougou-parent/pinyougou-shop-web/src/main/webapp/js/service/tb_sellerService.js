app.service('tb_sellerService', function ($http) {


    //查询实体
    this.findOne = function () {
        return $http.get('../tb_seller/findOne');
    }
    //增加
    this.add = function (entity) {
        return $http.post('../tb_seller/add.do', entity);
    }
    //修改
    this.update = function (entity) {
        return $http.post('../tb_seller/update.do', entity);
    }
    this.updatePasswd=function (passwd) {
        return $http.post("../tb_seller/confirmPasswd",passwd);
    }
});
