var progrid;
var id="";
(function() {
	id=getUrlParam("id");
var grid_selector = "#grid-table-machine-task-ingrediient";
var pager_selector = "#grid-pager-machine-task-ingrediient";
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
            url:'/yhsj/machine/task/ingrediient/list',
            datatype: 'json',
            mtype: 'POST',
            height: 270,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['材料名称', '数量', '创建时间', '创建人'],//表栏目
            colModel:[
                        {name:'ingredient_name',index:'ingredient_name', align:"center"},
                        {name:'ingredient_num',index:'ingredient_num', align:"center"},
                        {name:'ingredient_create_time',index:'ingredient_create_time',formatter:function(cellvalue, options, rowObject ){
                        	if(rowObject.ingredient_create_time){
                        		return new Date(rowObject.ingredient_create_time).Format("yyyy-MM-dd HH:mm:ss");
                        	}
                        	return "";
                        }},
                        {name:'ingredient_create_user',index:'ingredient_create_user'}
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
            caption: "机器任务材料列表"//表头标题
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
        if(id){
        	condition.push({
        		field_name:'task_id',
        		field_compare:'=',
        		field_value:"'"+id+"'"
        	});
        }
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
    
    search('init');

})();
