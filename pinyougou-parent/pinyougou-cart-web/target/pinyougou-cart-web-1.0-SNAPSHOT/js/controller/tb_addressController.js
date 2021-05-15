 //控制层 
app.controller('tb_addressController' ,function($scope,$controller   ,tb_addressService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
	$scope.findAddressList=function () {
		tb_addressService.findAddressList().success(function (res) {
			console.log(res)
			$scope.addressList=res
		})
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=tb_addressService.update( $scope.entity ); 修改  
		}else{
			serviceObject=tb_addressService.add( $scope.entity  );//增加 
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
		tb_addressService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
    
});	
