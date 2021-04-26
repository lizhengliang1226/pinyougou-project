 //控制层 
app.controller('tb_type_templateController' ,function($scope,$controller   ,tb_type_templateService,brandService,tb_specificationService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		tb_type_templateService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){
		console.log("调用findPage")
		tb_type_templateService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		tb_type_templateService.findOne(id).success(
			function(response){
				$scope.entity= response;
				$scope.entity.brandIds=JSON.parse(response.brandIds)
				$scope.entity.specIds=JSON.parse(response.specIds)
				$scope.entity.customAttributeItems=JSON.parse(response.customAttributeItems)
				$scope.reloadList()
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=tb_type_templateService.update( $scope.entity );
		}else{
			serviceObject=tb_type_templateService.add( $scope.entity  );//增加 
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
		if ($scope.selectIds.length===0){
			alert("请选择要删除的编号！")
			return ;
		}
		let b = confirm("确定删除吗？");
		if (b){
			//获取选中的复选框
			tb_type_templateService.dele( $scope.selectIds ).success(
				function(response){
					if(response.success){
						$scope.reloadList();//刷新列表
					}
					$scope.selectIds=[];
				}
			);
			return;
		}
		$scope.deselect(".type-template-list td:nth-child(2)");
		$scope.selectIds=[];
	}
    $scope.brandList={data:[]}
    $scope.specificationList={data:[]}
	$scope.findBrandList=function () {
		 brandService.selectOptionList().success(function (res){
		 	$scope.brandList={data:res};
		 })
	}
	$scope.findSpecificationList=function () {
		tb_specificationService.selectSpecificationList().success(function (res) {
			$scope.specificationList={data:res}
		})
	}
	$scope.addTableRow=function () {
		$scope.entity.customAttributeItems.push({});
	}
	$scope.deleteTableRow=function (index){
		$scope.entity.customAttributeItems.splice(index,1)
	}
});
