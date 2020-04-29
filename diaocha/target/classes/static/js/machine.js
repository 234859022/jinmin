var progrid;
(function() {
var grid_selector = "#grid-table-machine";
var pager_selector = "#grid-pager-machine";
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
            url:'/yhsj/machine/list',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['操作','机器名称', '机器类型', '创建人', '创建时间', '删除','是否故障','故障原因'],//表栏目
            colModel:[
                        {name:'machine_id',index:'machine_id', width:60, align:"center",formatter:function(cellvalue, options, rowObject ){
                        	var htmltext='';
                        	htmltext+='<a href="#" onclick="editMachine(\''+rowObject.machine_id+'\')">编辑</a>&nbsp';
                      		htmltext+='<a href="#" onclick="deleteMachine(\''+rowObject.machine_id+'\')">删除</a>&nbsp';
                      		return htmltext;
                        }},
                        {name:'machine_name',index:'machine_name', width:60, align:"center"},
                        {name:'machine_type',index:'machine_type', width:60, align:"center", formatter:function(cellvalue, options, rowObject ){
                        	var texthtml="";
                        	switch(rowObject.machine_type){
	                	    	case 1:
	                	    		texthtml='吹膜机器';
	                	    		break;
	                	    	case 2:
	                	    		texthtml='封切机器';
	                	        	break;
                        	}
                        	return texthtml;
                        }},
                        /*{name:'machine_status',index:'machine_status', width:60, align:"center", formatter:function(cellvalue, options, rowObject ){
                        	var texthtml="";
                        	switch(rowObject.machine_status){
	                	    	case 0:
	                	    		texthtml='开始';
	                	    		break;
	                	    	case 1:
	                	    		texthtml='结束';
	                	        	break;
                        	}
                        	return texthtml;
                        }},*/
                        {name:'username',index:'username', width:60, align:"center"},
                        {name:'machine_create_time',index:'machine_create_time', width:100,formatter:function(cellvalue, options, rowObject ){
                        	if(rowObject.machine_create_time){
                        		return new Date(rowObject.machine_create_time).Format("yyyy-MM-dd HH:mm:ss");
                        	}
                        	return "";
                        }},
                        {name:'machine_is_delete',index:'machine_is_delete', width:125, formatter:function(cellvalue, options, rowObject ){
                        	var texthtml="";
                        	switch(rowObject.machine_is_delete){
	                	    	case 0:
	                	    		texthtml='否';
	                	    		break;
	                	    	case 1:
	                	    		texthtml='是';
	                	        	break;
                        	}
                        	return texthtml;
                        }},
                        {name:'machine_is_fault',index:'machine_is_fault', width:125, formatter:function(cellvalue, options, rowObject ){
                        	var texthtml="";
                        	switch(cellvalue){
	                	    	case 0:
	                	    		texthtml='否';
	                	    		break;
	                	    	case 1:
	                	    		texthtml='是';
	                	        	break;
                        	}
                        	return texthtml;
                        }},
                        {name:'machine_fault_reason',index:'machine_fault_reason', width:60, align:"left"}
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
            caption: "机器列表"//表头标题
        });
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var machine_name = $("#machine_name").val();
        var machine_type = $("#machine_type").val();
        var condition=[];
        if(machine_name){
            condition.push({
    			field_name:'machine_name',
    			field_compare:'like',
    			field_value:"'%"+machine_name+"%'"
    		});
        }
        if(machine_type){
            condition.push({
    			field_name:'machine_type',
    			field_compare:'=',
    			field_value:machine_type
    		});
        }
        

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
        	 search('');
        });
        $("#resetbtn").on("click",function(){
        	$("#machine_name").val("");
        	$("#machine_type").val("");
        });
    }

    var machineObj = {
        initPage: initPage //初始页面信息
    }


    machinePage = machineObj;

})();

/**
 * 新增
 */
function  addMachine() {
	layer.open({
		type:2,
		title:'添加机器信息',
		area: ['800px', '50%'],
		fixed: false, //不固定
		maxmin: true,
		content: 'breakPage?pageName=machine_add'
	});
}

/**
 * 修改
 */
function editMachine(id) {
	layer.open({
		type:2,
		title:'修改机器信息',
		area: ['800px', '50%'],
		fixed: false, //不固定
		maxmin: true,
		content: 'breakPage?pageName=machine_add&id='+id
	});
}

/**
 * 删除
 */
function deleteMachine(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/yhsj/machine/delete",
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