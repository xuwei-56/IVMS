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
					checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDate(checkform.cftime)+"</td><td>"+checkform.claid+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
  				})
  				$('#cfnormal').html(checkformdata);
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
					checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDate(checkform.cftime)+"</td><td>"+checkform.claid+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
  				})
  				$('#cfspecial').html(checkformdata);
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
					checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDate(checkform.cftime)+"</td><td>"+checkform.claid+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='print'>打印</a><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
  				})
  				$('#cfzero').html(checkformdata);
			}
		}
	})
}
function gethistoryCheckingForm(){
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
				$('#pageTool').html("");
				var pageSize = 20;
				$('#pageTool').Paging({pagesize:pageSize,count:count,callback:function(page,size,count){
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
}
function getClassify(){
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
								var checkformdata = "<tr><th>检测单号</th><th>送检日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
								data.data.forEach(function(checkform){
									checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
				  				})
				  				$('#cffinished').html(checkformdata);
							}
						}
					})
				}});
				var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪规格</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:150px;'>操作</th></tr>";
				data.data.forEach(function(checktool){
					checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctusestation+"</td><td>"+getCTStatus(checktool.ctstatus)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
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
function prn_print(strNumber,strName,strTime){
	CreatePage(strNumber,strName,strTime);
	LODOP.PRINT();
}
function prn_preview(strNumber,strName,strTime) {	
	CreatePage(strNumber,strName,strTime);	
	LODOP.PREVIEW();	
};
function prn_setup(strNumber,strName,strTime) {		
	CreatePage(strNumber,strName,strTime);
	LODOP.PRINT_setup();	
};
function prn_design(strNumber,strName,strTime) {		
	CreatePage(strNumber,strName,strTime);
	LODOP.PRINT_design(); 	
};	
function CreatePage(strNumber,strName,strTime){
	LODOP.PRINT_INIT("打印插件_标签");
	LODOP.SET_PRINT_PAGESIZE(1,400,300,"");    // 单位是0.1毫米
	LODOP.ADD_PRINT_TEXT(30,51,90,24,strNumber);    // 单位是px
	LODOP.SET_PRINT_STYLEA(1,"FontName","C39HrP24DlTt");
	LODOP.SET_PRINT_STYLEA(1,"FontSize",16);
	LODOP.SET_PRINT_STYLEA(1,"Alignment",2);
	LODOP.ADD_PRINT_TEXT(65,48,94,24,strName);
	LODOP.SET_PRINT_STYLEA(2,"FontSize",16);
	LODOP.SET_PRINT_STYLEA(2,"Alignment",2);
	LODOP.ADD_PRINT_TEXT(95,13,166,24,strTime);
	LODOP.SET_PRINT_STYLEA(3,"FontSize",16);
	LODOP.SET_PRINT_STYLEA(3,"Alignment",2);
};
$(document).ready(function(){
	//检测浏览器，至于IE能支持打印
	$(function(){
		var ie = 0;
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? ie = s[1] : 0;
    (s = ua.match(/msie ([\d.]+)/)) ? ie = s[1] : 0;
    if (ie > 9) {
    	/*CheckLodop();*/
    }/*else{
    	alert("浏览器不支持打印，请使用IE9以上的浏览器！")
    }*/
	})
	//tab
	$(".admin_tab li a").click(function(){
		var liindex = $(".admin_tab li a").index(this);
		$(this).addClass("active").parent().siblings().find("a").removeClass("active");
		$(".admin_tab_cont").eq(liindex).fadeIn(150).siblings(".admin_tab_cont").hide();
		/*console.log(liindex)*/
		if (liindex == 1) {
			//得到正常过程送检送检单
			getnormalCheckingForm();
		}
		if (liindex == 2) {
			//得到其他分类的送检单
			getothersCheckingForm();
		}
	});
	//得到未打印的送检单
	getnotPrintCheckingForm();
	
	//得到完成的送检信息
	gethistoryCheckingForm();
	// 获取我的量检具
	getcheckingToolsInfo(1,null,null);

	// LODOP打印
	$('#cfzero').delegate('#print','click',function(){
		var cfid = $(this).parent().parent().find('td:first').text();
		cfid = cfid.substring(9);
		var moveP = $(this).parent().parent().find('td:eq(3)').text();
		var time = $(this).parent().parent().find('td:eq(1)').text();
		prn_preview(cfid,moveP,time); //打印预览
		//prn_print(cfid,moveP,time);   // 直接打印

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
					if (data.data.cfurgentstatus == 1 ) {
						$('#urgentStatus').val("加急")
						$('#urgentfile').val(data.data.urgentFile.ufname)
					}else{
						$('#urgentStatus').val("普通")
					}
					/*$('#urgentStatus').val(data.data.cfurgentstatus);*/
					$('#cCId').val(data.data.checkingClassify.ccname)
					$('#time').val($.UnixToDate(data.data.cftime));
					$('#cfstatus').val(getStatus(data.data.cfstatus))
					$('#lId').val(data.data.line.lname);
					$('#cId').val(data.data.cell.cname);
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
					$('#cfreportfile').text("<a href='./"+data.data.cfreportfile+"'>"+data.data.cfreportfile+"</a>"); 
					$('#pop_bg_user').fadeIn();
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
})
