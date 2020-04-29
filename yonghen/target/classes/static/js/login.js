(function() {

    /**
     * 初始化
     */
    function  initPage() {
        var remFlag = $.cookie('rember');
        var remUser = $.cookie('username');
        if(remFlag == "true"){
            $("#chkRember").attr('checked',true)
        }
        if(remUser != ""){
            $("#userinput").val(remUser);
        }
        $("#loginbtn").on("click", function(){
            var userName = $("#userinput").val();
            var userPwd =  $("#pwdinput").val();
            var remFlag = $("#chkRember").is(':checked')
            if(userName == ""){
                showTip("请输入用户名");
                return;
            }else if(userPwd == ""){
                showTip("请输入密码");
                return;
            }
            if(remFlag){
                $.cookie('username', userName);
                $.cookie('rember', remFlag);
            }else{
                $.cookie('username', null);
                $.cookie('rember', null);
            }

            setLogin(userName,userPwd,remFlag);
        });
    }

    function  setLogin(name,pwd,remFlag) {
        var jsonParams={
        	username:name,
        	password:pwd,
            remember:remFlag
        };
        $.ajax({
            type : "get",
            url : '/sys/admin/login',
            data: jsonParams,
            async : false,
            success : function(result)
            {
                if(result.code = "200")
                {
                    console.log(result);
                    if(result.message !="SUCCESS"){
                        showTip(result.message);
                    }
                    else{
                    	sessionStorage.setItem('adminid', result.data.userId);
                        $(location).prop('href', "/index/index?url=summary");
                    }
                }
            },
            error: function () {},
            complete: function () {
                var test = 1;
            },
            error:function(){
                window.console && console.log("error:ajax失败");
            }
        });
    }


    var loginObj = {
        initPage: initPage, //初始页面信息
        showTip: showTip
    }


    loginPage = loginObj;
})();