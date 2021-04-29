app.controller("contentController",function ($scope,contentService) {
    $scope.contentList=[]
    $scope.findByCategoryId=function (id) {
        contentService.findByCategoryId(id).success(function (res) {
            $scope.contentList[id]=res
        })
    }
    $scope.search=function () {
        let keywords=$scope.keywords
        location.href="http://localhost:9104/search.html#?keywords="+keywords
    }
})