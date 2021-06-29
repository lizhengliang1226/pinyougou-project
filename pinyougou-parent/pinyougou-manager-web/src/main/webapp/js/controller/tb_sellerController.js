//控制层
app.controller('tb_sellerController', function ($scope, $controller, tb_sellerService) {

    $controller('baseController', {$scope: $scope});//继承
    $scope.sellerStatus=["未审核","审核通过","审核未通过","关闭商家"]
    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        tb_sellerService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        tb_sellerService.findPage(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        tb_sellerService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = tb_sellerService.update($scope.entity);
        } else {
            serviceObject = tb_sellerService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
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
        tb_sellerService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                }
            }
        );
    }
    $scope.updateStatus = function (sellerId, status) {
        tb_sellerService.updateStatus(sellerId, status).success(function (res) {
            alert(res.message);
            //重新查询
            $scope.reloadList();//重新加载
        })
    }
});	
