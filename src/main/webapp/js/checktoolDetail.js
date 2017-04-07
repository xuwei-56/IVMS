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
				$('#ctid').text(data.data.CTId);
				$('#ctuseitem').text(data.data.CTUseitem)
				$('#ctcheckprogram').text(data.data.CTCheckProgram)
				$('#ctname').text(data.data.CTName)
				$('#ctuseline').text(data.data.CTUseLine)
				$('#ctcheckway').text(data.data.CTCheckWay)
				$('#ctproducer').text(data.data.CTProducer)
				$('#ctusestation').text(data.data.CTUseStation)
				var cycle = getCTCycle(data.data.CTCheckCycle);
				$('#ctcheckcycle').text(cycle + "个月")
				$('#ctproductionnum').text(data.data.CTProductionNum)
				$('#ctuser').text(data.data.CTReceiver)
				$('#ctmsa').text(data.data.CTMSA)
				$('#ctnorms').text(data.data.CTNorms)
				$('#ctusetime').text($.UnixToDate(data.data.CTUseTime))
				$('#ctoriginalnum').text(data.data.CTOriginalNum)
				$('#ctprocision').text(data.data.CTProcision)
				$('#ctassetnum').text(data.data.CTAssetNum)
				$('#ctsize').text(data.data.CTSize)
				$('#ctresolation').text(data.data.CTResolation)
				$('#ctcheckTH').text(data.data.CTCheckTemperature +"℃/"+ data.data.CTCheckHumidiry)
				$('#ctstatus').text(data.data.CTStatus)
				$('#ctremark').text(data.data.CTRemark)
				$('#ctchecknexttime').text(CTRCheckNextTime);
				/*var CTRData = "<tr><th width='5%'>测量序号</th><th>送检人</th><th>送检日期</th><th>校验人员</th><th>校验日期</th><th>校验内容/技术规范</th><th>实测值</th><th>校验仪器/工具</th><th>测量结论</th><th>是否接受</th><th>状态</th></tr>";
				var temp;
				for (var i = data.data.checkingToolsRecord.length - 1; i >= 0; i--) {
					temp = data.data.checkingToolsRecord[i];
					CTRData += "<tr><td>"+temp.ctrid+"</td><td>"+temp.ctrmovecp+"</td><td>"+$.UnixToDate(temp.ctrmovetime)+"</td><td>"+temp.ctrcheckman+"</td><td>"+$.UnixToDate(temp.ctrchecktime)+"</td><td>"+temp.ctrcheckcontent+"</td><td>"+temp.ctrcheckvalue+"</td><td>"+temp.ctrchecktools+"</td><td>"+temp.ctrcheckresult+"</td><td>"+temp.ctracceptresult+"</td><td>"+temp.ctrremark+"</td></tr>";
				}
				$('#CTRTable').html(CTRData);*/
				var ctchecknexttime = "";
				if (data.data.ctstatus == 1 ) {
					if (data.data.checkingToolsRecord != null) {
						ctchecknexttime = data.data.checkingToolsRecord[0].ctrctrchecknexttime
					} else {
						ctchecknexttime = $.UnixToDate(data.data.ctusetime + cycle * 30 * 24 * 60 * 60 * 1000) // 这个时间是大概时间  
					}
				}
				
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
	$.ajax({
		url:'./user/myCheckingToolRecords',
		type:'POST',
		data:{'ctid':ctid},
		datatype:'json',
		success:function(data){
			data = JSON.parse(data);
			if (data.code == 1) {
				var CTRData = "<tr><th width='5%'>测量序号</th><th>送检人</th><th>送检日期</th><th>校验单位</th><th>校验人员</th><th>校验日期</th><th>校验内容/技术规范</th><th>实测值</th><th>校验仪器/工具</th><th>测量结论</th><th>是否接受</th><th>是否同意</th><th>状态</th></tr>";
				for (var i = 0; i < data.data.length; i++) {
					CTRData += "<tr><td>"+data.data[i].CTRId+"</td><td>"+data.data[i].CTRMoveCP+"</td><td>"+data.data[i].CTRMoveTime+"</td><td>"+data.data[i].CTRCheckMan+"</td><td>"+data.data[i].CTRCheckTime+"</td><td>"+data.data[i].CTRCheckContent+"</td><td>"+data.data[i].CTRCheckValue+"</td><td>"+data.data[i].CTRCheckTools+"</td><td>"+data.data[i].CTRCheckResult+"</td><td>"+data.data[i].CTRAcceptResult+"</td><td>"+data.data[i].CTRIsAgree+"</td><td>"+data.data[i].CTRRemark+"</td></tr>";
				}
				$('#CTRTable').html(CTRData);
			}else{
				alert(data.msg)
				return false;
			}
		}
	})

	

	$('.top_rt_btn').mouseenter(function(){
		$('.CTFileList_pop_bg').fadeIn();
	})
	$('.mouseleave').mouseleave(function(){
		$('.CTFileList_pop_bg').fadeOut();
	})


})