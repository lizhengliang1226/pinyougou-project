app.controller("searchController", function ($scope, searchService) {
    $scope.searchMap = {keywords: "", brand: "", category: "", spec: {},price:""}
    $scope.search = function () {
        searchService.search($scope.searchMap).success(function (res) {
            $scope.resultMap = res
        })
    }
    //添加搜索项
    $scope.addSearchItem = function (key, value) {
        if (key === "brand" || key === "category"||key==="price") {
            $scope.searchMap[key] = value;
        } else {
            $scope.searchMap.spec[key]=value
        }
        $scope.search()
    }
    //删除搜索选项
    $scope.removeSearchItem=function (key){
        if (key === "brand" || key === "category"||key==="price") {
            $scope.searchMap[key] = "";
        } else {
           delete $scope.searchMap.spec[key]
        }
        $scope.search()
    }
})