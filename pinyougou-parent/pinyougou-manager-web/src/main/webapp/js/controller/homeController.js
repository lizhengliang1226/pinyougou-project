//控制层
app.controller('homeController', function ($scope, $controller, homeService) {

    $scope.orderNums = 0
    $scope.orderNum = function () {
        homeService.findOrderNum().success(function (res) {
            $scope.orderNums = res;
        })
    }

});	
