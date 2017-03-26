function showCheck(a){
	var c = document.getElementById("myCanvas");
  var ctx = c.getContext("2d");
	ctx.clearRect(0,0,1000,1000);
	ctx.font = "80px 'Microsoft Yahei'";
	ctx.fillText(a,0,100);  // 
	ctx.fillStyle = "white";
}
var code ;    
function createCode(){       
    code = ""; 
    $.ajax({
      url:'./code/getAuthCode',
      type:'POST',
      data:{'type':'type'},
      datatype:'json',
      success:function(data){
        data = JSON.parse(data);
        if (data.code == 1) {
          code = data.data
          showCheck(code);
        }else{
          createCode();
        }
      }
    })
}
          
function validate () {
    var inputCode = document.getElementById("Vcode").value.toUpperCase();
    var codeToUp=code.toUpperCase();
    if(inputCode.length <=0) {
      document.getElementById("Vcode").setAttribute("placeholder","输入验证码");
      return false;
    }
    else if(inputCode != codeToUp ){
      document.getElementById("Vcode").value="";
      document.getElementById("Vcode").setAttribute("placeholder","验证码错误");
      return false;
    }

}