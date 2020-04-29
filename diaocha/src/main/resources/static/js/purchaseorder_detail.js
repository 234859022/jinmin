(function() {
	var lastrow;  //最后修改行号先定义一个全局变量
	var lastcell; //最后修改列号
	var grid_selector = "#grid-table-purchaseorderdetail";
	var purchase_id=getUrlParam("purchase_id");
	var initdata={};
	var detaildata;
	getPurchaseOrderDetail();
	
	function statusFormat( cellvalue, options, rowObject ){
    	var htmltext="";
        if(cellvalue == 1){
        	htmltext = "未处理";
        }else if(cellvalue == 2){
        	htmltext = "未处理吹膜";
        }else if(cellvalue == 3){
        	htmltext = "已吹膜处理";
        }else if(cellvalue == 4){
        	htmltext = "已吹膜完成";
        }else if(cellvalue == 5){
        	htmltext = "未处理封切";
        }else if(cellvalue == 6){
        	htmltext = "已封切处理";
        }else if(cellvalue == 7){
        	htmltext = "已封切完成";
        }else if(cellvalue == 8){
        	htmltext = "现货处理完成";
        }
        return htmltext;
    }
	
	function timeFormat( cellvalue, options, rowObject ){
		var html = "";
		if(cellvalue!=null && cellvalue!=""  && cellvalue!="null"){
			html = (new Date(cellvalue)).Format("yyyy-MM-dd HH:mm:ss");
		}
		return html;
    }
	
	function timeFormat2( cellvalue, options, rowObject ){
		var html = "";
		if(cellvalue!=null && cellvalue!=""  && cellvalue!="null"){
			html = (new Date(cellvalue)).Format("yyyy-MM-dd HH:mm:ss");
		}
		return html;
    }
	
	 /**
     * 显示表格
     * @param params
     */
    function showTable() {
    	$(grid_selector).jqGrid({
            height: 300,
            width:1200,
            altRows:true,//隔行变色
            multiselect:true,
            autowidth: false,
            datatype:"local",
            altclass:'',//用于交替变色的class属性
            colNames:['产品名称', '规格', '价格' , '数量', '总价', '状态', '吹膜机器', '吹膜时间' , '封切机器', '封切时间'],//表栏目
            colModel:[
                        {name:'productName',index:'productName', width:100, align:"left",editable:false},
                        {name:'tpName',index:'tpName', width:100, align:"left",editable:false},
                        {name:'productPrice',index:'productPrice', width:60, align:"left",editable:false, sorttype:"number"},
                        {name:'productCount',index:'productCount', width:60, align:"left",editable:false, sorttype:"number"},
                        {name:'productTotalPrice',index:'productTotalPrice', width:60, align:"left",editable:false, sorttype:"number"},
                        {name:'detailStatue',index:'detailStatue', width:100,formatter:statusFormat},
                        {name:'blownMachineName',index:'blownMachineName', width:100, align:"left",editable:false},
                        {name:'blownTime',index:'blownTime', width:125, formatter:timeFormat},
                        {name:'cutMachineName',index:'cutMachineName', width:100, align:"left",editable:false},
                        {name:'cutTime',index:'cutTime', width:125, formatter:timeFormat2}
            ],
            cellsubmit: "clientArray",
            cellEdit:true,
			beforeEditCell: function (rowid, cellname, v, iRow, iCol) {
			      lastrow = iRow;  //给全局变量赋值
			      lastcell = iCol;
			},
			loadComplete: function () {
				var mygrid = jQuery(grid_selector).jqGrid('clearGridData')[0];
				mygrid.addJSONData(detaildata);
            },
            caption: "采购明细"//表头标题
        });

        //$(pager_selector).css("height","40px");
    }
	
	function getPurchaseOrderDetail(){
    	$.ajax({ 
    		url: "/yhsj/purchase/order/getPurchaseOrderDetail",
    		data:{
    			purchase_id:purchase_id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				if(result.data){
    					initdata=result.data;
    					initLoadData(initdata.main);
    					detaildata = initdata.detail;
    				}
    			}
    		},
    		complete: function(result){
    			showTable();
    		}
    	});
    }
    
    $("#submitbtn").click(function(){
    	
    	if($("#proName").val()==null||$("#proName").val()==""){
    		layer.msg("请填写产品名称", {icon: 2});
    		return;
    	}
    	
    	$.ajax({ 
    		url: "/yhsj/purchase/order/saveProduct",
    		data:$('#purchaseOrderForm').serialize(),
    		dataType:'json',
    		type:"POST" ,
    		processData:false,
    		contentType:false,
    		success: function(result){
    			if(result.code==200){
    				var msg = "";
    				if(type == '1'){
						msg = "新增成功";
    				}else if(type == '2'){
						msg = "修改成功";
    				}
    				layer.msg(msg, {icon: 1,time:1000},function(){
    					location.href="index?url=product&urlName=产品管理";
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#printbtn").click(function(){
    	print(purchase_id);
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=purchaseorder&urlName=采购单管理";
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function print(id){
    	layer.open({
    		type:2,
    		title:'打印采购单',
    		area: ['40%', '80%'],
    		fixed: false, //不固定
    		maxmin: true,
    		content: "breakPage?pageName=purchaseorder_print&purchase_id="+id
    	});
    }
    
    function initLoadData(initdata){
    	$("#purchaseId").val(initdata.purchaseId);
    	$("#purchaseNo").val(initdata.purchaseNo);
		$("#purchaseStatus").val(initdata.purchaseStatus);
		$("#purchaseCreateUserName").val(initdata.purchaseCreateUserName);
		$("#purchaseAddress").val(initdata.purchaseAddress);
		$("#purchaseRemark").val(initdata.purchaseRemark);
		if(initdata.purchaseCreateTime){
			$("#purchaseCreateTime").val(timeFormat3(initdata.purchaseCreateTime));
		}
    }
    
    function timeFormat3(val){
		return (new Date(val)).Format("yyyy-MM-dd HH:mm:ss");
    }
    
})();