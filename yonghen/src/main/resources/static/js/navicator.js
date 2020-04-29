
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

$(document).ready(function(){

    indexPage.initPage();

});

(function() {
    

    function  setChange() {
        var message  ="<div class='well' style='margin-top:25px;'><form class='form-horizontal' role='form'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>旧密码</label><div class='col-sm-9'><input type='password' id='txtOldPwd' placeholder='请输入旧密码' class='col-xs-10 col-sm-5' /></div></div><div class='space-4'></div><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtNewPwd1'>新密码</label><div class='col-sm-9'><input type='password' id='txtNewPwd1' placeholder='请输入新密码' class='col-xs-10 col-sm-5' /></div></div><div class='space-4'></div><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtNewPwd2'>确认新密码</label><div class='col-sm-9'><input type='password' id='txtNewPwd2' placeholder='再次输入新密码' class='col-xs-10 col-sm-5' /></div></div></form></div>";

        var okFun =  function() {
                        var txt1 = $("#txtOldPwd").val();
                        var txt2 = $("#txtNewPwd1").val();
                        var txt3 = $("#txtNewPwd2").val();

                        if(txt1 == "" || txt2 == "" || txt3 == ""){
                            bootbox.alert("密码不能为空");
                            return false;
                        }
                        if(txt2 != txt3 ){
                            bootbox.alert("两次输入新密码不一致，请重新输入!");
                            return false;
                        }
                        var info = {mobile:$("#spanUserId").html(),oldPwd:txt1,newPwd:txt2};
                        new AjaxRequest({
                            url: "/sys/admin/changepwd",
                            param: info,
                            isShowLoader: true,
                            type:"post",
                            dataType: "",
                            callBack: function(res){
                                console.log(res);
                                if(res.code = "200" && res.data != null)
                                {
                                    if(res.message!=null && res.message == "失败，密码错误!"){
                                        showTip(res.message);
                                    }else {
                                        showTip("保存成功");
                                    }
                                }else{
                                	showTip(res.message);
                                }
                            }
                        });
                    };
        showDialog("修改密码",message,okFun,null);
    }
    
    /**
     * 取用户信息
     */
    function  getUserInfo() {
        $.ajax({
            type : "get",
            url : '/sys/admin/sessions',
            async : false,
            success : function(result)
            {
                if(result.message !="成功!" || result.data == null){
                    $(location).prop('href', "/index/geturl?url=login");
                }
                if(result.code = "200")
                {
                    console.log(result);
                    $("#spanUser").html(result.data.nickname);
                    $("#spanUserId").html(result.data.mobile);
                    if(result.data.nickname!='admin'){
                    	$("#y1").hide();
                    	$("#y2").hide();
                    	$("#y3").hide();
                    	$("#y4").hide();
//                    	$("#y5").hide();
                    }
//                    if(null !=result.data.headPic){
//                        $("#iuserImg").attr("src",result.data.headPic);
//                    }
                }
            },
            error: function () { },
            complete: function () {},
            error:function(){
                window.console && console.log("error:ajax失败");
            }
        });
    }

    /**
     * z载入HTML
     */
    function  loadPage() {
        var url = getUrlParam("url");
        var paramHtml = "";
        if(url == null ){
            return;
        }
        $.ajax({
            type:"get",
            url:"geturl?url=" + url + "", //需要获取的页面内容
            async:true,
            success:function(data){
                //console.log(data)
                paramHtml = data;
                $('#divpagecontent').html(data) //将获取到的内容放到当前页面的.contentBox中
                
                if(url == "advertisement"){
                	advertisementPage.initPage('0');
                }
                if(url == "etype"){
                	etypePage.initPage('0');
                }
                if(url == "equestion"){
                	eQuestionPage.initPage('0');
                }
                if(url == "answer"){
                	eAnswerPage.initPage('0');
                }
                if(url == "euser"){
                	euserPage.initPage('0');
                }
                if(url == "admin"){
                	adminPage.initPage('0');
                }
                if(url == "euseranswer_one"){
                	eAonePage.initPage('0');
                }
                
                
            }
        });
    }

    /**
     * 推出系统
     *
     */
    function  logoutFun() {
        $.ajax({
            type : "get",
            url : '/user/info/tb/logout',
            async : false,
            success : function(result)
            {
                if(result.code = "200")
                {
                    console.log(result);
                    $(location).prop('href','/index/geturl?url=login');
                }
            },
            error: function () { },
            complete: function () {},
            error:function(){
                window.console && console.log("error:ajax失败");
            }
        });
    }
    /**
     * 退出系统
     * 
     */
    function  setLogout() {
        var rtr = showConfirm('确定要退出吗?',logoutFun);

    }

    /**
     * 初始化函数
     * @param param
     */
    function  initPage(param) {
        loadPage();
        getUserInfo();
        $("#sLogout").on("click", function(){
            setLogout();
        });

        $("#aLogout").on("click", function(){
                setLogout();
        });

        $("#aUserHref").on("click", function(){
            setChange();
        });
        $('.nav-list li a').on("click", function(){
            $('.nav-list li').removeClass('active');
            $(this).parent().addClass('active');
        })

        $(".nav-list li a").each(function(){
            var span = $(this).children().last();
            var spanHtml = span.html();
            var curHearHtml = $("#curHear").html()==""?"首页":$("#curHear").html();
            if(curHearHtml ==spanHtml){
                $(this).parent().addClass('active');
            }
        });
    }
    var indexObj = {
        initPage: initPage //初始页面信息
    }
indexPage = indexObj;
})();