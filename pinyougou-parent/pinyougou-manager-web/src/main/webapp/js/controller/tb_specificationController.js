 //控制层 
app.controller('tb_specificationController' ,function($scope,$controller   ,tb_specificationService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		tb_specificationService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		tb_specificationService.findPage(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		tb_specificationService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.specification.id!=null){//如果有ID
			serviceObject=tb_specificationService.update( $scope.entity ); //修改
		}else{
			serviceObject=tb_specificationService.add( $scope.entity  );//增加 
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
	$scope.dele= function(){
		if ($scope.selectIds.length===0){
			alert("请选择要删除的编号！")
			return ;
		}
		let b = confirm("确定删除吗？");
		if (b){
			//获取选中的复选框
			tb_specificationService.dele( $scope.selectIds ).success(
				function(response){
					if(response.success){
						$scope.reloadList();//刷新列表
						$scope.selectIds=[];
					}
				}
			);
			return;
		}
		$scope.deselect(".specification-list td:nth-child(2)");
		$scope.selectIds=[];
	}
	$scope.addTableRow=function () {
		$scope.entity.specificationOptionList.push({});
	}
	$scope.deleteTableRow=function (index){
		$scope.entity.specificationOptionList.splice(index,1)
	}
    
});	
