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
				notifyMailData[0] = data.data.mail; //通知送检人本人
				$('#userNameList').html("<span class='userNameDel'>"+data.data.cn+"<a href='#' class='deleteEmail'>X</a></span>")
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


			}
			else{
				alert("请先登录")
				location.href = "./login";
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
		notifyMailData[notifyNum] = userData[num].mail;

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
					var CheckingClassify = "";
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

		// 获取库位信息
		$.ajax({
			url:'./user/',
			type:'POST',
			data:{'ClassifyId':claid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					var warehouse = "";
					for (var i = 0; i < data.data.length; i++) {
						warehouse += "<option value="+data.data[i].wid+">"+data.data[i].wid+"</option>";
					}
					$('#wId').html(warehouse);
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
		if (claid == 2) {
			$('#checkNum').removeAttr("disabled","true");
			$('#cfreply').removeAttr("disabled","true");
			$('#cfreply').val("");
			$('#cfreplyreport').removeAttr("disabled","true");
		}else{
			$('#checkNum').attr("disabled","true");
			$('#checkNum').val("1");
			$('#cfreply').attr("disabled","true");
			$('#cfreply').val("无");
			$('#cfreplyreport').attr("disabled","true");
			$('#cfreplyreport').val("0");
		}
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

	

	// 提交
	$('#userCheckformInput').click(function(){
		var cfmovep = $('#moveP').val();  // 送检人
		var cfphonenum = $('#phoneNum').val();  // 联系电话
		var cfemail = $('#mail').val();   // 邮箱
		var claid = $('#claId').val();   // 送检类型
		if (claid == null || claid == "") {
			alert("请选择送检类型")
			return false;
		}
		var cfurgentstatus = $('#urgentStatus').val();  // 紧急状态
		var ccid = $('#cCId').val();  // 检测类型
		if (ccid == null || ccid== "") {
			alert("请选择检测类型")
			return false;
		}
		var lid = $('#lId').val();   // 所属产线
		if (lid == null || lid== "") {
			alert("请选择所属产线")
			return false;
		}
		var cid = $('#cId').val();   // 所属单元
		if (cid == null || cid== "") {
			alert("请选择所属单元")
			return false;
		}
		var pid = $('#pId').val();   // 所属项目
		if (pid == null || pid == "") {
			alert("请选择所属项目")
			return false;
		}
		var cfcomponentid = $('#componentId').val();    // 零件编号
		if (cfcomponentid == null || cfcomponentid == "") { 
			alert("请输入零件编号")
			return false;
		}
		var cfcomponentname = $('#componentName').val();   // 零件名称
		if (cfcomponentname == null || cfcomponentname == "") {
			alert("请输入零件名称")
			return false;
		}
		var cfcomponentnum = $('#componentNum').val();  // 零件数量
		if (cfcomponentnum == null || cfchecknum== "") {
			alert("请输入零件数量")
			return false;
		}
		var wid = $('#wId').val();   // 放置库位
		if (wid == null ) {
			alert("请选择放置库位")
			return false;
		}
		var cfchecknum = $('#checkNum').val();   // 送检次数
		if (cfchecknum == null || cfchecknum == "") {
			alert("请选择送检次数")
			return false;
		}
		var cfreply = $('#cfreply').val();    // 供应商名称
		if (cfreply == null) {
			alert("请输入供应商名称")
			return false;
		};
		var cfreplyreport = $('#cfreplyreport').val();   // 供应商报告
		var cfremark = $('#remark').val();   // 备注信息
		cfremark = stripscript(cfremark);

		var formdata = new FormData();
		var cfremarkfile = $('#urgentfile')[0].files[0];
		console.log(cfremarkfile);
		if (cfurgentstatus == 2) {
			if (cfremarkfile == null || cfremarkfile == "") {
				alert("请上传紧急说明文件")
				return false;
			}else{
				formdata.append('urgentfile',cfremarkfile); 
			}
		}else{
			formdata.append('urgentfile',null); 
		}

		
		formdata.append('copySendEmails',notifyMailData)
		formdata.append('lid',lid)
		formdata.append('cid',cid)
		formdata.append('pid',pid)
		formdata.append('claid',claid)
		formdata.append('ccid',ccid)
		formdata.append('cfmovep',cfmovep)
		formdata.append('cfphonenum',cfphonenum)
		formdata.append('cfemail',cfemail)
		formdata.append('cfcomponentname',cfcomponentname)
		formdata.append('cfcomponentid',cfcomponentid)
		formdata.append('cfcomponentnum',cfcomponentnum)
		formdata.append('cfchecknum',cfchecknum)
		formdata.append('cfremark',cfremark)
		formdata.append('cfurgentstatus',cfurgentstatus)
		formdata.append('wid',wid)
		formdata.append('cfreply',cfreply)
		formdata.append('cfreplyreport'.cfreplyreport)
		console.log(formdata)
		/*'lid':lid,'cid':cid,'pid':pid,'wid':wid,'claid':claid,'ccid':ccid,'cfmovep':cfmovep,'cfphonenum':cfphonenum,
		'cfemail':cfemail,'cfcomponentname':cfcomponentname,'cfcomponentid':cfcomponentid,
		'cfcomponentnum':cfcomponentnum,'cfchecknum':cfchecknum,'cfremark':cfremark,formdata,'cfurgentstatus':cfurgentstatus
		/*,'copySendEmail':notifyMailData*/
		$.ajax({
			url:'./user/addSendCheckInfo',
			type:'POST',
			cache:false,
			data:formdata,
			processData:false,
			contentType:false,
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					alert("提交成功")
					$('#checkNum').attr("disabled","true");
					$('#checkNum').val("1");
					$('#cfreply').attr("disabled","true");
					$('#cfreply').val("无");
					$('#cfreplyreport').attr("disabled","true");
					$('#cfreplyreport').val("0");
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
	
})