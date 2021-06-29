 //控制层 
app.controller('tb_goodsController' ,function($scope,$controller , $location, tb_type_templateService, tb_item_catService, uploadService ,tb_goodsService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		tb_goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    

	//分页
	$scope.findPage = function (page, rows) {
		tb_goodsService.search( page, rows,$scope.searchEntity).success(
			function (response) {
				$scope.list = response.rows;
				$scope.paginationConf.totalItems = response.total;//更新总记录数
			}
		);
	}
	$scope.selectItemCat1List = function () {
		tb_item_catService.findByParentId(0).success(function (res) {
			$scope.itemCat1List = res
		})
	}

	//查询实体
	$scope.entity = {goodsDesc: {itemImages: [], specificationItems: []}}
	$scope.findOne = function () {
		let id = $location.search()["id"]
		if (id === null) {
			return
		}
		tb_goodsService.findOne(id).success(
			function (response) {
				$scope.entity = response;
				//商品介绍
				editor.html($scope.entity.goodsDesc.introduction)
				//商品图片
				$scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages)
				$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.entity.goodsDesc.customAttributeItems)
				$scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems)
				for (let i = 0; i < $scope.entity.itemList.length; i++) {
					$scope.entity.itemList[i].spec = JSON.parse($scope.entity.itemList[i].spec)
				}
			}
		);
	}
	//保存 
	$scope.save=function(){
		let serviceObject;//服务层对象
		if($scope.entity.id!=null){//如果有ID
			serviceObject=tb_goodsService.update( $scope.entity );
		}else{
			serviceObject=tb_goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		tb_goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	$scope.aduitStatus = ["未审核", "已审核", "审核未通过", "已关闭"]
	$scope.itemCatList = []
	$scope.findItemCatList = function () {
		tb_item_catService.findAll().success(function (res) {
			for (let i = 0; i < res.length; i++) {
				$scope.itemCatList[res[i].id] = res[i].name
			}
		})
	}
	$scope.updateStatus=function (status) {
		tb_goodsService.updateStatus($scope.selectIds,status).success(function (res) {
			if(res.success){
				$scope.reloadList()
				$scope.selectIds=[]
			}else{

				alert(res.message)
			}
		})
	}
});	
