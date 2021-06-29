app.controller("searchController", function ($scope, $location,searchService) {
    $scope.searchMap = {
        keywords: "",
        brand: "",
        category: "",
        spec: {},
        price: "",
        total: "",
        totalPages: "",
        pageNum: 1,
        pageSize: 10,
        sortType: "",
        sortField: ""
    }
    $scope.activeClass=["active",'','','','','']
    $scope.setActive=function (index) {
        $scope.activeClass=["",'','','','','']
        $scope.activeClass[index]="active"
    }
    $scope.loadKeywords=function () {
        $scope.searchMap.keywords=$location.search()["keywords"]
        if ($scope.searchMap.keywords!=null&&$scope.searchMap.keywords!==""){
            $scope.search();
        }
    }
    $scope.sortSearch = function (sortType, sortField) {
        $scope.searchMap.sortType = sortType
        $scope.searchMap.sortField = sortField
        $scope.search()
    }
    $scope.search = function () {
        $scope.searchMap.pageNum = 1
        searchService.search($scope.searchMap).success(function (res) {
            $scope.resultMap = res
        })
    }
    //添加搜索项
    $scope.addSearchItem = function (key, value) {
        if (key === "brand" || key === "category" || key === "price") {
            $scope.searchMap[key] = value;
        } else {
            $scope.searchMap.spec[key] = value
        }
        $scope.search()
    }
    //删除搜索选项
    $scope.removeSearchItem = function (key) {
        if (key === "brand" || key === "category" || key === "price") {
            $scope.searchMap[key] = "";
        } else {
            delete $scope.searchMap.spec[key]
        }
        $scope.search()
    }
    //分页查询
    $scope.searchPage = function () {
        if ($scope.searchMap.pageNum < $scope.resultMap.totalPages) {
            $scope.searchMap.pageNum++;
            searchService.search($scope.searchMap).success(function (res) {
                $scope.resultMap.rows = $scope.resultMap.rows.concat(res.rows)
            })
        }
    }
    $scope.keywordsIsBrand=function () {
        for (let i = 0; i < $scope.resultMap.brandList.length; i++) {
            if ($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].text)>=0){
                return true;
            }
        }
        return false;
    }
    $scope.goToDetail=function (goodsId) {
        
    }
})