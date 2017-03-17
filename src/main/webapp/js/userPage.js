/*var userData;   //保存当前部门下所有人员
var notifyMailData = new Array(); //保存抄送人邮箱*/
$(document).ready(function(){
	var userData = new Array();   //保存当前部门下所有人员
	var notifyMailData = new Array(); //保存抄送人邮箱
	//得到当前登录员工信息
	$.ajax({
		url:'./user/getSessionUser',
		type:'POST',
		data:{},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				$('#moveP').val(data.data.cn)
				$('#phoneNum').val(data.data.mobile)
				$('#mail').val(data.data.mail)
				notifyMailData[0] = data.data; //通知送检人本人
				$('#userNameList').html("<span class='userNameDel'>"+data.data.cn+"<a href='#' class='deleteEmail'>X</a></span>")
			}
			else{
				alert("请先登录")
				location.href = "./login";
			}
		}
	})
	// 获取部门信息
	$.ajax({
		url:'./user/getDepartments',
		type:'POST',
		data:{},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				var department = "<option></option>";
				for (var i = 0; i < data.data.length; i++) {
					department += "<option value="+data.data[i]+">"+data.data[i]+"</option>"
				}
				$('#departmentName').html(department)
			}else{
				alert("获取部门信息失败！错误信息：" + data.msg)
			}
		}
	})
	// 获取送检类型
	$.ajax({
		url:'./user/getClassify',
		type:'POST',
		data:{},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				var Classify = "<option></option>";
				for (var i = 0; i < data.data.length; i++) {
					Classify += "<option value="+data.data[i].claid+">"+data.data[i].cname+"</option>"
				}
				$('#claId').html(Classify)
			}else{
				alert("获取送检类型失败！错误信息：" + data.msg)
			}
		}
	})
	// 获取产线
	$.ajax({
		url:'./user/getLines',
		type:'POST',
		data:{},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				var Lines = "<option></option>";
				for (var i = 0; i < data.data.length; i++) {
					Lines += "<option value="+data.data[i].lid+">"+data.data[i].lname+"</option>"
				}
				$('#lId').html(Lines)
			}else{
				alert("获取产线失败！错误信息：" + data.msg)
			}
		}
	})

	// 根据部门获取员工信息
	$('#departmentName').change(function(){
		var department = $('#departmentName').val();
		if (department == null) {
			return false;
		}
		$.ajax({
			url:'./user/getUserInfoByDepartment',
			type:'POST',
			data:{'department':department},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					var user = "<option></option>";
					userData = data.data; 
					for (var i = 0; i < data.data.length; i++) {
						user += "<option value="+i+">"+data.data[i].cn+"</option>";
					}
					$('#userName').html(user);
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
	//将选中抄送人，加入文本框，且存入notifyMailData数组
	$('#userName').change(function(){
		var num = $('#userName').val()
		if (num ==  null) {
			return false;
		}
		var notifyNum = $('#userNameList span').size(); // 获取当前抄送人数
		$('#userNameList').append("<span class='userNameDel'>"+userData[num].cn+"<a href='#' class='deleteMail' id='deleteMail'>X</a></span>");
		notifyMailData[notifyNum] = userData[num];

	})
	// 删除抄送人邮箱
	$('#userNameList').delegate('#deleteMail','click',function(){
		var num = $(this).parent().index();   // 得到删除的抄送人在数组中的位置
		console.log(num);
		notifyMailData.splice(num,1);   // 删除此位置的邮箱
		console.log(notifyMailData);
		$(this).parent().remove();
	})

	// 获取对应送检类型下的检测类型
	$('#claId').change(function(){
		var claid = $('#claId').val();
		if (claid == null) { return false }
		$.ajax({
			url:'./user/getCheckingClassify',
			type:'POST',
			data:{'ClassifyId':claid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					var CheckingClassify = "<option></option>";
					for (var i = 0; i < data.data.length; i++) {
						CheckingClassify += "<option value="+data.data[i].ccid+">"+data.data[i].ccname+"</option>";
					}
					$('#cCId').html(CheckingClassify);
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})

	// 获取对应产线下的单元
	$('#lId').change(function(){
		var lid = $('#lId').val();
		if (lid == null) { return false }
		$.ajax({
			url:'./user/getCellNames',
			type:'POST',
			data:{'LineId':lid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					var CellNames = "<option></option>";
					for (var i = 0; i < data.data.length; i++) {
						CellNames += "<option value="+data.data[i].cid+">"+data.data[i].cname+"</option>";
					}
					$('#cId').html(CellNames);
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})

	// 获取项目
	
	$.ajax({
		url:'./user/getProjects',
		type:'POST',
		data:{},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				var Projects = "<option></option>";
				for (var i = 0; i < data.data.length; i++) {
					Projects += "<option value="+data.data[i].pid+">"+data.data[i].pname+"</option>";
				}
				$('#pId').html(Projects);
			}else{
				alert(data.msg)
				return false;
			}
		}
	})
	
})