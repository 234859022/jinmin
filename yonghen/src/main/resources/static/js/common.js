
/*
 * type              请求的方式  默认为get
 * url               发送请求的地址
 * param             发送请求的参数
 * isShowLoader      是否显示loader动画  默认为false
 * dataType          返回JSON数据  默认为JSON格式数据
 * callBack          请求的回调函数
 */
(function(){
    function AjaxRequest(opts){
        this.type         = opts.type || "get";
        this.url          = opts.url;
        this.param        = opts.param || {};
        this.isShowLoader = opts.isShowLoader || false;
        this.dataType     = opts.dataType || "json";
        this.callBack     = opts.callBack;
        this.init();
    }

    AjaxRequest.prototype = {
        //初始化
        init: function(){
            this.sendRequest();
        },
        //渲染loader
        showLoader: function(){
            if(this.isShowLoader){
                var loader = '<div class="ajaxLoader"><div class="loader">加载中...</div></div>';
                $("body").append(loader);
            }
        },
        //隐藏loader
        hideLoader: function(){
            if(this.isShowLoader){
                $(".ajaxLoader").remove();
            }
        },
        //发送请求
        sendRequest: function(){
            var self = this;
            $.ajax({
                type: this.type,
                url: this.url,
                data: this.param,
                dataType: this.dataType,
                beforeSend: this.showLoader(),
                success: function(res){
                    self.hideLoader();
                    if (res != null && res != "") {
                        if(self.callBack){
                            if (Object.prototype.toString.call(self.callBack) === "[object Function]") {   //Object.prototype.toString.call方法--精确判断对象的类型
                                self.callBack(res);
                            }else{
                                console.log("callBack is not a function");
                            }
                        }
                    }
                }
            });
        }
    };

    window.AjaxRequest = AjaxRequest;
})();

$.extend($.validator.messages, {
    required: "必选字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串"),
    minlength: $.validator.format("请输入一个长度最少是 {0} 的字符串"),
    rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
    range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: $.validator.format("请输入一个最大为 {0} 的值"),
    min: $.validator.format("请输入一个最小为 {0} 的值")
});

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function  showTip(msg) {
    bootbox.dialog({
        message: msg,
        buttons: {
            "success" : {
                "label" : "OK",
                "className" : "btn-sm btn-primary"
            }
        }
    });
}

function  showConfirm(msg,callbackFun,parm1,parm2) {
    bootbox.confirm({
        buttons: {
            confirm: {
                label: '确认',
                className: 'btn-sm btn-primary'
            },
            cancel: {
                label: '取消',
                className: 'btn-sm'
            }
        },
        message: msg,
        callback: function (result) {
            if (result) {

                if(parm1!=undefined && parm2!=undefined){
                    callbackFun(parm1,parm2);
                }else{
                    callbackFun();
                }
            } else {

            }
        },
        //title: "bootbox confirm也可以添加标题哦",
    });
}
/*格式化开始时间*/
function dateStartformat(myDate) {
    return myDate.getFullYear() + "-" + check(myDate.getMonth()+1) + "-" + check(myDate.getDate());
}

