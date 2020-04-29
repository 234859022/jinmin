var progrid;
(function() {
var grid_selector = "#grid-table-ingredient";
var pager_selector = "#grid-pager-ingredient";
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
            url:'/yhsj/ingredient/list',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['操作','配料名称', '配料备注','创建时间', '删除'],//表栏目
            colModel:[
                        {name:'ingredient_id',index:'ingredient_id', width:60, align:"center",formatter:function(cellvalue, options, rowObject ){
                        	var htmltext='';
                        	htmltext+='<a href="#" onclick="editIngredient(\''+rowObject.ingredient_id+'\')">编辑</a>&nbsp';
                      		htmltext+='<a href="#" onclick="deleteIngredient(\''+rowObject.ingredient_id+'\')">删除</a>&nbsp';
                      		return htmltext;
                        }},
                        {name:'ingredient_name',index:'ingredient_name', width:60, align:"center"},
                        {name:'ingredient_remark',index:'ingredient_remark', width:60, align:"center"},
                        {name:'ingredient_create_time',index:'ingredient_create_time', width:100, formatter:function(cellvalue, options, rowObject ){
                        	if(rowObject.ingredient_create_time){
                        		return new Date(rowObject.ingredient_create_time).Format("yyyy-MM-dd HH:mm:ss");
                        	}
                        	return "";
                        }},
                        {name:'ingredient_is_delete',index:'ingredient_is_delete', width:125, formatter:function(cellvalue, options, rowObject ){
                        	var texthtml="";
                        	switch(rowObject.ingredient_is_delete){
	                	    	case 0:
	                	    		texthtml='否';
	                	    		break;
	                	    	case 1:
	                	    		texthtml='是';
	                	        	break;
                        	}
                        	return texthtml;
                        }}
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
            caption: "配料列表"//表头标题
        });
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var ingredient_name = $("#ingredient_name").val();
        var condition=[];
        if(ingredient_name){
            condition.push({
    			field_name:'ingredient_name',
    			field_compare:'like',
    			field_value:"'%"+ingredient_name+"%'"
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
        	$("#ingredient_name").val("");
        });
    }

    var ingredientObj = {
        initPage: initPage //初始页面信息
    }


    ingredientPage = ingredientObj;

})();

/**
 * 新增
 */
function  addIngredient() {
	layer.open({
		type:2,
		title:'添加配料信息',
		area: ['800px', '65%'],
		fixed: false, //不固定
		maxmin: true,
		content: 'breakPage?pageName=ingredient_add'
	});
}

/**
 * 修改
 */
function editIngredient(id) {
	layer.open({
		type:2,
		title:'修改配料信息',
		area: ['800px', '65%'],
		fixed: false, //不固定
		maxmin: true,
		content: 'breakPage?pageName=ingredient_add&id='+id
	});
}

/**
 * 删除
 */
function deleteIngredient(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/yhsj/ingredient/delete",
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