//控制层
app.controller('indexController', function ($scope, loginService) {


    $scope.loginName=function () {
        loginService.loginName().success(function (res) {
            $scope.loginName=res.username
        })
    }
});
