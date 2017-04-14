$(document).ready(function(){
	$(".admin_tab li a").click(function(){
	  var liindex = $(".admin_tab li a").index(this);
	  $(this).addClass("active").parent().siblings().find("a").removeClass("active");
	  $(".admin_tab_cont").eq(liindex).fadeIn(150).siblings(".admin_tab_cont").hide();
	 });
	//存储送检单信息
	var checkformdata;
	var pageSize = 20;
	var count;
	//得到当前登录员工送检信息
	$.ajax({
		url:'./user/myCheckingForm',
		type:'POST',
		data:{'requestPageNum':1,"claid":null,'pid':null,'cfid':null},
		datatype:'json',
		beforeSend:function(){
			$(".loading_area").fadeIn();
		},
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {			
				// 获取
				count = parseInt(data.msg);
				checkformdata = data.data;
				$('#pageTool').html("");
				$('#pageTool').Paging({pagesize:pageSize,count:count,callback:function(page,size,count){
					$.ajax({
						url:'./user/myCheckingForm',
						type:'POST',
						data:{'requestPageNum':page,"claid":null,'pid':null,'cfid':null},
						datatype:'json',
						success:function(data){
							data = JSON.parse(data);
							if(data.code == 1){
								//getPage(data.data[0].count,1,pageSize);
								count = parseInt(data.msg);
								checkformdata = data.data;
								var mycheckformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
								data.data.forEach(function(checkform){
									mycheckformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td>";
									if (checkform.cfstatus == 0) {
										mycheckformdata += "<td><a href='#' class='inner_btn' id='checkformdetail'>详情</a><a href='#' class='inner_btn' id='deleteform'>删除</a></td></tr>";
									}else{
										mycheckformdata += "<td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
									}
				  				})
				  				$('#mycheckform').html(mycheckformdata);
							}
						}
					})
				}});
				var mycheckformdata = "<tr><th>检测单号</th><th>检测日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
				data.data.forEach(function(checkform){
					mycheckformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td>";
  					if (checkform.cfstatus == 0) {
						mycheckformdata += "<td><a href='#' class='inner_btn' id='checkformdetail'>详情</a><a href='#' class='inner_btn' id='deleteform'>删除</a></td></tr>";
					}else{
						mycheckformdata += "<td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
					}
  				})
  				$('#mycheckform').html(mycheckformdata);
  				$(".loading_area").fadeOut();

  				// 获取送检类型
				$.ajax({
					url:'./user/getClassify',
					type:'POST',
					data:{},
					datatype:'json',
					success:function(data){
						data = JSON.parse(data);
						if (data.code == 1) {
							var Classify = "<option value=''>所有送检</option>";
							for (var i = 0; i < data.data.length; i++) {
								Classify += "<option value="+data.data[i].claid+">"+data.data[i].cname+"</option>"
							}
							$('#byclaId').html(Classify)
						}else{
							alert("获取送检类型失败！错误信息：" + data.msg)
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
							var Projects = "<option value=''>所有项目</option>";
							for (var i = 0; i < data.data.length; i++) {
								Projects += "<option value="+data.data[i].pid+">"+data.data[i].pname+"</option>";
							}
							$('#bypId').html(Projects);
						}else{
							alert("获取送检项目失败！错误信息：" + data.msg)
							return false;
						}
					}
				})
				// 获取我的量检具
				$.ajax({
					url:'./user/myCheckingTools',
					type:'POST',
					data:{},
					datatype:'json',
					success:function(data){
						data = JSON.parse(data);
						if (data.code == 1) {
							var mychecktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪规格</th><th>效验周期</th><th>使用项目</th><th>领用日期</th><th style='width:250px;'>操作</th></tr>";
							data.data.forEach(function(checktool){
								if (checktool.ctstatus == 6) {
									mychecktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctusestation+"</td><td>"+$.UnixToDate(checktool.ctusetime)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a><a class='inner_btn' id='confirm'>确认检测</a></td></tr>";
								}else{
									mychecktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctusestation+"</td><td>"+$.UnixToDate(checktool.ctusetime)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
								}
								
							})
							$('#mychecktools').html(mychecktooldata);
		  				$(".loading_area").fadeOut();
						}else if (data.code == 0) {
							$('#mychecktools').html("<div>没有数据</div>");
						}else{
							alert("获取我的量检具失败！错误信息："+data.msg)
							return false;
						}
					}
				})
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
	//	确认检测结果
	$('#mychecktools').delegate('#confirm','click',function(){
		var ctid = $(this).parent().parent().find("td:eq(0)").text();
		var ctname = $(this).parent().parent().find("td:eq(1)").text();
		$('#ctid_confirm').val(ctid)
		$('#ctname_confirm').val(ctname)
		$('#pop_bg_confirm').fadeIn()
	})
	// 提交确认结果
	$('#toolconfirm_btn').click(function(){
		var ctid = $('#ctid_confirm').val();
		var ctisagree = $('#ctisagree').val()
		var ctracceptresult = $('#ctracceptresult').val();
		$.ajax({
			url:'./user/updateAgreeAndAccept',
			type:'POST',
			data:{'ctid':ctid,'ctracceptresult':ctracceptresult,'ctisagree':ctisagree},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					window.location.reload();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
	//根据送检单模糊查询到自己的送检单
	$('#button_searchByCfid').click(function(){
		var cfid = $('#bycfid').val();
		var claid = $('#byclaId').val();
		var pid = $('#bypId').val()
		if (cfid == null && cfid == "" && claid == null && claid == "" && pid == null && pid == "" ) {
			return false;
		}
		if (cfid == "") {
			cfid = null;
		};
		console.log(pid);
		$('#pageTool').html("");
		// 根据送检类型查找送检单
		$.ajax({
			url:'./user/myCheckingForm',
			type:'POST',
			data:{'requestPageNum':1,"claid":claid,'pid':pid,'cfid':cfid},
			datatype:'json',
			beforeSend:function(){
				$(".loading_area").fadeIn();
			},
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {			
					// 获取
					count = data.msg;
					checkformdata = data.data;
					$('#pageTool').Paging({pagesize:pageSize,count:count,callback:function(page,size,count){
						$.ajax({
							url:'./user/myCheckingForm',
							type:'POST',
							data:{'requestPageNum':page,"claid":null,'pid':null,'cfid':null},
							datatype:'json',
							success:function(data){
								data = JSON.parse(data);
								if(data.code == 1){
									//getPage(data.data[0].count,1,pageSize);
									count = data.msg;
									checkformdata = data.data;
									var mycheckformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
									data.data.forEach(function(checkform){
										mycheckformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td>";
										if (checkform.cfstatus == 0) {
											mycheckformdata += "<td><a href='#' class='inner_btn' id='checkformdetail'>详情</a><a href='#' class='inner_btn' id='deleteform'>删除</a></td></tr>";
										}else{
											mycheckformdata += "<td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
										}
					  				})
					  				$('#mycheckform').html(mycheckformdata);
								}
							}
						})
					}});
					var mycheckformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
					data.data.forEach(function(checkform){
						mycheckformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td>";
	  					if (checkform.cfstatus == 0) {
							mycheckformdata += "<td><a href='#' class='inner_btn' id='checkformdetail'>详情</a><a href='#' class='inner_btn' id='deleteform'>删除</a></td></tr>";
						}else{
							mycheckformdata += "<td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
						}
	  				})
	  				$('#mycheckform').html(mycheckformdata);
	  				
				}else if (data.code == 0) {
					$('#mycheckform').html("<div>没有数据</div>");
				}
				else{
					alert(data.msg)
					return false;
				}
				$(".loading_area").fadeOut();
			}
		})
	})
	// 获取检测单详情
	$('#mycheckform').delegate('#checkformdetail','click',function(){
		var cfid = $(this).parent().parent().find('td:first').text();
		$.ajax({
			url:'./user/myCheckingFormDetails',
			type:'POST',
			data:{'cfid':cfid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					$('#moveP').val(data.data.cfmovep)
					$('#phoneNum').val(data.data.cfphonenum);
					$('#mail').val(data.data.cfemail);
					$('#claId').val(data.data.classify.cname); 
					var notifymail = "";
					for (var i = 0; i < data.data.notifyPersonnelEmail.length; i++) {
						notifymail += data.data.notifyPersonnelEmail[i].npenotifyemail + ";";
					}
					$('#notifymail').val(notifymail) ;
					if (data.data.cfurgentstatus == 2 ) {
						$('#urgentStatus').val("加急")
						if (data.data.urgentFile != null ) {
							var uffilename = data.data.urgentFile.ufname.substr(data.data.urgentFile.ufname.lastIndexOf('_')+1)//获取最后一个下滑线之后的字符串
							$('#urgentfile').html("<a href='./urgentfile/"+data.data.urgentFile.ufname+"' download='"+uffilename+"'>"+uffilename+"</a>")
						};
					}else{
						$('#urgentStatus').val("普通")
					}
					/*$('#urgentStatus').val(data.data.cfurgentstatus);*/
					$('#cCId').val(data.data.checkingClassify.ccname)
					$('#time').val($.UnixToDate(data.data.cftime));
					$('#cfstatus').val(getStatus(data.data.cfstatus))
					var  lname =  "默认";
					if (data.data.line != null) {
						lname = data.data.line.lname
					};
					$('#lId').val(lname);
					var cname = "默认"
					if (data.data.cell != null) {
						cname = data.data.cell.cname;
					};
					$('#cId').val(cname);
					$('#pId').val(data.data.project.pname);
					$('#componentId').val(data.data.cfcomponentid);
					$('#componentName').val(data.data.cfcomponentname);
					$('#componentNum').val(data.data.cfcomponentnum);
					$('#checkNum').val(data.data.cfchecknum);
					if (data.data.wid != null) {
						$('#wId').val(data.data.wid)
					} else{
						$('#wId').val("默认");
					};
					$('#checkNum').val(data.data.cfchecknum)
					$('#remark').val(data.data.cfremark);
					if (data.data.claid == 2) {
						$('#cfreply').val(data.data.cfreply)
						$('#cfreplyreport').val(data.data.cfreplyreport)
					}
					if (data.data.cfreportfile != 0) {
						var filename = data.data.cfreportfile.substr(data.data.cfreportfile.lastIndexOf('_')+1)//获取最后一个下滑线之后的字符串
						$('#cfreportfile').html("<a href='./cfreportfile/"+data.data.cfreportfile+"' download='"+filename+"'>"+filename+"</a>"); 
					};
					$('#pop_bg_user').fadeIn();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})

	//删除送检单
	$('#mycheckform').delegate('#deleteform','click',function(){
		var cfid = $(this).parent().parent().find('td:first').text(); 
		if (window.confirm("你确定删除吗？")) {
			$.ajax({
				url:'./user/deleteMyCheckingForm',
				type:'POST',
				data:{'cfid':cfid},
				datatype:'json',
				success:function(data){
					data = JSON.parse(data);
					if (data.code == 1) {
						window.location.reload();
					}else{
						alert(data.msg)
						return false;
					}
				}
			})
		}else{
			return false;
		}
	})

	// 关闭弹框
    $(".falseBtn").click(function(){
      $(".pop_bg").fadeOut();
    });
	
	/*//回车提交事件
	$("body").keydown(function() {
	    if (event.keyCode == "13") {//keyCode=13是回车键
	        $('#button_searchByCfid').click();
	    }
	});*/
	//--------回车提交事件完毕---------------------//
})