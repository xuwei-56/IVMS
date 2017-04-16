
$(document).ready(function(){
	//验证码
  	createCode();

  	//前端验证验证码
  	$('#Vcode').blur(function(){
  		validate ();
  	})
	//检测登录
	$('#button_login').click(function() {

		var userId = removeAllSpace($('#user').val());
		var password = removeAllSpace($('#password').val());
		var verifyCode = removeAllSpace($('#Vcode').val());
		if (userId == "" || password == "" || verifyCode == "") {
			if (userId == "") {
				alert("请输入管理员帐号");
				return false;
			}
			if (password == "") {
				alert("请输入管理员密码");
				return false;
			}
			if (verifyCode == "") {
				alert("请输入验证码");
				return false;
			}
		}else{
			$.ajax({
				url:"./user/login",
				type:"POST",
				data:{"username":userId,"password":password,"verifyCode":verifyCode},
				datatype:"json",
				success:function(data){
					data = JSON.parse(data);
					if(data.code > 101){
						location.href="./user_index";
					}else if (data.code == 101) {
						location.href="./admin_index";
					}else{
						createCode()
						alert(data.msg)
					}
				}
			});
		}
	})
	//回车提交事件
	$("body").keydown(function() {
	    if (event.keyCode == "13") {//keyCode=13是回车键
	        $('#button_login').click();
	    }
	});
	//--------回车提交事件完毕---------------------//
})