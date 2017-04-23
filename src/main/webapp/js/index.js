function getnormalCheckingForm(){
	//得到正常过程送检送检单
	$.ajax({
		url:'./normalCheckingForm',
		type:'POST',
		data:{},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if(data.code == 1){
				var checkformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
				data.data.forEach(function(checkform){
					checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='print'>打印</a><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
				})
				$('#wait_num').html("当前正常物料检测有 <p>"+ data.data.length +"</p> 人排队！")
        $('#cfnormal').html(""+checkformdata);
			}if (data.code == 0) {
				$('#cfnormal').html("没有未打印的送检单！");
			}
		}
	})
}
function getothersCheckingForm(){
	//得到其他分类的送检单
	$.ajax({
		url:'./othersCheckingForm',
		type:'POST',
		data:{},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if(data.code == 1){
				var checkformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
				data.data.forEach(function(checkform){
					checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='print'>打印</a><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
				})
				$('#wait_num').html("当前其他分类检测有 <p>"+ data.data.length +"</p> 人排队！")
        $('#cfspecial').html(""+checkformdata);
			}if (data.code == 0) {
				$('#cfspecial').html("没有未打印的送检单！");
			}
		}
	})
}
function getnotPrintCheckingForm(){
	//得到未打印的送检单
	$.ajax({
		url:'./notPrintCheckingForm',
		type:'POST',
		data:{},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if(data.code == 1){
				var checkformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
				data.data.forEach(function(checkform){
					checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='print'>打印</a><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
  				})
  				$('#cfzero').html(""+checkformdata);
			}if (data.code == 0) {
				$('#cfzero').html("没有未打印的送检单！");
			}
		}
	})
}
function gethistoryCheckingForm(){

}
function getClassify1(){
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
				$('#byclaId').html(Classify)
			}else{
				alert("获取送检类型失败！错误信息：" + data.msg)
			}
		}
	})
}
function getProject(){
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
				$('#bypId').html(Projects);
			}else{
				alert("获取送检项目失败！错误信息：" + data.msg)
				return false;
			}
		}
	})
}
function getcheckingToolsInfo(requestPageNum,CTUseItem,CTStatus){
	// 获取量检具
	var count;
	$.ajax({
		url:'./checkingToolsInfo',
		type:'POST',
		data:{'requestPageNum':requestPageNum,'CTUseItem':CTUseItem,'CTStatus':CTStatus},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				count = parseInt(data.msg);
				$('#pageTool').html("");
				var pageSize = 20;
				$('#pageTool').Paging({pagesize:pageSize,count:count,callback:function(page,size,count){
					$.ajax({
						url:'./checkingToolsInfo',
						type:'POST',
						data:{'requestPageNum':page,'CTUseItem':CTUseItem,'CTStatus':CTStatus},
						datatype:'json',
						success:function(data){
							data = JSON.parse(data);
							if(data.code == 1){
								//getPage(data.data[0].count,1,pageSize);
								count = parseInt(data.msg);
								data.data.forEach(function(checktool){
									checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
								})
								$('#checktools').html(mychecktooldata);
							}
						}
					})
				}});
				var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪规格</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:150px;'>操作</th></tr>";
				data.data.forEach(function(checktool){
					checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
				})
				$('#checktools').html(mychecktooldata);
			}else if (data.code == 0) {
				$('#checktools').html("<div>没有数据</div>");
			}else{
				alert("获取量检具失败！错误信息："+data.msg)
				return false;
			}
		}
	})
}
// LODOP 打印插件   
function prn_print(strNumber,strName,strTime,strCName){
	CreatePage(strNumber,strName,strTime,strCName);
	LODOP.PRINT();
}
function prn_preview(strNumber,strName,strTime,strCName) {	
	CreatePage(strNumber,strName,strTime,strCName);	
	LODOP.PREVIEW();	
};
function prn_setup(strNumber,strName,strTime,strCName) {		
	CreatePage(strNumber,strName,strTime,strCName);
	LODOP.PRINT_setup();	
};
function prn_design(strNumber,strName,strTime,strCName) {		
	CreatePage(strNumber,strName,strTime,strCName);
	LODOP.PRINT_DESIGN(); 	
};	

