app.controller("indexController", function ($scope, $controller, loginService) {
    $scope.findLoginName = function () {
        loginService.loginName().success(function (res) {
            $scope.loginName = res.loginName;
        })
    }
})