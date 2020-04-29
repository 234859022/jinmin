(function() {
	var task_id=getUrlParam("task_id");
	var initdata={};
	var status = ["","未处理","进行中","已完成"]
	getMachineTaskDetail();
	function getMachineTaskDetail(){
    	$.ajax({ 
    		url: "/yhsj/machine/task/getMachineTaskDetail",
    		data:{
    			task_id:task_id
    		},
    		async:false,
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
    
    $("#printbtn2").click(function(){
    	print();
    });
    
    $("#cancelbtn2").click(function(){
    	parent.layer.closeAll();
    });  
    
    function print(){
       //备份body
       var body = window.document.body.innerHTML;
       //获取要打印的内容
       var form = $("#printdetail").html();
       window.document.body.innerHTML = form;
       //调用window的打印功能
       window.print();
       //恢复原内容
       window.document.body.innerHTML = body;
       parent.layer.closeAll();
    }
    
    function initLoadData(initdata){
    	var main = initdata.main;
    	var detail = initdata.detail;
    	var html = "";
    	var detailhtml = '';
    	$("#machineName").html("机器："+main.machineName);
    	$("#purchaseNo").html("采购编号："+main.purchaseNo);
    	
    	html += '<div class="item">';
		html += '<div>';
		html += '<span>'+main.productName+'</span>';
		html += '<span>规格</span>';
		html += '<span>'+main.tpName+'</span>';
		html += '</div>';
		html += '<div>';
		html += '<span>数量：'+main.productCount+'</span>';
		html += '<span>重量：'+main.productWeight+'</span>';
		html += '<span>实际生产重量：'+main.actualWeight+'</span>';
		html += '</div>';
		html += '</div>';
		$("#detaillist").html(html);
		
		$("#taskStatus").html("状态："+status[main.taskStatus]);
		if(main.taskStartTime){
			$("#taskStartTime").html("开始时间："+timeFormat(main.taskStartTime));
		}
		if(main.taskEndTime){
			$("#taskEndTime").html("开始时间："+timeFormat(main.taskEndTime));
		}
		
		$.each(detail,function(index,item){ 
			if(item){
				detailhtml += '<span>'+item.ingredientName+" "+item.ingredientNum+'</span>';
			}
		});
		$("#ingrediient").html(detailhtml);
    }
    
    function timeFormat(val){
		return (new Date(val)).Format("yyyy-MM-dd HH:mm:ss");
    }
    
})();