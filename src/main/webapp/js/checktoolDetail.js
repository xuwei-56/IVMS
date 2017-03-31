$(document).ready(function(){
	var ctid = $.getUrlVar('ctid');
	if (ctid == null || ctid == "") {
		/*alert("无效查询,返回前页");
		window.opener = null; 
		window.open('', '_self'); 
		window.close() 
		return false*/
	}
	$('a.media').media({width:700, height:800});

	// 检具履历
	$.ajax({
		url:'./user/myCheckingToolsDetails',
		type:'POST',
		data:{'ctid':ctid},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				$('#ctid').text(data.data.ctid);
				$('#ctuseitem').text(data.data.ctuseitem)
				$('#ctcheckprogram').text(data.data.ctcheckprogram)
				$('#ctname').text(data.data.ctname)
				$('#ctuseline').text(data.data.ctuseline)
				$('#ctcheckway').text(data.data.ctcheckway)
				$('#ctproducer').text(data.data.ctproducer)
				$('#ctusestation').text(data.data.ctusestation)
				var cycle = getCTCycle(data.data.ctcheckcycle);
				$('#ctcheckcycle').text(cycle + "个月")
				$('#ctproductionnum').text(data.data.ctproductionnum)
				$('#ctuser').text(data.data.user)
				$('#ctmsa').text(data.data.ctmsa)
				$('#ctnorms').text(data.data.ctnorms)
				$('#ctusetime').text($.UnixToDate(data.data.ctusetime))
				$('#ctoriginalnum').text(data.data.ctoriginalnum)
				$('#ctprocision').text(data.data.ctprocision)
				$('#ctassetnum').text(data.data.ctassetnum)
				$('#ctsize').text(data.data.ctsize)
				$('#ctresolation').text(data.data.ctresolation)
				$('#ctcheckTH').text(data.data.ctchecktemperature +"℃/"+ data.data.ctcheckhumidiry)
				$('#ctstatus').text(data.data.ctstatus)
				$('#ctremark').text(data.data.ctremark)
				var CTRData = "<tr><th width='5%'>测量序号</th><th>送检人</th><th>送检日期</th><th>校验人员</th><th>校验日期</th><th>校验内容/技术规范</th><th>实测值</th><th>校验仪器/工具</th><th>测量结论</th><th>是否接受</th><th>状态</th></tr>";
				var temp;
				for (var i = data.data.checkingToolsRecord.length - 1; i >= 0; i--) {
					temp = data.data.checkingToolsRecord[i];
					CTRData += "<tr><td>"+temp.ctrid+"</td><td>"+temp.ctrmovecp+"</td><td>"+$.UnixToDate(temp.ctrmovetime)+"</td><td>"+temp.ctrcheckman+"</td><td>"+$.UnixToDate(temp.ctrchecktime)+"</td><td>"+temp.ctrcheckcontent+"</td><td>"+temp.ctrcheckvalue+"</td><td>"+temp.ctrchecktools+"</td><td>"+temp.ctrcheckresult+"</td><td>"+temp.ctracceptresult+"</td><td>"+temp.ctrremark+"</td></tr>";
				}
				$('#CTRTable').html(CTRData);
				var ctchecknexttime = "";
				if (data.data.ctstatus == 1 ) {
					if (data.data.checkingToolsRecord != null) {
						ctchecknexttime = data.data.checkingToolsRecord[0].ctrctrchecknexttime
					} else {
						ctchecknexttime = $.UnixToDate(data.data.ctusetime + cycle * 30 * 24 * 60 * 60 * 1000) // 这个时间是大概时间  
					}
				}
				
				$('#ctchecknexttime').text(ctchecknexttime);
				var fileUrl = "";
				for (var i = data.data.checkingToolsFile.length - 1; i >= 0; i--) {
					temp = data.data.checkingToolsFile[i]
					fileUrl += "<li><a href='./file/"+temp.ctfname+"' id='fileUrlA'>"+temp.ctfname+"</a></li>"
				}
				$('#CTFileList_ul').html(fileUrl)
			}else{
				alert(data.msg)
				return false;
			}
		}
	})

	// 检具每期检测数据
	/*$.ajax({
		url:'./user/get',
		type:'POST',
		data:{'':ctid},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				var CTRData = "<tr><th width='5%'>测量序号</th><th>送检人</th><th>送检日期</th><th>校验单位</th><th>校验人员</th><th>校验日期</th><th>校验内容/技术规范</th><th>实测值</th><th>校验仪器/工具</th><th>测量结论</th><th>是否接受</th><th>状态</th></tr>";
				for (var i = 0; i < data.data.length; i++) {
					CTRData += "<tr><td>"+data.data[i].ctrid+"</td><td>"+data.data[i].ctrmovep+"</td><td>"+data.data[i].ctrmovetime+"</td><td>"+data.data[i].ctrcheckman+"</td><td>"+data.data[i].ctrchecktime+"</td><td>"+data.data[i].ctrcheckcontent+"</td><td>"+data.data[i].ctrcheckvalue+"</td><td>"+data.data[i].ctrchecktools+"</td><td>"+data.data[i].ctrcheckresult+"</td><td>"+data.data[i].ctracceptresult+"</td><td>"+data.data[i].ctrremark+"</td></tr>";
				}
				$('#CTRTable').html(CTRData);
			}else{
				alert(data.msg)
				return false;
			}
		}
	})*/

	

	$('.top_rt_btn').mouseenter(function(){
		$('.CTFileList_pop_bg').fadeIn();
	})
	$('.mouseleave').mouseleave(function(){
		$('.CTFileList_pop_bg').fadeOut();
	})


})