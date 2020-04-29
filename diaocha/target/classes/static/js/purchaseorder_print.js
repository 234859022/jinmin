(function() {
    $("#printbtn2").click(function(){
    	print();
    });
    
    $("#cancelbtn2").click(function(){
    	parent.layer.closeAll();
    });  
//	print();
    function print(){
       //备份body
       var body = window.document.body.innerHTML;
       //获取要打印的内容
       var form = $("#printdetail").html();
       window.document.body.innerHTML = form;
       //调用window的打印功能
       window.print();
       //恢复原内容
       window.document.body.innerHTML = body;
       parent.layer.closeAll();
    }
})();