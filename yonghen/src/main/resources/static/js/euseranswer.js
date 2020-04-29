var user_id='';
var eaaaagrid;
(function() {
	var pager_selector = "#grid-pager-advertisement";
	user_id=getUrlParam("user_id");
	var lastrow;  //最后修改行号先定义一个全局变量
	var lastcell; //最后修改列号
	var grid_selector = "#grid-table-purchaseorderdetail";
	var purchase_id=getUrlParam("purchase_id");
	var initdata={};
	var detaildata;
	getUserDetail();
	function getUserDetail(){
    	$.ajax({ 
    		url: "/e/user/getUserDetail",
    		data:{
    			user_id:user_id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				if(result.data){
    					initLoadData(result.data);
    				}
    			}
    		},
    		complete: function(result){
    			showTable();
    		}
    	});
    }
	
	function statusFormat( cellvalue, options, rowObject ){
    	var htmltext="";
        if(cellvalue == 1){
        	htmltext = "已处理";
        }else{
        	htmltext = "未处理";
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

	    function operateFormat(cellvalue, options, rowObject){
	    	var rStr = '';
	    	rStr += '<a name="edit" onclick="modifyEUA(\''+cellvalue+'\')">编辑</a>';
	    	rStr += '&nbsp;|&nbsp;<a name="edit" onclick="showEUA(\''+cellvalue+'\')">查看</a>';
	    	rStr += '&nbsp;|&nbsp;<a name="edit" onclick="commitEUA(\''+cellvalue+'\')">已处理</a>';
	    	rStr += '&nbsp;|&nbsp;<a name="edit" onclick="commitEUA2(\''+cellvalue+'\')">未处理</a>';
	    	rStr += '&nbsp;|&nbsp;<a name="edit" onclick="deleteEUA(\''+cellvalue+'\')">删除</a>';
	    	return rStr;	
	    }
	    
	  
	 /**
     * 显示表格
     * @param params
     */
    function showTable(params) {
    	eaaaagrid =$(grid_selector).jqGrid({
            url:'/e/user/answer/getUserAnswerList',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['评论标题', '上级评价人', '平级评价人','下级评价人', '上级得分', '平级得分','下级得分','状态','创建时间','操作'],//表栏目
            colModel:[
                        {name:'aTitle',index:'aTitle', width:120, align:"center"},
                        {name:'aYiAdmin',index:'aYiAdmin', width:90},
                        {name:'aErAdmin',index:'aErAdmin', width:90},
                        {name:'aSanAdmin',index:'aSanAdmin', width:90},
                        {name:'aYiScore',index:'aYiScore', width:90},
                        {name:'aErScore',index:'aErScore', width:90},
                        {name:'aSanScore',index:'aSanScore', width:90},
                        {name:'aStatus',index:'aStatus', width:90,formatter:statusFormat},
                        {name:'aCreateTime',index:'aCreateTime', width:125, formatter:timeFormat},
                        {name:'aId',index:'aId', width:180,sorttype:"string",formatter:operateFormat}
                  
            ],
            postData:{aUserid:user_id},
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
            caption: "被评价人列表"//表头标题
        });

    	

        //$(pager_selector).css("height","40px");
    }
    
    $("#submitbtn").click(function(){
    	
    	if($("#proName").val()==null||$("#proName").val()==""){
    		layer.msg("请填写产品名称", {icon: 2});
    		return;
    	}
    	
    	
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
    	$("#A_USERNAME").val(initdata.userName);
    	
    }
    
    function timeFormat3(val){
		return (new Date(val)).Format("yyyy-MM-dd HH:mm:ss");
    }
    
})();

/**
 * 新增
 */
function  addEUA() {
	location.href = 'index?url=euseranswer_edit&urlName=考核管理&type=1&user_id='+user_id;
}

/**
 * 修改
 */
function modifyEUA(id) {
	location.href = 'index?url=euseranswer_edit&urlName=考核管理&type=2&a_id='+id+'&user_id='+user_id;
}

function showEUA(id) {
	location.href = 'index?url=answer_show_all&urlName=查看考核管理&type=2&a_id='+id+'&user_id='+user_id;
}

function commitEUA(id) {
	layer.confirm('确认改成已处理?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/e/user/answer/checkUserAnswer",
    		data:{
    			A_ID:id,
    			status:1
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					eaaaagrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
	});
}

function commitEUA2(id) {
	layer.confirm('确认改成未处理?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/e/user/answer/checkUserAnswer",
    		data:{
    			A_ID:id,
    			status:0
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					eaaaagrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
	});
}

function deleteEUA(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/e/user/answer/deleteUserAnswer",
    		data:{
    			A_ID:id,
    			status:1
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					eaaaagrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
	});
}
