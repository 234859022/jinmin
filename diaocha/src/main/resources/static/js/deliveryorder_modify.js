(function() {
	var delivery_id=getUrlParam("delivery_id");
	var status=getUrlParam("status");
	if(delivery_id){
		$("#delivery_id").val(delivery_id);
	}
	if(status){
		$("#status").val(status);
	}
    
    $("#submitbtn").click(function(){
    	$.ajax({ 
    		url: "/yhsj/delivery/order/updateDeliveryOrder",
    		data:$('#deliveryModifyForm').serialize(),
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				layer.msg("修改状态成功", {icon: 1,time:1000},function(){
    					parent.$("#grid-table-deliveryorder").jqGrid().trigger("reloadGrid");
        				parent.layer.closeAll();
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	parent.$("#grid-table-deliveryorder").jqGrid().trigger("reloadGrid");
		parent.layer.closeAll();
    });
    
})();