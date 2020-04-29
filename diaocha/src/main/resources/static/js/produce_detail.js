var grid;
(function() {
var grid_selector = "#grid-table-produceDetail";
var pager_selector = "#grid-pager-produceDetail";
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
    	rStr += '<a name="edit" onclick="goProduceDetail(\''+cellvalue+'\')">详情</a>';
	    rStr +='&nbsp;|&nbsp;<a name="del" onclick="updateProduceDetail(\''+cellvalue+'\')">修改明细</a>';
	    rStr +='&nbsp;|&nbsp;<a name="del" onclick="deleteProduceDetail(\''+cellvalue+'\')">删除明细</a>';
	    return rStr;	
    }
    
    function timeFormat( cellvalue, options, rowObject ){
		return (new Date(cellvalue)).Format("yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 显示表格
     * @param params
     */
    function showTable(params) {
    	grid=$(grid_selector).jqGrid({
            url:'/yhsj/produce/detail/list',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['客户','规格','颜色','机器','操作'],//表栏目
            colModel:[
                        {name:'produce_detail_user_name',index:'produce_detail_user_name', width:100, align:"center"},
                        {name:'produce_detail_spec_name',index:'produce_detail_spec_name', width:100, align:"center"},
                        {name:'produce_detail_color_type',index:'produce_detail_color_type', width:100},
                        {name:'produce_detail_machine_name',index:'produce_detail_machine_name', width:100, align:"center"},
                        {name:'produce_detail_id',index:'produce_detail_id', width:125,sorttype:"string",formatter:operateFormat}
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
            caption: "生产明细列表"//表头标题
        });
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var produce_detail_user_name = $("#produce_detail_user_name").val();
        var produce_detail_spec_name = $("#produce_detail_spec_name").val();
    	var produce_detail_color_type=$("#produce_detail_color_type").val();
    	var params=[];
    	if(produce_detail_user_name){
    		params.push({
    			field_name:'produce_detail_user_name',
    			field_compare:'like',
    			field_value:"'%"+produce_detail_user_name+"%'"
    		})
    	}
    	if(produce_detail_spec_name){
    		params.push({
    			field_name:'produce_detail_spec_name',
    			field_compare:'like',
    			field_value:"'%"+produce_detail_spec_name+"%'"
    		})
    	}
    	if(produce_detail_color_type){
    		params.push({
    			field_name:'produce_detail_color_type',
    			field_compare:'like',
    			field_value:"'%"+produce_detail_color_type+"%'"
    		})
    	}
        var postData = {
        		condition_str:JSON.stringify(params)
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

    var produceDetailObj = {
        initPage: initPage //初始页面信息
    }


    produceDetailPage = produceDetailObj;

})();

/**
 * 详情
 */
function goProduceDetail(id) {
	layer.open({
		type:2,
		title:'修改生产明细',
		area: ['60%', '65%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=produce_detail_modify&opertype=read&id="+id
	});
}

/**
 * 新增生产明细
 * */
function insertProduceDetail(){
	layer.open({
		type:2,
		title:'新增生产明细',
		area: ['60%', '65%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=produce_detail_modify"
	});
}

/**
 * 修改生产明细
 * */
function updateProduceDetail(id){
	layer.open({
		type:2,
		title:'修改生产明细',
		area: ['60%', '65%'],
		fixed: false, //不固定
		maxmin: true,
		content: "breakPage?pageName=produce_detail_modify&id="+id
	});
}

/**
 * 删除生产明细
 */
function deleteProduceDetail(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/yhsj/produce/detail/deleteDeliveryOrder",
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