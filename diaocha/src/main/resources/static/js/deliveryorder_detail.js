(function() {
	var lastrow;  //最后修改行号先定义一个全局变量
	var lastcell; //最后修改列号
	var grid_selector = "#grid-table-deliveryorderdetail";
	var delivery_id=getUrlParam("delivery_id");
	var initdata={};
	var detaildata;
	getDeliveryOrderDetail();
	
	function operateFormat(val, options, rowObject){
    	var rStr = '';
	    rStr +='<a name="del" onclick="delRow(\''+options.rowId+'\')">删除</a>';
    	return rStr;	
    }
	
	 /**
     * 显示表格
     * @param params
     */
    function showTable() {
    	$(grid_selector).jqGrid({
            height: 300,
            width:1000,
            altRows:true,//隔行变色
            multiselect:true,
            autowidth: false,
            datatype:"local",
            altclass:'',//用于交替变色的class属性
            colNames:['产品名称', '规格', '价格' , '数量', '总价'],//表栏目
            colModel:[
                        {name:'productName',index:'productName', width:100, align:"left",editable:false},
                        {name:'tpName',index:'tpName', width:100, align:"left",editable:false},
                        {name:'productPrice',index:'productPrice', width:60, align:"left",editable:false, sorttype:"number"},
                        {name:'productCount',index:'productCount', width:60, align:"left",editable:false, sorttype:"number"},
                        {name:'productTotalPrice',index:'productTotalPrice', width:60, align:"left",editable:false, sorttype:"number"}
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
            caption: "发货明细"//表头标题
        });

        //$(pager_selector).css("height","40px");
    }
	
	function getDeliveryOrderDetail(){
    	$.ajax({ 
    		url: "/yhsj/delivery/order/getDeliveryOrderDetail",
    		data:{
    			delivery_id:delivery_id
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
    		url: "/yhsj/delivery/order/saveProduct",
    		data:$('#deliveryOrderForm').serialize(),
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
    	print(delivery_id);
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=deliveryorder&urlName=发货单管理";
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata.main);
    });   
    
    function print(id){
    	layer.open({
    		type:2,
    		title:'打印发货单',
    		area: ['30%', '80%'],
    		fixed: false, //不固定
    		maxmin: true,
    		content: "breakPage?pageName=deliveryorder_print&delivery_id="+id
    	});
    }
    
    function initLoadData(initdata){
    	$("#deliveryId").val(initdata.deliveryId);
    	$("#deliveryNo").val(initdata.deliveryNo);
    	$("#purchaseNo").val(initdata.purchaseNo);
		$("#deliveryStatus").val(initdata.deliveryStatus);
		$("#deliveryTotalPrice").val(initdata.deliveryTotalPrice);
		$("#deliveryTotalWeight").val(initdata.deliveryTotalWeight);
		$("#deliveryUserName").val(initdata.deliveryUserName);
		$("#deliveryAddress").val(initdata.deliveryAddress);
		$("#deliveryPayType").val(initdata.deliveryPayType);
		$("#deliveryZfbAmount").val(initdata.deliveryZfbAmount);
		$("#deliveryWxAmount").val(initdata.deliveryWxAmount);
		$("#deliveryYhkAmount").val(initdata.deliveryYhkAmount);
		if(initdata.deliveryCreateTime){
			$("#deliveryCreateTime").val(timeFormat(initdata.deliveryCreateTime));
		}
    }
    
    function timeFormat(val){
		return (new Date(val)).Format("yyyy-MM-dd HH:mm:ss");
    }
    
})();