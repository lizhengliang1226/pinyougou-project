//控制层
app.controller('tb_sellerController', function ($scope, tb_sellerService) {


    $scope.entity = {}
    $scope.passwd = {}
    //查询实体
    $scope.findOne = function () {
        tb_sellerService.findOne().success(
            function (response) {
                $scope.entity = response;
            }
        );
    }
    $scope.updatePasswd = function () {
        if ($scope.passwd.old == null) {
            alert("请输入旧密码！");
            return;
        }
        if ($scope.passwd.new == null) {
            alert("请输入新密码！");
            return;
        }
        if ($scope.passwd.confirmNew == null) {
            alert("请输入确认密码！");
            return;
        }
        if ($scope.passwd.new !== $scope.passwd.confirmNew) {
            alert("两次密码输入不相同！");
            return;
        }
        if ($scope.passwd.old === $scope.passwd.new) {
            alert("新密码与旧密码不能相同！");
            return;
        }
        tb_sellerService.updatePasswd($scope.passwd).success(function (res) {
            alert(res.message);
            location.href="password.html"
        })
    }
    //保存
    $scope.add = function () {
        tb_sellerService.add($scope.entity).success(
            function (response) {
                if (response.success) {
                    //重新查询
                    // $scope.reloadList();//重新加载
                    alert("申请成功，请等待管理员审核")
                    location.href = "shoplogin.html"
                } else {
                    alert(response.message);
                }
            }
        );
    }
    //保存
    $scope.update = function () {
        tb_sellerService.update($scope.entity).success(
            function (response) {
                if (response.success) {
                    //重新查询
                    // $scope.reloadList();//重新加载
                    alert("更新信息成功！")
                } else {
                    alert(response.message);
                }
            }
        );
    }


});	
