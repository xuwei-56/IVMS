function getStatusButton(status){
	var buttonA;
	switch(status){
		case 1: buttonA = "<a href='#' class='inner_btn' id='startcheck'>开始检测</a>";break;
		case 2: buttonA = "<a href='#' class='inner_btn' id='endcheck'>结束检测</a>";break;
	}
	return buttonA;
}
function getnormalCheckingForm1(){
  //得到正常过程送检送检单
  $.ajax({
    url:'./normalCheckingForm',
    type:'POST',
    data:{},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if(data.code == 1){
        var checkformdata = "<tr><th>检测单号</th><th>检测日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
        data.data.forEach(function(checkform){
          checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatusButton(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
          })
          $('#cfnormal').html(checkformdata);
      }
    }
  })
}
function getothersCheckingForm1(){
  //得到其他分类的送检单
  $.ajax({
    url:'./othersCheckingForm',
    type:'POST',
    data:{},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if(data.code == 1){
        var checkformdata = "<tr><th>检测单号</th><th>检测日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
        data.data.forEach(function(checkform){
          checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatusButton(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
          })
          $('#cfspecial').html(checkformdata);
      }
    }
  })
}

$(document).ready(function(){
	var userData = new Array();   //保存当前部门下所有人员
	var notifyMailData = new Array(); //保存抄送人邮箱
	$(function(){
		//得到正常过程送检送检单
	  $.ajax({
	    url:'./normalCheckingForm',
	    type:'POST',
	    data:{},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if(data.code == 1){
	        var checkformdata = "<tr><th>检测单号</th><th>检测日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
	        data.data.forEach(function(checkform){
	          checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatusButton(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
	          })
	          $('#cfnormal').html(checkformdata);
	      }
	    }
	  })
	})

	$(".admin_tab li a").click(function(){
		var liindex = $(".admin_tab li a").index(this);
		$(this).addClass("active").parent().siblings().find("a").removeClass("active");
		$(".admin_tab_cont").eq(liindex).fadeIn(150).siblings(".admin_tab_cont").hide();
		if (liindex == 0) {
			//得到正常过程送检送检单
			getnormalCheckingForm1();
		}
		if (liindex == 1) {
			//得到其他分类的送检单
			getothersCheckingForm1();
		}
		if (liindex == 3) {
			notifyMailData = getSessionUser();
		}
	});

	// 获取历史检测报告
	var count;
	var claid = null;
	var pid = null
	var cfid = null;
	$.ajax({
		url:'./historyCheckingForm',
		type:'POST',
		data:{'requestPageNum':1,"claId":null,'pid':null,'cfid':null},
		datatype:'json',
		beforeSend:function(){
			//$(".loading_area").fadeIn();
		},
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {			
				// 获取
				count = parseInt(data.msg);
				$('#pageToolFinished').html("");
				var pageSize = 20;
				$('#pageToolFinished').Paging({pagesize:pageSize,count:count,callback:function(page,size,count){
					$.ajax({
						url:'./historyCheckingForm',
						type:'POST',
						data:{'requestPageNum':page,"claId":claid,'pid':pid,'cfid':cfid},
						datatype:'json',
						success:function(data){
							data = JSON.parse(data);
							if(data.code == 1){
								//getPage(data.data[0].count,1,pageSize);
								count = parseInt(data.msg);
								var checkformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
								data.data.forEach(function(checkform){
									checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
				  				})
				  				$('#cffinished').html(checkformdata);
							}
						}
					})
				}});
				var checkformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
				data.data.forEach(function(checkform){
					checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
  				})
  				$('#cffinished').html(checkformdata);
  				$(".loading_area").fadeOut();
  				//送检类型
  				getClassify();
  				//获取项目
  				getProject();
			}else if (data.code == 0) {
				$('#mycheckform').html("<div>没有数据</div>");
			}
			else{
				alert("请先登录")
				location.href = "./login";
			}
			$(".loading_area").fadeOut();
		}
	})
	//查询送检单
  $('#button_searchCheckForm').click(function(){
		cfid = $('#bycfid').val();
		claid = $('#byclaId').val();
		pid = $('#bypId').val()
		if (cfid == null && cfid == "" && claid == null && claid == "" && pid == null && pid == "" ) {
			return false;
		}
		if (cfid == "") {
			cfid = null;
		};
		$('#pageToolFinished').html("");
		// 根据送检类型查找送检单
		$.ajax({
			url:'./historyCheckingForm',
			type:'POST',
			data:{'requestPageNum':1,"claId":claid,'pid':pid,'cfid':cfid},
			datatype:'json',
			beforeSend:function(){
				//$(".loading_area").fadeIn();
			},
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {			
					// 获取
					count = parseInt(data.msg);
					$('#pageToolFinished').html("");
					var pageSize = 20;
					$('#pageToolFinished').Paging({pagesize:pageSize,count:count,callback:function(page,size,count){
						$.ajax({
							url:'./historyCheckingForm',
							type:'POST',
							data:{'requestPageNum':page,"claId":null,'pid':null,'cfid':null},
							datatype:'json',
							success:function(data){
								data = JSON.parse(data);
								if(data.code == 1){
									//getPage(data.data[0].count,1,pageSize);
									count = parseInt(data.msg);
									var checkformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
									data.data.forEach(function(checkform){
										checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
					  				})
					  				$('#cffinished').html(checkformdata);
								}
							}
						})
					}});
					var checkformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
					data.data.forEach(function(checkform){
						checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
	  			})
	  			$('#cffinished').html(checkformdata);
	  			$(".loading_area").fadeOut();
				}else if (data.code == 0) {
					$('#mycheckform').html("<div>没有数据</div>");
				}
				else{
					alert("请先登录")
					location.href = "./login";
				}
				$(".loading_area").fadeOut();
			}
		})
	})
	// 查看详情
	$('#cffinished,#cfzero,#cfnormal,#cfspecial').delegate('#checkformdetail','click',function(){
		var cfid = $(this).parent().parent().find('td:first').text();
		$.ajax({
			url:'./user/myCheckingFormDetails',
			type:'POST',
			data:{'cfid':cfid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					$('#moveP_detail').val(data.data.cfmovep)
					$('#phoneNum_detail').val(data.data.cfphonenum);
					$('#mail_detail').val(data.data.cfemail);
					$('#claId_detail').val(data.data.classify.cname); 
					var notifymail = "";
					for (var i = 0; i < data.data.notifyPersonnelEmail.length; i++) {
						notifymail += data.data.notifyPersonnelEmail[i].npenotifyemail + ";";
					}
					$('#notifymail_detail').val(notifymail) ;
					if (data.data.cfurgentstatus == 2 ) {
						$('#urgentStatus_detail').val("加急")
						if (data.data.urgentFile != null ) {
							$('#urgentfile_detail').html("<a href='./urgentfile/"+data.data.urgentFile.ufname+"' download>"+data.data.urgentFile.ufname+"</a>")
						};
					}else{
						$('#urgentStatus_detail').val("普通")
					}
					/*$('#urgentStatus').val(data.data.cfurgentstatus);*/
					$('#cCId_detail').val(data.data.checkingClassify.ccname)
					$('#time_detail').val($.UnixToDate(data.data.cftime));
					$('#cfstatus_detail').val(getStatus(data.data.cfstatus))
					var  lname =  "默认";
					if (data.data.line != null) {
						lname = data.data.line.lname
					};
					$('#lId_detail').val(lname);
					var cname = "默认"
					if (data.data.cell != null) {
						cname = data.data.cell.cname;
					};
					$('#cId_detail').val(cname);
					$('#pId_detail').val(data.data.project.pname);
					$('#componentId_detail').val(data.data.cfcomponentid);
					$('#componentName_detail').val(data.data.cfcomponentname);
					$('#componentNum_detail').val(data.data.cfcomponentnum);
					$('#checkNum_detail').val(data.data.cfchecknum);
					if (data.data.wid != null) {
						$('#wId_detail').val(data.data.wid)
					} else{
						$('#wId_detail').val("默认");
					};
					$('#checkNum_detail').val(data.data.cfchecknum)
					$('#remark_detail').val(data.data.cfremark);
					if (data.data.claid == 2) {
						$('#cfreply_detail').val(data.data.cfreply)
						$('#cfreplyreport_detail').val(data.data.cfreplyreport)
					}
					if (data.data.cfreportfile != 0) {
						('#cfreportfile_detail').html("<a href='./cfreportfile/"+data.data.cfreportfile+"' download>"+data.data.cfreportfile+"</a>"); 
					};
					$('#pop_bg_user').fadeIn();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
	// 关闭弹框
  $(".falseBtn").click(function(){
    $("#pop_bg_user").fadeOut();
    $("#pop_bg_tool").fadeOut();
    $("#pop_bg_normal").fadeOut();
  });

  // 开始送检
  $('#cfnormal,#cfspecial').delegate('#startcheck','click',function(){
  	var cfid = $(this).parent().parent().find('td:first').text();
  	var SCFComponentId = $(this).parent().parent().find('td:eq(4)').text();
  	$.ajax({
  		url:'./user/updateCfStatusToOnCheck',
  		type:'POST',
  		data:{'cfid':cfid,'SCFComponentId':SCFComponentId},
  		datatype:'json',
  		success:function(data){
  			data = JSON.parse(data)
  			if (data.code == 1) {
  				getnormalCheckingForm1();
  				getothersCheckingForm1()
  			}else{
  				alert(data.msg)
  			}
  		}
  	})
  })

  // 结束送检
  $('#cfnormal,#cfspecial').delegate('#endcheck','click',function(){
  	var cfid = $(this).parent().parent().find('td:first').text();
  	var cname = $(this).parent().parent().find('td:eq(2)').text();
  	if (cname == "检具送检") {
  		$.ajax({
	  		url:'./user/getSessionUser',
	  		type:'POST',
	  		data:{},
	  		datatype:'json',
	  		success:function(data){
	  			data = JSON.parse(data)
	  			if (data.code == 1) {
	  				var ctid = $(this).parent().parent().find('td:eq(4)').text();
	  				var ctrmovep = $(this).parent().parent().find('td:eq(3)').text();
	  				var ctrmovetime = $(this).parent().parent().find('td:eq(1)').text();
	  				
	  				$('#ctrcheckman').val(data.data.cn);
	  				$('#cfid').val(cfid);
	  				$('#ctid').val(ctid);
	  				$('#ctrmovep').val(ctrmovep);
	  				$('#ctrmovetime').val(ctrmovetime)

  					$('#pop_bg_tool').fadeIn()
	  			}
	  		}
	  	})
  	}else{
  		$.ajax({
	  		url:'./user/getCfRemark',
	  		type:'POST',
	  		data:{'cfid':cfid},
	  		datatype:'json',
	  		success:function(data){
	  			data = JSON.parse(data)
	  			if (data.code == 1) {
	  				$('#normalcfid').val(cfid);
	  				$('#normalcheckremark').val(data.data)
	  				$('#pop_bg_normal').fadeIn();
	  			}
	  		}
	  	})
  	}
  })
	// 提交报告
	$('#normalcheckresult_btn').click(function(){
		var cfid = $('#normalcfid').val();
		var normalcheckresult = $('#normalcheckresult').val();
		var remark = $('#normalcheckremark').val();
		var normalreportfile = $('#normalreportfile')[0].files[0];
		var formdata = new FormData();
		formdata.append("cfId",cfid)
		formdata.append("cfStatus",normalcheckresult)
		formdata.append("cfRemark",remark)
		formdata.append("cfReportFile",normalreportfile)
		$.ajax({
  		url:'./user/submitCheckResult',
  		type:'POST',
  		cache:false,
			data:formdata,
			processData:false,
			contentType:false,
  		datatype:'json',
  		success:function(data){
  			data = JSON.parse(data)
  			if (data.code == 1) {
  				alert("提交成功")
  				$("#pop_bg_normal").fadeOut();
  				getothersCheckingForm1();
  			}
  		}
  	})
	})
	// 提交检具送检报告
	$('#toolcheckresult_btn').click(function(){
		var ctid = $('#ctid').val()
		var cfid = $('#cfid').val()
		var ctrmovep = $('#ctrmovep').val()
		var ctrcheckman = $('#ctrcheckman').val()
		var ctrmovetime = $.DateToUnix($('#ctrmovetime').val())
		console.log(ctrmovetime)
		var ctrcheckcontent = $('#ctrcheckcontent').val()
		if (ctrcheckcontent == null || ctrcheckcontent == "") {
			alert('请输入测量内容/技术规范')
			return false;
		};
		var ctrcheckvalue = $('#ctrcheckvalue').val()
		if (ctrcheckvalue == null || ctrcheckvalue == "") {
			alert("请输入实测值")
			return false;
		};
		var ctrchecktool = $('#ctrchecktool').val()
		if (ctrchecktool == null || ctrchecktool == "") {
			alert("请输入检测工具")
			return false;
		};
		var ctrcheckresult = $('#ctrcheckresult').val()
		var ctrremark = $('#ctrremark').val()
		$.ajax({
  		url:'./user/addCheckingToolResult',
  		type:'POST',
  		data:{"ctid":ctid,"ctrnum":cfid,"ctrmovecp":ctrmovep,"ctrcheckman":ctrcheckman,"ctrcheckcontent":ctrcheckcontent,"ctrchecktools":ctrchecktool,"ctrcheckresult":ctrcheckresult,"ctrmovetime":ctrmovetime,"ctrremark":ctrremark,"ctrcheckvalue":ctrcheckvalue},
  		datatype:'json',
  		success:function(data){
  			data = JSON.parse(data)
  			if (data.code == 1) {
  				alert("添加成功")
  				s('#pop_bg_tool').fadeIn();
  			}
  		}
  	})
})
	

	// 根据部门获取员工信息
	$('#departmentName').change(function(){
		var department = $('#departmentName').val();
		if (department == null) {
			return false;
		}
		userData = getUserInfoByDepartment(department);
	})

	//将选中抄送人，加入文本框，且存入notifyMailData数组
	$('#userName').change(function(){
		var mail = $('#userName').val()
		var cn = $('#userName').find("option:selected").text()
		if (num ==  null) {
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
		getWareHouse(claid);

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
	})
	// 获取对应产线下的单元
	$('#lId').change(function(){
		var lid = $('#lId').val();
		if (lid == null) { return false }
		getCellNames(lid);
	})

	// 判断检具送检的零件号是否正确，正确添加检具名字到零件名称
	$('#componentName').blur(function(){
		var claid = $('#claId').val();
		var ctid = $('#componentName').val();
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
		formdata.append('cfreplyreport',cfreplyreport)
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
