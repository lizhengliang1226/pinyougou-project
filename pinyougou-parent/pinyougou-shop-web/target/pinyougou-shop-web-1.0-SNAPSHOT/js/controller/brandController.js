app.controller("brandController", function ($scope, $controller, brandService) {
    $controller("baseController", {$scope: $scope})
    $scope.findAll = function () {
        brandService.findAll().success(function (data) {
            $scope.list = data.rows;
            $scope.paginationConf.totalItems = data.total;
        })
    }
    // $scope.findPage = (page, size) => {
    //     brandService.findPage(page, size).success((data) => {
    //         $scope.list = data.rows;
    //         $scope.paginationConf.totalItems = data.total;
    //     })
    // }
    $scope.findPage = function (page, rows) {
        brandService.pageCondition(page, rows, $scope.searchEntity).success(function (data) {
            $scope.list = data.rows;
            $scope.paginationConf.totalItems = data.total;
        })
    }

    $scope.save = function () {
        let methodName = $scope.entity.id != null ? "updateBrand" : "addBrand";
        brandService.save(methodName, $scope.entity).success(function (data) {
            $scope.validData(data);
        })
    }
    $scope.findById = function (id) {
        brandService.findById(id).success(function (data) {
            $scope.entity = data;
        })
    }
    // $scope.update = function () {
    //     brandService.update($scope.entity).success(function (data) {
    //         $scope.validData(data);
    //     })
    // }

    $scope.deleteBrands = function () {
        let res = confirm("确定删除吗？")
        if (res) {
            brandService.deleteBrands($scope.selectIds).success(function (data) {
                $scope.validData(data);
                $scope.selectIds = [];
            })
            return;
        }
        $scope.deselect(".brand-list td:nth-child(2)");
        $scope.selectIds = [];
    }

})