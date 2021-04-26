app.service("brandService", function ($http) {
    this.save = function (methodName, entity) {
        return $http.post("../brand/" + methodName, entity)
    }
    this.findById = function (id) {
        return $http.get("../brand/findById?id=" + id)
    }
    // this.update = function (entity) {
    //     return $http.post("../brand/updateBrand", entity)
    // }
    this.deleteBrands = function (selectIds) {
        return $http.get("../brand/deleteBrand?ids=" + selectIds);
    }
    this.findAll = function () {
        return $http.get("../brand/brandList");
    }
    this.findPage = function (page, size) {
        return $http.get("../brand/pageBrandList?page=" + page + "&size=" + size)
    }
    this.pageCondition = function (page, rows, searchEntity) {
        return $http.post("../brand/pageConditionQuery?page=" + page + "&size=" + rows, searchEntity)
    }
    this.selectOptionList=function (){
        return $http.get("../brand/selectOptionList");
    }
})