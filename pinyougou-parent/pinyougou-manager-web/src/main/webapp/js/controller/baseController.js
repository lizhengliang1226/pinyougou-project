app.controller("baseController", function ($scope) {
    $scope.selectIds = [];
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [5, 10, 20, 30, 40],
        onChange: function () {
            $scope.reloadList();
            document.querySelector("#selall").checked = false;
        }
    }
    $scope.searchEntity = {};
    $scope.updateSelection = function ($event, id) {
        if ($event.target.checked) {
            $scope.selectIds.push(id);
        } else {
            $scope.selectIds.splice($scope.selectIds.indexOf(id), 1);
        }
    }

    $scope.reloadList = function () {
        $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

    $scope.validData = function (data) {
        if (data.success) {
            $scope.reloadList();
        } else {
            alert(data.message);
        }
    }

    // $scope.update = function () {
    //     brandService.update($scope.entity).success(function (data) {
    //         $scope.validData(data);
    //     })
    // }
    $scope.deselect=function (selector){
        document.querySelectorAll(selector).forEach(id => {
            if ($scope.selectIds.indexOf(parseInt(id.innerHTML)) !== -1) {
                id.parentNode.children[0].children[0].checked = false;
            }
        })
        document.querySelector("#selall").checked=false;
    }
    $scope.selectAll = function ($event,selector) {
        let trs= document.querySelectorAll(selector);
        if ($event.target.checked) {
            $scope.selectIds = [];
            trs.forEach(tr=>{
                $scope.selectIds.push(parseInt(tr.children[1].innerHTML))
            })
        }else{
            $scope.selectIds=[];
        }
        trs.forEach(tr=>{
            tr.children[0].children[0].checked=$event.target.checked;
        })
    }
    $scope.jsonToString=function (json,key) {
        let obj = JSON.parse(json);
        if (obj.length===0){
            return "无"
        }
        let val=[];
        obj.forEach(o=>{
            val.push(o[key]);
        })
        return val.join(", ");
    }
})