//控制层
app.controller('itemController', function ($scope) {
    $scope.num = 1;
    $scope.addNum = function (x) {
        $scope.num = $scope.num + x
        if ($scope.num < 1) {
            $scope.num = 1
        }
    }
    $scope.specItem = {}
    $scope.selectSpec = function (name, value) {
        $scope.specItem[name] = value
        $scope.searchSku()
    }
    $scope.isSelected = function (name, value) {
        if ($scope.specItem[name] !== value) {
            return false;
        } else {
            return true
        }
    }
    $scope.loadSku = function () {
        $scope.sku = skuList[0]
        $scope.specItem = JSON.parse(JSON.stringify($scope.sku.spec))
    }
    $scope.searchSku=function () {
        for (let i = 0; i < skuList.length; i++) {
            if ($scope.matchObj(skuList[i].spec, $scope.specItem)) {
                $scope.sku=skuList[i]
                break
            }
        }

    }
    $scope.addToCart=function () {
        // alert($scope.sku.id)
        location.href="http://localhost:9106/cart.html#?itemId="+$scope.sku.id+"&num="+$scope.num;
    }
    $scope.matchObj = function (a, b) {
            //获取a和b的属性长度
            let propsA = Object.keys(a),
                propsB = Object.keys(b);
            if (propsA.length !== propsB.length) {
                return false;
            }
            for (let i = 0; i < propsA.length; i++) {
                let propName = propsA[i];
                //如果对应属性对应值不相等，则返回false
                if (a[propName] !== b[propName]) {
                    return false;
                }
            }
            return true;
    }
});
