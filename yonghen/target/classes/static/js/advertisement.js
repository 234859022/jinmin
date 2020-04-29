var advertisementgrid;
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
    	rStr += '<a name="edit" onclick="modifyAdvertisement(\''+cellvalue+'\')">编辑</a>';
	    rStr +='&nbsp;|&nbsp;<a name="del" onclick="deleteAdvertisement(\''+cellvalue+'\')">删除</a>';
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
    	advertisementgrid=$(grid_selector).jqGrid({
            url:'/yhsj/advertisement/getAdvertisement',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['名称', '状态', '排序','创建时间', '详情','操作'],//表栏目
            colModel:[
                        {name:'advertisementName',index:'advertisementName', width:60, align:"center"},
                        {name:'advertisementStatus',index:'advertisementStatus', width:90,formatter:statusFormat},
                        {name:'advertisementSort',index:'advertisementSort', width:100},
                        {name:'advertisementCreateTime',index:'advertisementCreateTime', width:125, formatter:timeFormat},
                        {name:'advertisementDesc',index:'advertisementDesc', width:100},
                        {name:'advertisementId',index:'advertisementId', width:125,sorttype:"string",formatter:operateFormat}
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
            caption: "广告列表"//表头标题
        });

        //$(pager_selector).css("height","40px");
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var advertisement_name = $("#advertisement_name").val();

        var postData = {
        		advertisement_name:advertisement_name
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

    var advertisementObj = {
        initPage: initPage //初始页面信息
    }


    advertisementPage = advertisementObj;

})();

/**
 * 新增
 */
function  addAdvertisement() {
	location.href = 'index?url=advertisement_edit&urlName=广告管理&type=1';
}

/**
 * 修改
 */
function modifyAdvertisement(id) {
	location.href = 'index?url=advertisement_edit&urlName=广告管理&type=2&advertisement_id='+id;
}

/**
 * 删除
 */
function deleteAdvertisement(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/yhsj/advertisement/deleteAdvertisement",
    		data:{
    			advertisement_id:id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					advertisementgrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
		
	});
}