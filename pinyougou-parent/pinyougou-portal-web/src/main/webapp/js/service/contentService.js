app.service("contentService",function ($http) {
    this.findByCategoryId=function (id) {
        return $http.get("/content/findByCategoryId?categoryId="+id)
    }
})