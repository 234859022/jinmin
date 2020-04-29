

(function() {
	var id=getUrlParam("id");
	var initdata={};
    
    if(id){
    	loadData();
    }else{
    	initdata.machineId=$("#machineId").val();
    	initdata.machineName=$("#machineName").val();
    	initdata.machineType=$("#machineType").val();
    	initdata.machineStatus=$("#machineStatus").val();
    	initdata.machineIsDelete=$("#machineIsDelete").val();
    	initdata.machineIsFault=$("#machineIsFault").val();
    	initdata.machineFaultReason=$("#machineFaultReason").val();
    }
    
    $("#submitbtn").click(function(){
    	var request_url="/yhsj/machine/add";
    	if(id){
    		request_url="/yhsj/machine/update";
    	}
    	var machine_name = $("#machineName").val();
    	var machine_type = $("#machineType").val();
    	var machine_status = $("#machineStatus").val();
    	var machine_is_delete = $("#machineIsDelete").val();
    	if(machine_name==null||machine_name==''){
			layer.msg("机器名称为必填内容", {icon: 2,time:1000});
			return;
    	}
    	if(machine_type==null||machine_type==''){
			layer.msg("机器类型必选", {icon: 2,time:1000});
			return;
    	}
    	if(machine_status==null||machine_status==""){
			layer.msg("机器状态必选", {icon: 2,time:1000});
			return;
    	}
    	if(machine_is_delete==null||machine_is_delete==''){
			layer.msg("删除状态必选", {icon: 2,time:1000});
			return;
    	}
    	$.ajax({ 
    		url: request_url,
    		data:$('#machineinfo').serialize(),
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				parent.$("#grid-table-machine").jqGrid('setGridParam',{
    		               datatype:'json',
    		                postData:{}, //发送数据
    		                page:1
    		            }).trigger("reloadGrid");
    				parent.layer.closeAll();
    			}else{
    				layer.alert(result.message);
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	parent.layer.closeAll();
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function loadData(){
    	$.ajax({ 
    		url: "/yhsj/machine/detail",
    		data:{
    			id:id
    		},
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				if(result.data){
    					initdata=result.data;
    					initLoadData(initdata);
    				}
    			}
    		}
    	});
    }
    
    function initLoadData(initdata){
    	$("#machineId").val(initdata.machineId);
		$("#machineName").val(initdata.machineName);
		$("#machineType").val(initdata.machineType);
		$("#machineStatus").val(initdata.machineStatus);
		$("#machineIsDelete").val(initdata.machineIsDelete);
		$("#machineIsFault").val(initdata.machineIsFault);
		$("#machineFaultReason").val(initdata.machineFaultReason);
    }

})();

