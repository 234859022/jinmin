

(function() {
	var id=getUrlParam("id");
	var opertype=getUrlParam("opertype");
	var initdata={};
    
    if(id){
    	loadData();
    }else{
    	initdata.produceDetailSpecName=$("#produceDetailSpecName").val();
    	initdata.produceDetailUserName=$("#produceDetailUserName").val();
    	initdata.produceDetailColorType=$("#produceDetailColorType").val();
    	initdata.produceDetailMachineName=$("#produceDetailMachineName").val();
    	initdata.produceDetailQie=$("#produceDetailQie").val();
    	initdata.produceDetailGe=$("#produceDetailGe").val();
    	initdata.produceDetailSlZl=$("#produceDetailSlZl").val();
    	initdata.produceDetailCszl=$("#produceDetailCszl").val();
    	initdata.produceDetailCl=$("#produceDetailCl").val();
    	initdata.produceDetailXb=$("#produceDetailXb").val();
    	initdata.produceDetailDb=$("#produceDetailDb").val();
    	initdata.produceDetailXie=$("#produceDetailXie").val();
    	initdata.produceDetailCd=$("#produceDetailCd").val();
    	initdata.produceDetailPl=$("#produceDetailPl").val();
    	initdata.produceDetailMonth=$("#produceDetailMonth").val();
    	initdata.produceDetailDay=$("#produceDetailDay").val();
    }
    
    $("#submitbtn").click(function(){
    	var request_url="/yhsj/produce/detail/add";
    	if(id){
    		request_url="/yhsj/produce/detail/update";
    	}
    	var produceDetailUserName = $("#produceDetailUserName").val();
    	var produceDetailSpecName = $("#produceDetailSpecName").val();
    	var produceDetailColorType = $("#produceDetailColorType").val();
    	var produceDetailMachineName = $("#produceDetailMachineName").val();
    	var produceDetailQie = $("#produceDetailQie").val();
    	var produceDetailGe = $("#produceDetailGe").val();
    	var produceDetailSlZl = $("#produceDetailSlZl").val();
    	var produceDetailCszl = $("#produceDetailCszl").val();
    	var produceDetailCl = $("#produceDetailCl").val();
    	var produceDetailXb = $("#produceDetailXb").val();
    	var produceDetailDb = $("#produceDetailDb").val();
    	var produceDetailXie = $("#produceDetailXie").val();
    	var produceDetailCd = $("#produceDetailCd").val();
    	var produceDetailPl = $("#produceDetailPl").val();
    	if(produceDetailUserName==null||produceDetailUserName==''){
			layer.msg("客户必填", {icon: 2,time:1000});
			return;
    	}
    	if(produceDetailSpecName==null||produceDetailSpecName==''){
			layer.msg("规格必填", {icon: 2,time:1000});
			return;
    	}
    	if(produceDetailColorType==null||produceDetailColorType==""){
			layer.msg("颜色必填", {icon: 2,time:1000});
			return;
    	}
    	if(produceDetailMachineName==null||produceDetailMachineName==''){
			layer.msg("机器必填", {icon: 2,time:1000});
			return;
    	}
    	$.ajax({ 
    		url: request_url,
    		data:$('#produceDetailinfo').serialize(),
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				parent.$("#grid-table-produceDetail").jqGrid('setGridParam',{
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
    		url: "/yhsj/produce/detail/detail",
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
    	$("#produceDetailId").val(initdata.produceDetailId);
    	$("#produceDetailSpecName").val(initdata.produceDetailSpecName);
    	$("#produceDetailUserName").val(initdata.produceDetailUserName);
    	$("#produceDetailColorType").val(initdata.produceDetailColorType);
    	$("#produceDetailMachineName").val(initdata.produceDetailMachineName);
    	$("#produceDetailQie").val(initdata.produceDetailQie);
    	$("#produceDetailGe").val(initdata.produceDetailGe);
    	$("#produceDetailSlZl").val(initdata.produceDetailSlZl);
    	$("#produceDetailCszl").val(initdata.produceDetailCszl);
    	$("#produceDetailCl").val(initdata.produceDetailCl);
    	$("#produceDetailXb").val(initdata.produceDetailXb);
    	$("#produceDetailDb").val(initdata.produceDetailDb);
    	$("#produceDetailXie").val(initdata.produceDetailXie);
    	$("#produceDetailCd").val(initdata.produceDetailCd);
    	$("#produceDetailPl").val(initdata.produceDetailPl);
    	$("#produceDetailMonth").val(initdata.produceDetailMonth);
    	$("#produceDetailDay").val(initdata.produceDetailDay);
    	if(opertype){
    		$("#produceDetailSpecName").attr("readOnly","readOnly");
        	$("#produceDetailUserName").attr("readOnly","readOnly");
        	$("#produceDetailColorType").attr("readOnly","readOnly");
        	$("#produceDetailMachineName").attr("readOnly","readOnly");
        	$("#produceDetailQie").attr("readOnly","readOnly");
        	$("#produceDetailGe").attr("readOnly","readOnly");
        	$("#produceDetailSlZl").attr("readOnly","readOnly");
        	$("#produceDetailCszl").attr("readOnly","readOnly");
        	$("#produceDetailCl").attr("readOnly","readOnly");
        	$("#produceDetailXb").attr("readOnly","readOnly");
        	$("#produceDetailDb").attr("readOnly","readOnly");
        	$("#produceDetailXie").attr("readOnly","readOnly");
        	$("#produceDetailCd").attr("readOnly","readOnly");
        	$("#produceDetailPl").attr("readOnly","readOnly");
        	$("#produceDetailMonth").attr("readOnly","readOnly");
        	$("#produceDetailDay").attr("readOnly","readOnly");
        	$("#submitbtn").hide();
        	$("#resetbtn").hide();
        	$("#cancelbtn").hide();
    	}
    }

})();

