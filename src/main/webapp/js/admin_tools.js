function getCTStatusButton(str){
	var status = "";
  switch(str){
    case 0:status = "<a href='#' class='inner_btn' id='confirmuser_btn'>登记领用人</a>";break;
    case 1:status = "已领用"; break;
    case 2:status = "维修"; break;
    case 3:status = "封存"; break;
    case 4:status = "报废"; break;
  }
  return status;
}
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
		data:{'requestPageNum':1,'CTUseItem':null,'CTStatus':null},
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
						data:{'requestPageNum':page,'CTUseItem':null,'CTStatus':null},
						datatype:'json',
						success:function(data){
							data = JSON.parse(data);
							if(data.code == 1){
								//getPage(data.data[0].count,1,pageSize);
								toolsCount = parseInt(data.msg);
								var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪规格</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:150px;'>操作</th></tr>";
								data.data.forEach(function(checktool){
									checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctusestation+"</td><td>"+getCTStatusButton(checktool.ctstatus)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
								})
				  				$('#cffinished').html(checkformdata);
							}
						}
					})
				}});
				var checktooldata = "<tr><th>量仪编号</th><th>量仪名称</th><th>量仪规格</th><th>效验周期</th><th>使用项目</th><th>检具状态</th><th style='width:150px;'>操作</th></tr>";
				data.data.forEach(function(checktool){
					checktooldata += "<tr><td>"+checktool.ctid+"</a></td><td>"+checktool.ctname+"</td><td>"+checktool.ctnorms+"</td><td>"+getCTCycle(checktool.ctcheckcycle)+"</td><td>"+checktool.ctusestation+"</td><td>"+getCTStatusButton(checktool.ctstatus)+"</td><td><a href='./checktoolDetail?ctid="+checktool.ctid+"' class='inner_btn' target='view_window'>查看详情</a></td></tr>";
				})
				$('#cttable').html(mychecktooldata);
			}else if (data.code == 0) {
				$('#cttable').html("<div>没有数据</div>");
			}else{
				alert("获取量检具失败！错误信息："+data.msg)
				return false;
			}
		}
	})
	// 确认认领人
	$('#cttable').delegate('confirmuser_btn','click',function(){
		var ctid = $(this).parent().parent().find("td:eq(0)").text();
		var ctname = $(this).parent().parent().find("td:eq(0)").text();
		$('#ctid_user').val(ctid);
		$('#ctname_user').val(ctname)
		$('#pop_bg_confirmuser').fadeIn();
	})
	// 提交认领人
	$('#tooluseconfirm_btn').click(function(){
		var ctid = $('#ctid_user').val();
		var ctname = $('#ctname_user').val()
		var ctuser = $('#ctuser').val()
		if (ctuser == null || ctuser == "") {
			alert("请输入领用人！")
			return false
		}
		$.ajax({
			url:'./',
			type:'POST',
			data:formdata,
			datatype:'json',
			contentType:false,
			processData:false,
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					alert("添加成功");
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
	// 关闭认领人弹框
	$(".falseBtn").click(function(){
    $("#pop_bg_confirmuser").fadeOut();
  });
	// 文件列表
	$('#ctfile').change(function(){
		var files = $('#ctfile')[0].files;
		var filesname = '';
		for (var i = files.length - 1; i >= 0; i--) {
			filesname += files[i].name + "；"
		}
		$('#ctfilename').text(filesname)
		console.log(filesname)
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
		var ctfile = $('#ctfile')[0];
		var formdata = new FormData();
		formdata.append('ctid',ctid)
		formdata.append('ctname',ctname)
		formdata.append('ctproducer',ctproducer)
		formdata.append('ctproductionnum',ctproductionnum)
		formdata.append('ctprocision',ctprocision)
		formdata.append('ctresolation',ctresolation)
		formdata.append('ctcheckprogram',ctcheckprogram)
		formdata.append('ctcheckway',ctcheckway)
		formdata.append('ctcheckcycle',ctcheckcycle)
		formdata.append('ctmsa',ctmsa)
		formdata.append('ctoriginalnum',ctoriginalnum)
		formdata.append('ctsize',ctsize)
		formdata.append('ctchecktemperature',ctchecktemperature)
		formdata.append('ctcheckhumidiry',ctcheckhumidiry)
		formdata.append('',ctfile)
		$.ajax({
			url:'./',
			type:'POST',
			data:formdata,
			datatype:'json',
			contentType:false,
			processData:false,
			success:function(data){
				data = JSON.parse(data);
				if (data.code == 1) {
					alert("添加成功");
				}else{
					alert(data.msg)
					return false;
				}
			}
		})
	})
})