function CreatePage(strNumber,strName,strTime,strCName) {
	LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM')); 
	LODOP.PRINT_INIT("打印测试")
	LODOP.SET_PRINT_PAGESIZE(0,"40mm","30mm","");
	LODOP.ADD_PRINT_TEXT("4mm","5mm","30mm","7mm",strNumber);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",18);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.ADD_PRINT_TEXT("12mm","5mm","30mm","5mm",strCName);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.ADD_PRINT_TEXT("17.5mm","5mm","30mm","5mm",strName);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.ADD_PRINT_TEXT("23mm","2mm","36mm","4mm",strTime);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
};
$(document).ready(function(){
	
	$(".admin_tab li a").click(function(){
		var liindex = $(".admin_tab li a").index(this);
		$(this).addClass("active").parent().siblings().find("a").removeClass("active");
		$(".admin_tab_cont").eq(liindex).fadeIn(150).siblings(".admin_tab_cont").hide();
		/*console.log(liindex)*/
		if (liindex == 0) {
			//得到未打印的送检单
			getnotPrintCheckingForm();
		}
		if (liindex == 1) {
			//得到正常过程送检送检单
			getnormalCheckingForm();
		}
		if (liindex == 2) {
			//得到其他分类的送检单
			getothersCheckingForm();
		}
		if (liindex == 1 || liindex == 2) {
			$('#wait_num').show();
		}
		if (liindex == 0 || liindex > 2) {
			$('#wait_num').hide();
		}
	});
	//得到未打印的送检单
	getnotPrintCheckingForm();
	
	/*//得到完成的送检信息
	gethistoryCheckingForm();*/
	// 获取历史检测报告
	var count;
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
				//送检类型
				getClassify1();
				//获取项目
				getProject();
			}else if (data.code == 0) {
				$('#cffinished').html("<div>没有数据</div>");
			}
			else{
				alert("请先登录")
				location.href = "./login";
			}
			$(".loading_area").fadeOut();
		}
	})
	// 获取量检具
	var toolsCount;
	$.ajax({
		url:'./checkingToolsInfo',
		type:'POST',
		data:{'requestPageNum':1,'CTUseItem':0,'CTStatus':null},
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
						data:{'requestPageNum':page,'CTUseItem':0,'CTStatus':null},
						datatype:'json',
						success:function(data){
							data = JSON.parse(data);
							if(data.code == 1){
								//getPage(data.data[0].count,1,pageSize);
								toolsCount = parseInt(data.msg);
								var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪规格</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:150px;'>操作</th></tr>";
								data.data.forEach(function(checktool){
									checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
								})
				  			$('#checktools').html(checkformdata);
							}
						}
					})
				}});
				var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪规格</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:150px;'>操作</th></tr>";
				data.data.forEach(function(checktool){
					checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
				})
				$('#checktools').html(checktooldata);
			}else if (data.code == 0) {
				$('#checktools').html("<div>没有数据</div>");
			}else{
				alert("获取量检具失败！错误信息："+data.msg)
				return false;
			}
		}
	})

	// LODOP打印
	$('#cfnormal,#cfspecial,#cfzero').delegate('#print','click',function(){
		var cfid = $(this).parent().parent().find('td:first').text();
		var cfidtemp = cfid.substring(8);
		var moveP = $(this).parent().parent().find('td:eq(3)').text();
		var time = $(this).parent().parent().find('td:eq(1)').text();
		var CName = $(this).parent().parent().find('td:eq(5)').text();
		//prn_preview(cfidtemp,moveP,time,CName); // 打印预览
		prn_print(cfidtemp,moveP,time,CName);   // 直接打印
		//prn_design(cfidtemp,moveP,time,CName)
	})
	$('#cfzero').delegate('#print','click',function(){
		var cfid = $(this).parent().parent().find('td:first').text();
		$.ajax({
			url:'./updateCfstatus',
			type:'POST',
			data:{'cfid':cfid},
			datatype:'json',
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					getnotPrintCheckingForm();
				}else{
					return false;
				}
			}
		})
	})

	
	// 获取检测单详情
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
	// 关闭弹框
  $("#falseBtn").click(function(){
    $("#pop_bg_user").fadeOut();
  });

  //查询送检单
  $('#button_searchCheckForm').click(function(){
		var cfid = $('#bycfid').val();
		var claid = $('#byclaId').val();
		var pid = $('#bypId').val()
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
				$(".loading_area").fadeIn();
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
								}else if (data.code == 0) {
									$('#cffinished').html("<div>没有数据</div>");
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
					$('#cffinished').html("<div>没有数据</div>");
				}
				else{
					alert("请先登录")
					location.href = "./login";
				}
				$(".loading_area").fadeOut();
			}
		})
	})
	
	// 查询检具
	$('#button_searchCheckTools').click(function(){
		var CTUseItem = $('#byCTUseItem').val();
		var CTStatus = $('#byCTStatus').val();
		if (CTStatus == null && CTUseItem == null && CTUseItem == "") {
			return false;
		}
		if (CTUseItem == "" || CTUseItem == null ) {
			CTUseItem = 0;
		};
		$.ajax({
			url:'./checkingToolsInfo',
			type:'POST',
			data:{'requestPageNum':1,'CTUseItem':CTUseItem,'CTStatus':CTStatus},
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
							data:{'requestPageNum':page,'CTUseItem':CTUseItem,'CTStatus':CTStatus},
							datatype:'json',
							success:function(data){
								data = JSON.parse(data);
								if(data.code == 1){
									//getPage(data.data[0].count,1,pageSize);
									toolsCount = parseInt(data.msg);
									var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪规格</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:150px;'>操作</th></tr>";
									data.data.forEach(function(checktool){
										checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
									})
					  				$('#checktools').html(checkformdata);
								}
							}
						})
					}});
					var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪规格</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:150px;'>操作</th></tr>";
					data.data.forEach(function(checktool){
						checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctuseitem+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
					})
					$('#checktools').html(checktooldata);
				}else if (data.code == 0) {
					$('#checktools').html("<div>没有数据</div>");
				}else{
					alert("获取量检具失败！错误信息："+data.msg)
					return false;
				}
			}
		})
	})
})
