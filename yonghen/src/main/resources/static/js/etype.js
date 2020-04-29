var etypegrid;
(function() {
var grid_selector = "#grid-table-advertisement";
var pager_selector = "#grid-pager-advertisement";
var getAdvertisement=[];
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
        if(cellvalue == 0){
        	htmltext = "不可用";
        }else if(cellvalue = 1){
        	htmltext = "可用";
        }
        return htmltext;
    }
    
    function operateFormat(cellvalue, options, rowObject){
    	var rStr = '';
    	rStr += '<a name="edit" onclick="modifyType(\''+cellvalue+'\')">编辑</a>';
	    rStr +='&nbsp;|&nbsp;<a name="del" onclick="deleteType(\''+cellvalue+'\')">删除</a>';
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
    	etypegrid=$(grid_selector).jqGrid({
            url:'/e/type/getTypeList',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['分类名称', '分类详情', '排序','操作'],//表栏目
            colModel:[
                        {name:'typeName',index:'typeName', width:60, align:"center"},
                        {name:'typeDetail',index:'typeDetail', width:90},
                        {name:'typeSort',index:'typeSort', width:90},
                        {name:'typeId',index:'typeId', width:125,sorttype:"string",formatter:operateFormat}
                  
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
            caption: "分类列表"//表头标题
        });

        //$(pager_selector).css("height","40px");
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var type_name = $("#type_name").val();

        var postData = {
        		name:type_name
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

    var etypeObj = {
        initPage: initPage //初始页面信息
    }


    etypePage = etypeObj;

})();

/**
 * 新增
 */
function  addType() {
	location.href = 'index?url=etype_edit&urlName=分类管理&type=1';
}

/**
 * 修改
 */
function modifyType(id) {
	location.href = 'index?url=etype_edit&urlName=分类管理&type=2&type_id='+id;
}

/**
 * 删除
 */
function deleteType(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/e/type/deleteType",
    		data:{
    			type_id:id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					etypegrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
		
	});
}