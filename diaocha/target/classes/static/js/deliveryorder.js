var deliveryordergrid;
(function() {
var grid_selector = "#grid-table-deliveryorder";
var pager_selector = "#grid-pager-deliveryorder";
var getProductType=[];
var payType = ["","微信","支付宝","银行卡"];
$("#btime").val(new Date().Format("yyyy-MM-dd"));
$("#etime").val(new Date().Format("yyyy-MM-dd"));
    function updatePagerIcons(table) {
        var replacement =
            {
                'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
                'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
                'ui-icon-seek-next' : 'icon-angle-right bigger-140',
                'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
            };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
        })
    }
    
    function typeFormat( cellvalue, options, rowObject ){
    	var htmltext="";
    	if(cellvalue!=null&&cellvalue!=""){
            var types = cellvalue.split(",");
            for(var i=0;i<types.length;i++){
            	if(i==0){
            		htmltext += payType[types[i]];
            	}else{
            		htmltext += "+"+payType[types[i]];
            	}
            }
    	}
        return htmltext;
    }
    
    function statusFormat( cellvalue, options, rowObject ){
    	var htmltext="";
        if(cellvalue == 1){
        	htmltext = "已发货";
        }else if(cellvalue == 2){
        	htmltext = "已收货";
        }else if(cellvalue == 3){
        	htmltext = "已收款";
        }else if(cellvalue == 4){
        	htmltext = "已完成";
        }
        return htmltext;
    }
    
    function operateFormat(cellvalue, options, rowObject){
    	var rStr = '';
    	rStr += '<a name="edit" onclick="goDeliveryDetail(\''+cellvalue+'\')">详情</a>';
	    rStr +=	'&nbsp;|&nbsp;<a name="del" onclick="updateDeliveryOrder(\''+cellvalue+'\',\''+rowObject.deliveryStatus+'\')">修改状态</a>';
    	rStr += '&nbsp;|&nbsp;<a name="edit" onclick="deliveryprint(\''+cellvalue+'\')">打印</a>';
    	return rStr;	
    }
    
    function timeFormat( cellvalue, options, rowObject ){
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
    function showTable(params) {
    	deliveryordergrid=$(grid_selector).jqGrid({
            url:'/yhsj/delivery/order/getDeliveryOrder',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['下单人','发货人','发货编号','采购编号','发货状态','支付方式','支付宝金额','微信金额','银行卡金额','发货时间','总价','总重(kg)','收货人','操作'],//表栏目
            colModel:[
                      	{name:'delivery_operation_name',index:'delivery_operation_name', width:100, align:"center"},
                      	{name:'delivery_user_name',index:'delivery_user_name', width:100, align:"center"},
                        {name:'deliveryNo',index:'deliveryNo', width:100, align:"center"},
                        {name:'purchaseNo',index:'purchaseNo', width:100, align:"center"},
                        {name:'deliveryStatus',index:'deliveryStatus', width:100,formatter:statusFormat},
                        {name:'deliveryPayType',index:'deliveryPayType', width:100},
                        {name:'deliveryZfbAmount',index:'deliveryZfbAmount', width:100},
                        {name:'deliveryWxAmount',index:'deliveryWxAmount', width:100},
                        {name:'deliveryYhkAmount',index:'deliveryYhkAmount', width:100},
                        {name:'deliveryCreateTime',index:'deliveryCreateTime', width:125, formatter:timeFormat},
                        {name:'deliveryTotalPrice',index:'deliveryTotalPrice', width:100, align:"center"},
                        {name:'deliveryTotalWeight',index:'deliveryTotalWeight', width:100, align:"center"},
                        {name:'deliveryUserName',index:'deliveryUserName', width:100, align:"center"},
                        {name:'deliveryId',index:'deliveryId', width:125,sorttype:"string",formatter:operateFormat}
            ],
            postData:params,
            viewrecords:true,//是否显示所有记录的总数
            rowNum:10,//每页显示的个数
            pager:pager_selector,//默认的分页
            autowidth: true,
            rowList: [10, 20, 30],
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                }, 0);
            },jsonReader : {
                root: "rows",
                page: "page",
                total: "total",
                records: "records",
                repeatitems: false,
                cell: "cell",
                id: "id"
            },
            caption: "发货单列表"//表头标题
        });

        //$(pager_selector).css("height","40px");
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var delivery_no = $("#delivery_no").val();
        var purchase_no = $("#purchase_no").val();
    	var btime=$("#btime").val();
        var etime=$("#etime").val();

        var postData = {
        		delivery_no:delivery_no,
        		purchase_no:purchase_no,
        		btime:btime,
        		etime:etime
        };
        if(parms == ''){
           $(grid_selector).jqGrid('setGridParam',{
               datatype:'json',
                postData:postData, //发送数据
                page:1
            }).trigger("reloadGrid");
        }
        else{
            showTable(postData);
        }
    }

    /**
     * 初始化函数
     * @param param
     */
    function  initPage(param) {
        search('init');
        $("#searchbtn").on("click", function(){
        	 search('');
        });
    }

    var deliveryOrderObj = {
        initPage: initPage //初始页面信息
    }


    deliveryOrderPage = deliveryOrderObj;

})();

/**
 * 详情
 */
function goDeliveryDetail(id) {
	location.href = 'index?url=deliveryorder_detail&urlName=发货单管理&delivery_id='+id;
}

//修改发货单状态
function updateDeliveryOrder(id,status){
	layer.open({
		type:2,
		title:'修改状态',
		area: ['60%', '65%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=deliveryorder_modify&delivery_id="+id+"&status="+status
	});
}

function deliveryprint(id){
	layer.open({
		type:2,
		title:'打印发货单',
		area: ['30%', '80%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=deliveryorder_print&delivery_id="+id
	});
}

/**
 * 删除
 */
function deleteProType(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/yhsj/delivery/order/deleteDeliveryOrder",
    		data:{
    			delivery_id:id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					deliveryordergrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
		
	});
}