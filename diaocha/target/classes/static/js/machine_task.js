var progrid;
(function() {
var grid_selector = "#grid-table-machine-task";
var pager_selector = "#grid-pager-machine-task";
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
    /**
     * 显示表格
     * @param params
     */
    function showTable(params) {
    	progrid=$(grid_selector).jqGrid({
            url:'/yhsj/machine/task/list',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['操作','机器名称', '采购编号', '产品名称', '数量', '重量', '规格名称', '开始时间', '结束时间', '状态', '创建时间', '创建人'],//表栏目
            colModel:[
                        {name:'task_id',index:'task_id', width:150, align:"center",formatter:function(cellvalue, options, rowObject ){
                        	var htmltext='';
                      		htmltext+='<a href="#" onclick="deleteMachineTask(\''+rowObject.task_id+'\')">删除</a>&nbsp';
                      		htmltext+='<a href="#" onclick="watchMachineTaskIngrediient(\''+rowObject.task_id+'\')">查看材料</a>&nbsp';
                      		//htmltext+='<a href="#" onclick="machineTaskPrint(\''+rowObject.task_id+'\')">打印预览</a>&nbsp';
                      		htmltext+='<a href="#" onclick="produceDetailPrint(\''+rowObject.purchase_detail_id+'\',\''+rowObject.task_id+'\')">打印生产明细</a>&nbsp';
                      		return htmltext;
                        }},
                        {name:'machine_name',index:'machine_name', width:125, align:"center"},
                        {name:'purchase_no',index:'purchase_no', width:125, align:"center"},
                        {name:'product_name',index:'product_name', width:125, align:"center"},
                        {name:'product_count',index:'product_count', width:125, align:"center"},
                        {name:'product_weight',index:'product_weight', width:100},
                        {name:'tp_name',index:'tp_name', width:125},
                        {name:'task_start_time',index:'task_start_time', width:125,formatter:function(cellvalue, options, rowObject ){
                        	if(rowObject.task_start_time){
                        		return new Date(rowObject.task_start_time).Format("yyyy-MM-dd HH:mm:ss");
                        	}
                        	return "";
                        }},
                        {name:'task_end_time',index:'task_end_time', width:125,formatter:function(cellvalue, options, rowObject ){
                        	if(rowObject.task_end_time){
                        		return new Date(rowObject.task_end_time).Format("yyyy-MM-dd HH:mm:ss");
                        	}
                        	return "";
                        }},
                        {name:'task_status',index:'task_status', width:125, formatter:function(cellvalue, options, rowObject ){
                        	var texthtml="";
                        	switch(rowObject.task_status){
	                	    	case 1:
	                	    		texthtml='未处理';
	                	    		break;
	                	    	case 2:
	                	    		texthtml='进行中';
	                	    		break;
	                	    	case 3:
	                	    		texthtml='已完成';
	                	        	break;
                        	}
                        	return texthtml;
                        }},
                        /*{name:'task_is_delete',index:'task_is_delete', width:125, formatter:function(cellvalue, options, rowObject ){
                        	var texthtml="";
                        	switch(rowObject.task_is_delete){
	                	    	case 0:
	                	    		texthtml='否';
	                	    		break;
	                	    	case 1:
	                	    		texthtml='是';
	                	        	break;
                        	}
                        	return texthtml;
                        }},*/
                        {name:'task_create_time',index:'task_create_time', width:125, formatter:function(cellvalue, options, rowObject ){
                        	if(rowObject.task_create_time){
                        		return new Date(rowObject.task_create_time).Format("yyyy-MM-dd HH:mm:ss");
                        	}
                        	return "";
                        }},
                        {name:'task_create_user',index:'task_create_user', width:125}
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
            caption: "机器任务列表"//表头标题
        });
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var condition=[];
        var postData = {
        	
        };
        if(condition.length>0){
        	postData.condition_str=JSON.stringify(condition);
        }else{
        	postData.condition_str="";
        }
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
        	queryMachineTask();
        });
    }

    var machineTaskObj = {
        initPage: initPage //初始页面信息
    }


    machineTaskPage = machineTaskObj;

})();

function queryMachineTask(){
	layer.open({
		type:2,
		title:'查询机器任务信息',
		area: ['60%', '65%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=machine_task_query"
	});
}


function watchMachineTaskIngrediient(id){
	if(id){
		layer.open({
			type:2,
			title:'查询机器任务材料信息',
			area: ['60%', '65%'],
			fixed: false, //不固定
			maxmin: true,
			content: "breakPage?pageName=machine_task_ingrediient&id="+id
		});
	}
}

/**
 * 删除
 */
function deleteMachineTask(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/yhsj/machine/task/delete",
    		data:{
    			id:id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					progrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
		
	});
}

function machineTaskPrint(id){
	layer.open({
		type:2,
		title:'打印生产明细',
		area: ['40%', '80%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=machine_task_print&task_id="+id
	});
}

function produceDetailPrint(id,task_id){
	layer.open({
		type:2,
		title:'打印生产明细',
		area: ['50%', '80%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=produce_detail_print&purchase_detail_id="+id+"&task_id="+task_id
	});
}