//检查是不是两位数字，不足补全
function check(str){
    str=str.toString();
    if(str.length<2){
        str='0'+ str;
    }
    return str;
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

/**
 * 显示对话框
 * @param title
 * @param msg
 * @param callbackOk
 * @param callbackCanc
 */
function showDialog(title,msg,callbackOk,callbackCanc,classNm,txt) {
    bootbox.dialog({
        title : title,
        message : msg,
        className: classNm,
        buttons : {
            "success" : {
                "label" : "<i class='icon-ok'></i> " +(txt==undefined?"保存":txt),
                "className" : "btn-sm btn-success",
                "callback" : callbackOk
            },
            "cancel" : {
                "label" : "<i class='icon-info'></i> 取消",
                "className" : "btn-sm btn-danger",
                "callback" : callbackCanc
            }
        }
    });
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = decodeURI(window.location.search).substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}


/**
 * 生成查询的容器id 
 * @param 要生成的条件集合 
 * 示例[{field_text:'',field_name:'',field_type:'',field_html_widget:'',field_html_widget_list:[],field_value_list:[]}]  
 * field_text 在页面显示的名称 field_name 表字段名称 field_type 字段类型 默认为text (值类型为text,number,date) 
 * field_html_widget_list 下拉框需要用到的集合 如何逻辑选择框select的话,需要传 默认不传  格式为[compare_value:'',compare_text:'']
 *  field_value_list 条件值 如何为select的话需要传 默认不传 格式为[compare_value:'',compare_text:'']
 *
 * */
function createConditionQuery(contrai_id,conditions){
	var htmlText="";
	//$("#"+contrai_id).html("");
	if(conditions.length>0){
		$.each(conditions,function(index,item){
			htmlText+='<div class="form-group" style="width:100%;margin-bottom: 5px;">';
			htmlText+='<label class="col-sm-3 control-label"';
			htmlText+='for="'+item.field_name+'_value" style="width:125px;" id="'+item.field_name+'_field" data-value="'+item.field_name+'">'+item.field_text+'</label>';
			if(item.field_html_widget_list!=null&&item.field_html_widget_list.length>0){
				htmlText+='<select class="col-sm-3 control-label no-padding-right"  data-field-name="'+item.field_name+'" data-value="'+item.field_html_widget_list[0].compare_value+'" onchange="selectVoluaOperation(this)" id="'+item.field_name+'_compare" style="width:70px;">';
				$.each(item.field_html_widget_list,function(cindex,citem){
					htmlText+='<option value="'+citem.compare_value+'">'+citem.compare_text+'</option>';
				});
				htmlText+='</select>';
			}else{
				htmlText+='<label class="col-sm-3 control-label no-padding-right"';
				htmlText+='for="id_value" style="width:70px;" id="'+item.field_name+'_compare" data-value="=">等于</label>';
			}
			if(item.field_value_list!=null&&item.field_value_list.length>0){
				htmlText+='<div class="col-sm-9" id="'+item.field_name+'_box">';
				htmlText+='<select class="col-xs-10 col-sm-5" id="'+item.field_name+'_value" style="width:200px;">';
				$.each(item.field_value_list,function(cindex,citem){
					htmlText+='<option value="'+citem.compare_value+'">'+citem.compare_text+'</option>';
				});
				htmlText+='</select>';
				htmlText+='</div>';
			}else{
				var tmp_field_type="text";
				if(item.field_type!=null&&item.field_type!=''){
					tmp_field_type=item.field_type;
				}
				if(item.field_html_widget_list!=null&&item.field_html_widget_list.length>0){
					if(item.field_html_widget_list[0].compare_value=='between'||item.field_html_widget_list[0].compare_value=='BETWEEN'){
						htmlText+='<div class="col-sm-9" id="'+item.field_name+'_between_box">';
						htmlText+='<input type="'+tmp_field_type+'" class="col-xs-10 col-sm-5" placeholder="" id="'+item.field_name+'_start_value" style="width:200px;"/>';
						htmlText+='<label class="col-xs-10 col-sm-5" style="width:40px;">至</label>';
						htmlText+='<input type="'+tmp_field_type+'" class="col-xs-10 col-sm-5" placeholder="" id="'+item.field_name+'_end_value" style="width:200px;"/>';
						htmlText+='</div>';
						htmlText+='<div class="col-sm-9" id="'+item.field_name+'_box"  style="display:none;">';
						htmlText+='<input type="'+tmp_field_type+'" class="col-xs-10 col-sm-5" placeholder="" id="'+item.field_name+'_value" style="width:200px;"/>';
						htmlText+='</div>';
					}else{
						htmlText+='<div class="col-sm-9" id="'+item.field_name+'_between_box" style="display:none;">';
						htmlText+='<input type="'+tmp_field_type+'" class="col-xs-10 col-sm-5" placeholder="" id="'+item.field_name+'_start_value" style="width:200px;"/>';
						htmlText+='<label class="col-xs-10 col-sm-5" style="width:40px;">至</label>';
						htmlText+='<input type="'+tmp_field_type+'" class="col-xs-10 col-sm-5" placeholder="" id="'+item.field_name+'_end_value" style="width:200px;"/>';
						htmlText+='</div>';
						htmlText+='<div class="col-sm-9" id="'+item.field_name+'_box">';
						htmlText+='<input type="'+tmp_field_type+'" class="col-xs-10 col-sm-5" placeholder="" id="'+item.field_name+'_value" style="width:200px;"/>';
						htmlText+='</div>';
					}
				}else{
					htmlText+='<div class="col-sm-9" id="'+item.field_name+'_box">';
					htmlText+='<input type="'+tmp_field_type+'" class="col-xs-10 col-sm-5" placeholder="" id="'+item.field_name+'_value" style="width:200px;"/>';
					htmlText+='</div>';
				}
			}
			htmlText+='</div>';
		});
	}
	
	$("#"+contrai_id).children().first().before(htmlText);
	
}

function selectVoluaOperation(obj){
	$(obj).attr("data-value",$(obj).val());
	
	if($(obj).val()=='between'||$(obj).val()=='BETWEEN'){
		$("#"+$(obj).attr("data-field-name")+"_between_box").show();
		$("#"+$(obj).attr("data-field-name")+"_box").hide();
	}else{
		$("#"+$(obj).attr("data-field-name")+"_between_box").hide();
		$("#"+$(obj).attr("data-field-name")+"_box").show();
	}
}


function getCondition(conditions){
	var condition=[];
	$.each(conditions,function(index,item){
		if($("#"+item.field_name+"_value").val()||$("#"+item.field_name+"_start_value").val()||$("#"+item.field_name+"_end_value").val()){
			if($("#"+item.field_name+"_compare").val()=='between'||$("#"+item.field_name+"_compare").val()=='BETWEEN' ){
				if(item.field_type=='date'){
					var st=$("#"+item.field_name+"_start_value").val()+' 00:00:00';
					var et=$("#"+item.field_name+"_end_value").val()+' 23:59:59';
					condition.push({
						field_name:item.field_name,
						field_compare:$("#"+item.field_name+"_compare").attr("data-value"),
						field_value:"'"+st+"'"+' and '+"'"+et+"'"
					});
				}else{
					condition.push({
						field_name:item.field_name,
						field_compare:$("#"+item.field_name+"_compare").attr("data-value"),
						field_value:$("#"+item.field_name+"_start_value").val()+' and '+$("#"+item.field_name+"_end_value").val()
					});
				}
			}else{
				switch(item.field_type){
					case 'date':
						condition.push({
							field_name: "TO_DAYS("+item.field_name+")",
							field_compare:$("#"+item.field_name+"_compare").attr("data-value"),
							field_value:"TO_DAYS('"+$("#"+item.field_name+"_value").val()+"')"
						});
						break;
					case 'number':
						condition.push({
							field_name:item.field_name,
							field_compare:$("#"+item.field_name+"_compare").attr("data-value"),
							field_value:$("#"+item.field_name+"_value").val()
						});
						break;
					case 'text':
						if($("#"+item.field_name+"_compare").val()=='like'||$("#"+item.field_name+"_compare").val()=='LIKE'){							
							condition.push({
								field_name:item.field_name,
								field_compare:$("#"+item.field_name+"_compare").attr("data-value"),
								field_value:"'%"+$("#"+item.field_name+"_value").val()+"%'"
							});
						}else{
							condition.push({
								field_name:item.field_name,
								field_compare:$("#"+item.field_name+"_compare").attr("data-value"),
								field_value:"'"+$("#"+item.field_name+"_value").val()+"'"
							});
						}
						break;
				}
			}
		}
	});
	return condition;
}


