//控制层
app.controller('tb_userController', function ($scope, tb_userService) {

    //注册
    $scope.save = function () {
        if ($scope.passwd !== $scope.entity.password) {
            alert("两次密码不相同，请重新输入")
            return
        }
        tb_userService.add($scope.entity,$scope.code).success(
            function (response) {
                alert(response.message);
            }
        );
    }

    $scope.sendCode=function () {
        if ($scope.entity.phone===null){
            alert("请输入手机号！")
            return
        }
        tb_userService.sendCode($scope.entity.phone).success(function (res) {
            alert(res.message);
        })
    }

});	
