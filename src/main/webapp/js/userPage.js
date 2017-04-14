
$(document).ready(function(){
	var notifyMailData = new Array(); //保存抄送人邮箱
	//得到当前登录员工信息
	notifyMailData = getSessionUser();
	

	// 根据部门获取员工信息
	$('#departmentName').change(function(){
		var department = $('#departmentName').val();
		if (department == null) {
			return false;
		}
		getUserInfoByDepartment(department);
	})
	//将选中抄送人，加入文本框，且存入notifyMailData数组
	$('#userName').change(function(){
		var mail = $('#userName').val()
		var cn = $('#userName').find("option:selected").text()
		if (mail ==  null) {
			return false;
		}
		var notifyNum = $('#userNameList span').size(); // 获取当前抄送人数
		$('#userNameList').append("<span class='userNameDel'>"+cn+"<a href='#' class='deleteMail' id='deleteMail'>X</a></span>");
		notifyMailData[notifyNum] = mail;

	})
	// 删除抄送人邮箱
	$('#userNameList').delegate('#deleteMail','click',function(){
		var num = $(this).parent().index();   // 得到删除的抄送人在数组中的位置
		notifyMailData.splice(num,1);   // 删除此位置的邮箱
		$(this).parent().remove();
	})

	// 获取对应送检类型下的检测类型
	$('#claId').change(function(){
		var claid = $('#claId').val();
		if (claid == null) { return false }
		getCheckingClassify(claid);

		// 获取库位信息
		getWarehouse(claid);

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
			$('#wId').html("<option value='0'>默认</option>")
		}
		if (claid > 1) {
			$('#lId').html("<option value='0'>默认</option>")
			$('#cId').html("<option value='0'>默认</option>")
		};
	})

	// 获取对应产线下的单元
	$('#lId').change(function(){
		var lid = $('#lId').val();
		if (lid == null) { return false }
		getCellNames(lid);
	})

	// 判断检具送检的零件号是否正确，正确添加检具名字到零件名称
	$('#componentName').change(function(){
		var claid = $('#claId').val();
		var ctid = $('#componentId').val();
		if (claid == 6) {
			$.ajax({
		    url:'./user/judgeCtidAndGetCTName',
		    type:'POST',
		    data:{'ctid':ctid},
		    datatype:'json',
		    success:function(data){
		      data = JSON.parse(data);
		      if (data.code == 1) {
		        $('#componentName').val(data.data);
		      }else{
		        alert(data.msg)
		      }
		    }
		  })
		}
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
		formdata.append('cfreplyreport',cfreplyreport)
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