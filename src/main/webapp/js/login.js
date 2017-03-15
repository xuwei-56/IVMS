
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
				alert("请输入管理员密码");
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
					if(data.code == 1){
						location.href="./index";
					}else{
						//refresh(this);
						alert(data.msg)
				}
				}
			});
		}
	})
})