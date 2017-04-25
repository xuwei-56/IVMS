
$(document).ready(function(){
	$(".admin_tab li a").click(function(){
		var liindex = $(".admin_tab li a").index(this);
		$(this).addClass("active").parent().siblings().find("a").removeClass("active");
		$(".admin_tab_cont").eq(liindex).fadeIn(150).siblings(".admin_tab_cont").hide();
	});

	// 获取量检具
	var toolsCount;
	$.ajax({
		url:'./checkingToolsInfo',
		type:'POST',
		data:{'requestPageNum':1,'CTUseItem':0,'CTStatus':null,'CTUseLine':0,'CTType':0},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				toolsCount = parseInt(data.msg);
				$('#pageToolTools').html("");
				var pageSize = 20;
				$('#pageToolTools').Paging({pagesize:pageSize,count:toolsCount,callback:function(page,size,count){
					$.ajax({
						url:'./checkingToolsInfo',
						type:'POST',
						data:{'requestPageNum':page,'CTUseItem':0,'CTStatus':null,'CTUseLine':0,'CTType':0},
						datatype:'json',
						success:function(data){
							data = JSON.parse(data);
							if(data.code == 1){
								//getPage(data.data[0].count,1,pageSize);
								toolsCount = parseInt(data.msg);
								var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪类型</th><th>领用产线</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:350px;'>操作</th></tr>";
								data.data.forEach(function(checktool){
									if (checktool.ctstatus == 0) {
										checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a><a href='#' class='inner_btn' id='deleteTool'>删除</a></td></tr>";
									}else if(checktool.ctstatus == 2){
										checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a><a href='#' class='inner_btn' id='servicingOver'>维修完毕</a></td></tr>";
									}else{
										checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
									}
								})
				  				$('#cttable').html(checkformdata);
							}
						}
					})
				}});
				var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪类型</th><th>领用产线</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:350px;'>操作</th></tr>";
				data.data.forEach(function(checktool){
					if (checktool.ctstatus == 0) {
						checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a><a href='#' class='inner_btn' id='deleteTool'>删除</a></td></tr>";
					}else if(checktool.ctstatus == 2){
						checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a><a href='#' class='inner_btn' id='servicingOver'>维修完毕</a></td></tr>";
					}else{
						checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
					}
				})
				$('#cttable').html(checktooldata);
			}else if (data.code == 0) {
				$('#cttable').html("<div>没有数据</div>");
			}else{
				alert("获取量检具失败！错误信息："+data.msg)
				return false;
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
        var department = "<option value=''>请选择部门</option>";
        for (var i = 0; i < data.data.length; i++) {
          department += "<option value="+data.data[i]+">"+data.data[i]+"</option>"
        }
        $('#bydepartmentname').html(department)
      }else{
        alert("获取部门信息失败！错误信息：" + data.msg)
        //return false;
      }
    }
  })
  
	// 确认认领人
	$('#cttable').delegate('#confirmuser_btn','click',function(){
		var ctid = $(this).parent().parent().find("td:eq(0)").text();
		var ctname = $(this).parent().parent().find("td:eq(1)").text();
		$('#ctid_user').val(ctid);
		$('#ctname_user').val(ctname)
		$('#pop_bg_confirmuser').fadeIn();
		$.ajax({
			url:'./user/getCheckingToolReceiver',
			type:'POST',
			data:{'ctid':ctid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					$('#ctuser').html("<option value='"+data.data.ctreceiver+"'>"+data.data.ctreceiver+"</option>")
					$('#ctuseitem_user').val(data.data.ctuseitem)
					var linetemp = data.data.ctuseline;
					// 获取产线
				  $.ajax({
				    url:'./user/getLines',
				    type:'POST',
				    data:{},
				    datatype:'json',
				    success:function(data){
				      data = JSON.parse(data);
				      if (data.code == 1) {
				        var Lines = "<option value='"+linetemp+"'>"+linetemp+"</option>";
				        for (var i = 0; i < data.data.length; i++) {
				          Lines += "<option value='"+data.data[i].lname+"'>"+data.data[i].lname+"</option>"
				        }
				      }else{
				        console.log("获取产线失败！错误信息：" + data.msg)
				        //return false;
				      }
				        $('#ctuseline_user').html(Lines)
				    }
				  })
				  $('#ctusestation_user').val(data.data.ctusestation)
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
	$('#bydepartmentname').change(function(){
		var department = $('#bydepartmentname').val();
		if (department == null || department == "") {
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
	        var user = "";
	        /*console.log(userData)*/
	        for (var i = 0; i < data.data.length; i++) {
	          user += "<option value="+data.data[i].mail+">"+data.data[i].cn+"</option>";
	        }
	        $('#ctuser').html(user);
	      }else{
	        alert(data.msg)
	        //return false;
	      }
	    }
	  })
	})
	// 提交认领人
	$('#tooluseconfirm_btn').click(function(){
		var ctid = $('#ctid_user').val();
		var ctuser = $('#ctuser').find("option:selected").text()
		var ctuseline = $('#ctuseline_user').find('option:selected').text();
		var ctuseitem = $('#ctuseitem_user').val();
		var ctusestation = $('#ctusestation_user').val();
		if (ctuser == null || ctuser == "") {
			alert("请选择领用人！")
			return false
		}
		$.ajax({
			url:'./user/addCheckingToolReceiver',
			type:'POST',
			data:{'ctid':ctid,"ctreceiver":ctuser,'ctuseline':ctuseline,'ctuseitem':ctuseitem,'ctusestation':ctusestation},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					alert("添加成功！");
					$(".pop_bg").fadeOut();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
	// 关闭认领人弹框
	$(".falseBtn").click(function(){
    $(".pop_bg").fadeOut();
  });
  // 维修好 通知领用人
  $('#cttable').delegate('#servicingOver','click',function(){
  	var ctid = $(this).parent().parent().find("td:eq(0)").text();
		$.ajax({
			url:'./user/updateCheckingToolStatus',
			type:'POST',
			data:{'ctid':ctid,'ctStatus':6},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					alert('通知成功！')
					myrefresh();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
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
        var Lines = "";
        for (var i = 0; i < data.data.length; i++) {
          Lines += "<option value='"+data.data[i].lname+"'>"+data.data[i].lname+"</option>"
        }
      }else{
        console.log("获取产线失败！错误信息：" + data.msg)
      }
        $('#byCTUseLine').append(Lines)
    }
  })
  
  // 查询检具
	$('#button_searchCheckTools').click(function(){
		var CTUseItem = $('#byCTUseItem').val();
		var CTStatus = $('#byCTStatus').val();
		var CTUseLine = $('#byCTUseLine').val();
		var CTType = $('#byCTType').val();
		if (CTStatus == null && CTUseItem == null && CTUseItem == "" && CTUseLine == "" && CTUseLine == null && CTType == "" && CTType == null) {
			return false;
		}
		if (CTUseItem == "" || CTUseItem == null ) {
			CTUseItem = 0;
		};
		if (CTUseLine == "" || CTUseLine == null) {
			CTUseLine = 0;
		}
		if (CTType == "" || CTType == null ) {
			CTType = 0;
		}
		$.ajax({
			url:'./checkingToolsInfo',
			type:'POST',
			data:{'requestPageNum':1,'CTUseItem':CTUseItem,'CTStatus':CTStatus,'CTUseLine':CTUseLine,'CTType':CTType},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					toolsCount = parseInt(data.msg);
					$('#pageToolTools').html("");
					var pageSize = 20;
					$('#pageToolTools').Paging({pagesize:pageSize,count:toolsCount,callback:function(page,size,count){
						$.ajax({
							url:'./checkingToolsInfo',
							type:'POST',
							data:{'requestPageNum':page,'CTUseItem':CTUseItem,'CTStatus':CTStatus,'CTUseLine':CTUseLine,'CTType':CTType},
							datatype:'json',
							success:function(data){
								data = JSON.parse(data);
								if(data.code == 1){
									//getPage(data.data[0].count,1,pageSize);
									toolsCount = parseInt(data.msg);
									var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪类型</th><th>领用产线</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:350px;'>操作</th></tr>";
									data.data.forEach(function(checktool){
										if (checktool.ctstatus == 0) {
											checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a><a href='#' class='inner_btn' id='deleteTool'>删除</a></td></tr>";
										}else if(checktool.ctstatus == 2){
											checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a><a href='#' class='inner_btn' id='servicingOver'>维修完毕</a></td></tr>";
										}else{
											checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
										}
									})
					  			$('#cttable').html(checkformdata);
								}
							}
						})
					}});
					var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪类型</th><th>领用产线</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:350px;'>操作</th></tr>";
					data.data.forEach(function(checktool){
						if (checktool.ctstatus == 0) {
							checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a><a href='#' class='inner_btn' id='deleteTool'>删除</a></td></tr>";
						}else if(checktool.ctstatus == 2){
							checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a><a href='#' class='inner_btn' id='servicingOver'>维修完毕</a></td></tr>";
						}else{
							checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.cttype+"</td><td>"+checktool.ctuseline+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='#' class='inner_btn' id='confirmuser_btn'>登记</a><a href='#' class='inner_btn' id='updateTool'>修改</a><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
						}
					})
					$('#cttable').html(checktooldata);
				}else if (data.code == 0) {
					$('#cttable').html("<div>没有数据</div>");
				}else{
					alert("获取量检具失败！错误信息："+data.msg)
					return false;
				}
			}
		})
	})
  //  获取检具详情可更改
  $('#cttable').delegate('#updateTool','click',function(){
		var ctid = $(this).parent().parent().find("td:eq(0)").text();
		// 获取检具附件
		$.ajax({
			url:'./user/myCheckingToolsFiles',
			type:'POST',
			cache:false,
			data:{'ctid':ctid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					var filedata = "";
					var filename  = "";
					data.data.forEach(function(file){
						filename = file.ctfname.substr(file.ctfname.lastIndexOf('_')+1)//获取最后一个下滑线之后的字符串
						filedata += "<li><span style='display:none;'>"+file.ctfid+"</span><span>"+ filename +"</span><a id='deletefile'>删除</a></li>"
					})
					$('#upload_file').html(filedata);
				}else if(data.code == 0){
					$('#upload_file').html(data.msg);
				}
			}
		})
		// 检具履历
		$.ajax({
			url:'./user/myCheckingToolsDetails',
			type:'POST',
			data:{'ctid':ctid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					$('#ctid_update').val(data.data.CTId);
					$('#ctname_update').val(data.data.CTName)
					$('#cttype_update').val(data.data.CTType)
					$('#ctcheckprogram_update').val(data.data.CTCheckProgram)
					$('#ctcheckway_update').val(data.data.CTCheckWay)
					$('#ctproducer_update').val(data.data.CTProducer)
					$('#ctcheckcycle_update').val(data.data.CTCheckCycle)
					$('#ctproductionnum_update').val(data.data.CTProductionNum)
					$('#ctmsa_update').val(data.data.CTMSA)
					$('#ctnorms_update').val(data.data.CTNorms)
					$('#ctprocision_update').val(data.data.CTProcision)
					$('#ctoriginalnum_update').val(data.data.CTOriginalNum)
					$('#ctassetnum_update').val(data.data.CTAssetNum)
					$('#ctsize_update').val(data.data.CTSize)
					$('#ctresolation_update').val(data.data.CTResolation)
					$('#ctchecktemperature_update').val(data.data.CTCheckTemperature)
					$('#ctcheckhumidiry_update').val(data.data.CTCheckHumidiry)
					$('#ctremark_update').val(data.data.CTRemark)
					$('#pop_bg_updateTool').fadeIn();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
  })
	// 删除附件
	$('#pop_bg_updateTool').delegate('#upload_file','click',function(){
		var ctfid = $(this).parent().find("span:eq(1)").text();
		if (window.confirm("你确认要删除吗？")) {
			$.ajax({
				url:'./user/deleteCheckingToolFile',
				type:'POST',
				cache:false,
				data:{'ctfid':ctfid},
				datatype:'json',
				success:function(data){
					data = JSON.parse(data);
					if (data.code == 1) {
						alert("删除附件成功！");
						$(this).parent().remove();
						
					}else if(data.code == 0){
						$('#upload_file').html(data.msg);
					}
				}
			})
		}else{
			return false;
		}
		
	})
  // 提交修改
  $('#updatetool_btn').click(function(){
  	var ctid = $('#ctid_update').val();
		if (ctid == null || ctid == "") {
			alert("请输入量仪管理编号！")
			return false;
		}
		var ctname = $('#ctname_update').val();
		if (ctname == null || ctname == "") {
			alert("请输入量仪名称！")
			return false
		}
		var cttype = $('#cttype_update').val();
		if (cttype == null || cttype == "") {
			alert("请输入量仪类型！")
			return false
		}
		var ctproducer = $('#ctproducer_update').val();
		if (ctproducer == "" || ctproducer == null) {
			alert("请输入量仪生产商！")
			return false
		}
		var ctproductionnum = $('#ctproductionnum_update').val()
		if (ctproductionnum == null || ctproductionnum == "") {
			alert("请输入量仪制造编号！")
			return false
		}
		var ctprocision = $('#ctprocision_update').val()
		if (ctprocision == null || ctprocision == "") {
			alert("请输入量仪精度！")
			return false
		}
		var ctresolation = $('#ctresolation_update').val()
		if (ctresolation == null || ctresolation == "") {
			alert("请输入量仪分度值！")
			return false
		}
		var ctnorms = $('#ctnorms_update').val()
		if (ctnorms == null || ctnorms == "") {
			alert("请输入量仪规格！")
			return false
		}
		var ctcheckprogram = $('#ctcheckprogram_update').val()
		if (ctcheckprogram == null || ctcheckprogram == "") {
			alert("请输入量仪校准程序！")
			return false
		}
		var ctcheckway = $('#ctcheckway_update').val()
		var ctcheckcycle = $('#ctcheckcycle_update').val()
		var ctmsa = $('#ctmsa_update').val()
		var ctoriginalnum = $('#ctoriginalnum_update').val()
		if (ctoriginalnum == null || ctoriginalnum == "") {
			alert("请输入量仪溯源编号！")
			return false
		}
		var ctassetnum = $('#ctassetnum_update').val()
		if (ctassetnum == null || ctassetnum == "") {
			alert("请输入量仪资产编号！")
			return false
		}
		var ctsize = $('#ctsize_update').val();
		if (ctsize == null || ctsize == "") {
			alert("请输入量仪尺寸描述！")
			return false
		}
		var ctchecktemperature = $('#ctchecktemperature_update').val()
		if (ctchecktemperature == null || ctchecktemperature == "") {
			alert("请输入量仪校验温度！")
			return false
		}
		var ctcheckhumidiry = $('#ctcheckhumidiry_update').val()
		if (ctcheckhumidiry == null || ctcheckhumidiry == "") {
			alert("请输入量仪校验湿度！")
			return false
		}
		var ctremark = $('#ctremark_update').val()
		ctremark = stripscript(ctremark);
		var ctfile = $('#ctfile_update')[0].files;
		var formdata = new FormData();
		for (var i = 0; i < ctfile.length; i++) {
			formdata.append('checkingToolFiles',ctfile[i])
		};
		formdata.append('ctid',ctid)
		formdata.append('ctname',ctname)
		formdata.append('cttype',cttype)
		formdata.append('ctproducer',ctproducer)
		formdata.append('ctproductionnum',ctproductionnum)
		formdata.append('ctprocision',ctprocision)
		formdata.append('ctresolation',ctresolation)
		formdata.append('ctnorms',ctnorms)
		formdata.append('ctcheckprogram',ctcheckprogram)
		formdata.append('ctcheckway',ctcheckway)
		formdata.append('ctcheckcycle',ctcheckcycle)
		formdata.append('ctmsa',ctmsa)
		formdata.append('ctoriginalnum',ctoriginalnum)
		formdata.append('ctassetnum',ctassetnum)
		formdata.append('ctsize',ctsize)
		formdata.append('ctremark',ctremark)
		formdata.append('ctchecktemperature',ctchecktemperature)
		formdata.append('ctcheckhumidiry',ctcheckhumidiry)
		$.ajax({
			url:'./user/updateCheckingTool',
			type:'POST',
			data:formdata,
			datatype:'json',
			contentType:false,
			processData:false,
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					alert("更改成功");
					myrefresh();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
  //  删除未领用的检具
  $('#cttable').delegate('#deleteTool','click',function(){
  	var ctid = $(this).parent().parent().find("td:eq(0)").text();
  	$.ajax({
			url:'./user/deleteCheckingTool',
			type:'POST',
			data:{'ctid':ctid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					alert("删除成功");
					myrefresh();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
  })
	// 文件列表
	$('#ctfile').change(function(){
		var files = $('#ctfile')[0].files;
		var filesname = '';
		for (var i = files.length - 1; i >= 0; i--) {
			filesname += files[i].name + "；"
		}
		$('#ctfilename').text(filesname)
	})

	// 检具信息录入提交
	$('#ToolInput').click(function(){
		var ctid = $('#ctid').val();
		if (ctid == null || ctid == "") {
			alert("请输入量仪管理编号！")
			return false;
		}
		var ctname = $('#ctname').val();
		if (ctname == null || ctname == "") {
			alert("请输入量仪名称！")
			return false
		}
		var cttype = $('#cttype').val();
		if (cttype == null || cttype == "") {
			alert("请输入量仪类型！")
			return false
		}
		var ctproducer = $('#ctproducer').val();
		if (ctproducer == "" || ctproducer == null) {
			alert("请输入量仪生产商！")
			return false
		}
		var ctproductionnum = $('#ctproductionnum').val()
		if (ctproductionnum == null || ctproductionnum == "") {
			alert("请输入量仪制造编号！")
			return false
		}
		var ctprocision = $('#ctprocision').val()
		if (ctprocision == null || ctprocision == "") {
			alert("请输入量仪精度！")
			return false
		}
		var ctresolation = $('#ctresolation').val()
		if (ctresolation == null || ctresolation == "") {
			alert("请输入量仪分度值！")
			return false
		}
		var ctnorms = $('#ctnorms').val()
		if (ctnorms == null || ctnorms == "") {
			alert("请输入量仪规格！")
			return false
		}
		var ctcheckprogram = $('#ctcheckprogram').val()
		if (ctcheckprogram == null || ctcheckprogram == "") {
			alert("请输入量仪校准程序！")
			return false
		}
		var ctcheckway = $('#ctcheckway').val()
		var ctcheckcycle = $('#ctcheckcycle').val()
		var ctmsa = $('#ctmsa').val()
		var ctoriginalnum = $('#ctoriginalnum').val()
		if (ctoriginalnum == null || ctoriginalnum == "") {
			alert("请输入量仪溯源编号！")
			return false
		}
		var ctassetnum = $('#ctassetnum').val()
		if (ctassetnum == null || ctassetnum == "") {
			alert("请输入量仪资产编号！")
			return false
		}
		var ctsize = $('#ctsize').val();
		if (ctsize == null || ctsize == "") {
			alert("请输入量仪尺寸描述！")
			return false
		}
		var ctchecktemperature = $('#ctchecktemperature').val()
		if (ctchecktemperature == null || ctchecktemperature == "") {
			alert("请输入量仪校验温度！")
			return false
		}
		var ctcheckhumidiry = $('#ctcheckhumidiry').val()
		if (ctcheckhumidiry == null || ctcheckhumidiry == "") {
			alert("请输入量仪校验湿度！")
			return false
		}
		var ctremark = $('#ctremark').val()
		ctremark = stripscript(ctremark);
		var ctfile = $('#ctfile')[0].files;
		console.log(ctfile)
		var formdata = new FormData();
		for (var i = 0; i < ctfile.length; i++) {
			formdata.append('checkingToolFiles',ctfile[i])
		};
		formdata.append('ctid',ctid)
		formdata.append('ctname',ctname)
		formdata.append('cttype',cttype)
		formdata.append('ctproducer',ctproducer)
		formdata.append('ctproductionnum',ctproductionnum)
		formdata.append('ctprocision',ctprocision)
		formdata.append('ctresolation',ctresolation)
		formdata.append('ctnorms',ctnorms)
		formdata.append('ctcheckprogram',ctcheckprogram)
		formdata.append('ctcheckway',ctcheckway)
		formdata.append('ctcheckcycle',ctcheckcycle)
		formdata.append('ctmsa',ctmsa)
		formdata.append('ctoriginalnum',ctoriginalnum)
		formdata.append('ctassetnum',ctassetnum)
		formdata.append('ctsize',ctsize)
		formdata.append('ctremark',ctremark)
		formdata.append('ctchecktemperature',ctchecktemperature)
		formdata.append('ctcheckhumidiry',ctcheckhumidiry)
		
		$.ajax({
			url:'./user/addCheckingToolInfo',
			type:'POST',
			cache:false,
			data:formdata,
			datatype:'json',
			contentType:false,
			processData:false,
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					alert("添加成功");
					myrefresh();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
	// 判断量仪编号是否重复
	$('#ctid').blur(function(){
		var ctid = $('#ctid').val();
		$.ajax({
			url:'./user/judgeCtid',
			type:'POST',
			cache:false,
			data:{'ctid':ctid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 0) {
					alert(data.msg);
				}
			}
		})
	})
})