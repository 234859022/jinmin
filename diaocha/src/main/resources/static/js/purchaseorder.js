var purchaseordergrid;
(function() {
var grid_selector = "#grid-table-purchaseorder";
var pager_selector = "#grid-pager-purchaseorder";
$("#btime").val(new Date().Format("yyyy-MM-dd"));
$("#etime").val(new Date().Format("yyyy-MM-dd"));
var getProductType=[];
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
    
    function statusFormat( cellvalue, options, rowObject ){
    	var htmltext="";
        if(cellvalue == 1){
        	htmltext = "未处理";
        }else if(cellvalue == 2){
        	htmltext = "已处理";
        }else if(cellvalue == 3){
        	htmltext = "已完成";
        }else if(cellvalue == 4){
        	htmltext = "已生成发货单";
        }
        return htmltext;
    }
    
    function operateFormat(cellvalue, options, rowObject){
    	var rStr = '';
    	rStr += '<a name="edit" onclick="goDetail(\''+cellvalue+'\')">详情</a>';
	    rStr +=	'&nbsp;|&nbsp;<a name="del" onclick="updatePurchaseOrder(\''+cellvalue+'\',\''+rowObject.purchaseStatus+'\')">修改状态</a>';
    	rStr += '&nbsp;|&nbsp;<a name="edit" onclick="purchaseprint(\''+cellvalue+'\')">打印</a>';
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
    	purchaseordergrid=$(grid_selector).jqGrid({
            url:'/yhsj/purchase/order/getPurchaseOrder',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['下单人','采购编号','发货状态','下单时间','备注','操作'],//表栏目
            colModel:[
                    	{name:'purchaseCreateUserName',index:'purchaseCreateUserName', width:100, align:"center"},
                        {name:'purchaseNo',index:'purchaseNo', width:100, align:"center"},
                        {name:'purchaseStatus',index:'purchaseStatus', width:100,formatter:statusFormat},
                        {name:'purchaseCreateTime',index:'purchaseCreateTime', width:125, formatter:timeFormat},
                        {name:'purchaseRemark',index:'purchaseRemark', width:100, align:"center"},
                        {name:'purchaseId',index:'purchaseId', width:125,sorttype:"string",formatter:operateFormat}
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
            caption: "采购单列表"//表头标题
        });

        //$(pager_selector).css("height","40px");
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var purchase_no = $("#purchase_no").val();
        var purchase_status = $("#purchase_status").val();
    	var btime=$("#btime").val();
        var etime=$("#etime").val();

        var postData = {
        		purchaseNo:purchase_no,
        		purchaseStatus:purchase_status,
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

    var purchaseOrderObj = {
        initPage: initPage //初始页面信息
    }


    purchaseOrderPage = purchaseOrderObj;

})();

/**
 * 详情
 */
function goDetail(id) {
	location.href = 'index?url=purchaseorder_detail&urlName=采购单管理&purchase_id='+id;
}

//修改采购单状态
function updatePurchaseOrder(id,status){
	layer.open({
		type:2,
		title:'修改状态',
		area: ['60%', '65%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=purchaseorder_modify&purchase_id="+id+"&status="+status
	});
}

function purchaseprint(id){
	layer.open({
		type:2,
		title:'打印采购单',
		area: ['40%', '80%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=purchaseorder_print&purchase_id="+id
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
    		url: "/yhsj/purchase/order/deletePurchaseOrder",
    		data:{
    			purchase_id:id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					purchaseordergrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
		
	});
}