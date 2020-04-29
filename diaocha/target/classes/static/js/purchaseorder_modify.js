(function() {
	var purchase_id=getUrlParam("purchase_id");
	var status=getUrlParam("status");
	if(purchase_id){
		$("#purchase_id").val(purchase_id);
	}
	if(status){
		$("#status").val(status);
	}
    
    $("#submitbtn").click(function(){
    	$.ajax({ 
    		url: "/yhsj/purchase/order/updatePurchaseOrder",
    		data:$('#purchaseModifyForm').serialize(),
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				layer.msg("修改状态成功", {icon: 1,time:1000},function(){
    					parent.$("#grid-table-purchaseorder").jqGrid().trigger("reloadGrid");
        				parent.layer.closeAll();
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	parent.$("#grid-table-purchaseorder").jqGrid().trigger("reloadGrid");
		parent.layer.closeAll();
    });
    
})();