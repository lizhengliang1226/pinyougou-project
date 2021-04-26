//控制层
app.controller('tb_goodsController', function ($scope, $controller, tb_type_templateService, tb_item_catService, uploadService, tb_goodsService) {

    $controller('baseController', {$scope: $scope});//继承
    $scope.imgEntity = {}
    $scope.uploadImg = function () {
        uploadService.upload().success(function (res) {
            if (res.error === 0) {
                $scope.imgEntity.url = res.url
            }
        })
    }
    $scope.selectItemCat1List = function () {
        tb_item_catService.findByParentId(0).success(function (res) {
            $scope.itemCat1List = res
        })
    }
    $scope.$watch("entity.goods.category1Id", function (newValue, oldValue) {
        tb_item_catService.findByParentId(newValue).success(function (res) {
            $scope.itemCat2List = res
        })
    })
    $scope.$watch("entity.goods.category2Id", function (newValue, oldValue) {
        tb_item_catService.findByParentId(newValue).success(function (res) {
            $scope.itemCat3List = res
        })
    })
    $scope.$watch("entity.goods.category3Id", function (newValue, oldValue) {
        tb_item_catService.findOne(newValue).success(function (res) {
            $scope.entity.goods.typeTemplateId = res.typeId
        })
    })
    $scope.$watch("entity.goods.typeTemplateId", function (newValue, oldValue) {
        tb_type_templateService.findOne(newValue).success(function (res) {
            $scope.typeTemplate = res
            $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds)
            $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems)
        })
        tb_type_templateService.findSpecList(newValue).success(function (res) {
            $scope.specList = res
        })
    })
    $scope.deleteImg = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    }
    $scope.entity = {goodsDesc: {itemImages: [], specificationItems: []}}
    $scope.addImageEntity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.imgEntity)
    }
    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        tb_goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        tb_goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        tb_goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        $scope.entity.goodsDesc.introduction = editor.html();//获取商品介绍
        tb_goodsService.add($scope.entity).success(
            function (response) {
                alert(response.message);
                if (response.success) {
                    $scope.entity = {}
                    editor.html("");
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        tb_goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                }
            }
        );
    }
    $scope.updateSpecItems = function ($event, name, value) {
        let searchObjectByKey = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems, "name", name);
        if (searchObjectByKey !== null) {
            if ($event.target.checked) {
                searchObjectByKey.values.push(value)
            } else {
                searchObjectByKey.values.splice(searchObjectByKey.values.indexOf(value), 1)
                if (searchObjectByKey.values.length === 0) {
                    $scope.entity.goodsDesc.specificationItems
                        .splice($scope.entity.goodsDesc.specificationItems.indexOf(searchObjectByKey), 1);
                }
            }
        } else {
            $scope.entity.goodsDesc.specificationItems.push({name: name, values: [value]})
        }
    }
    $scope.createItemList = function () {
        $scope.entity.itemList = [{spec: {}, price: 0, num: 100, status: "1", isDefault: "0"}]
        let items = $scope.entity.goodsDesc.specificationItems
        for (let i = 0; i < items.length; i++) {
            $scope.entity.itemList=addColumn( $scope.entity.itemList,items[i].name,items[i].values)
        }


    }
    let addColumn = function (list, name, values) {
        let newList = []
        for (let i = 0; i < list.length; i++) {
            let oldRow = list[i]
            for (let j = 0; j < values.length; j++) {
                let newRow = JSON.parse(JSON.stringify(oldRow))
                newRow.spec[name] = values[j]
                newList.push(newRow)
            }
        }
        return newList
    }
});	
