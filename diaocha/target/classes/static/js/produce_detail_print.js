(function() {
	var purchase_detail_id=getUrlParam("purchase_detail_id");
	var task_id=getUrlParam("task_id");
	var initdata={};
    if(purchase_detail_id){
    	loadData();
    }
    $("#submitbtn").click(function(){
    	print();
    });
    
    $("#cancelbtn").click(function(){
    	parent.layer.closeAll();
    });  
    
    function print(){
       //备份body
       var body = window.document.body.innerHTML;
       //获取要打印的内容
       var form = $("#printcontent").html();
       window.document.body.innerHTML = form;
       //调用window的打印功能
       window.print();
       //恢复原内容
       window.document.body.innerHTML = body;
       parent.layer.closeAll();
    }
    

    
    function loadData(){
    	$.ajax({ 
    		url: "/yhsj/machine/task/getDetailByPurchaseDetailId",
    		data:{
    			purchase_detail_id:purchase_detail_id,
    			task_id:task_id
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
    	if(initdata.produce_detail){
    		$("#produceDetailId").html(initdata.produce_detail.produce_detail_id);
        	$("#produceDetailSpecName").html(initdata.produce_detail.produce_detail_spec_name);
        	$("#produceDetailUserName").html(initdata.produce_detail.produce_detail_user_name);
        	$("#produceDetailColorType").html(initdata.produce_detail.produce_detail_color_type);
        	$("#produceDetailQie").html(initdata.produce_detail.produce_detail_qie);
        	$("#produceDetailGe").html(initdata.produce_detail.produce_detail_ge);
        	$("#produceDetailSlZl").html(initdata.produce_detail.produce_detail_sl_zl);
        	$("#produceDetailCszl").html(initdata.produce_detail.produce_detail_cszl);
        	$("#produceDetailCl").html(initdata.produce_detail.produce_detail_cl);
        	$("#produceDetailXb").html(initdata.produce_detail.produce_detail_xb);
        	$("#produceDetailDb").html(initdata.produce_detail.produce_detail_db);
        	$("#produceDetailXie").html(initdata.produce_detail.produce_detail_xie);
        	$("#produceDetailCd").html(initdata.produce_detail.produce_detail_cd);
        	$("#produceDetailMonth").html(initdata.produce_detail.produce_detail_month);
        	$("#produceDetailDay").html(initdata.produce_detail.produce_detail_day);
    	}
    	
    	if(initdata.machineTask){
        	$("#produceDetailMachineName").html(initdata.machineTask.machineName);
        	$("#produceDetailSlZl").html("数量:"+initdata.machineTask.productCount+',重量:'+initdata.machineTask.productWeight);
    	}
    	
    	if(initdata.taskIngrediients){
    		var htmltext="";
    		$.each(initdata.taskIngrediients,function(index,item){
    			htmltext+="<div>"+item.ingredientName+" * "+item.ingredientNum+"</div>";
    		});
    		$("#produceDetailPl").html(htmltext);   		
    	}
    	
    }
})();