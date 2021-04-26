//服务层
app.service('uploadService', function ($http) {
    this.upload = function () {

        let formData=new FormData();
        formData.append("imgFile",file.files[0])
        return $http(
            {
                url: "../upload",
                method: "POST",
                data: formData,
                headers: {"Content-Type": undefined},
                transformRequest: angular.identity  //表单序列化
            }
        )
    }


});
