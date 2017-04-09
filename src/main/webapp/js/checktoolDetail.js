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
				$('#ctuseitem').text(data.data.CTUseItem)
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
				$('#ctchecknexttime').text($.UnixToDate(data.data.CTRCheckNextTime));
				
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
				var CTRData = "<tr><th width='5%'>测量序号</th><th>送检人</th><th>送检日期</th><th>校验人员</th><th>校验日期</th><th>校验内容/技术规范</th><th>实测值</th><th>校验仪器/工具</th><th>测量结论</th><th>是否接受</th><th>是否同意</th><th>状态</th></tr>";
				for (var i = 0; i < data.data.length; i++) {
					CTRData += "<tr><td>"+data.data[i].CTRId+"</td><td>"+data.data[i].CTRMoveCP+"</td><td>"+$.UnixToDate(data.data[i].CTRMoveTime)+"</td><td>"+data.data[i].CTRCheckMan+"</td><td>"+$.UnixToDate(data.data[i].CTRCheckTIme)+"</td><td>"+data.data[i].CTRCheckContent+"</td><td>"+data.data[i].CTRCheckValue+"</td><td>"+data.data[i].CTRCheckTools+"</td><td>"+data.data[i].CTRCheckResult+"</td><td>"+getAccept(data.data[i].CTRAcceptResult)+"</td><td>"+getIsAgree(data.data[i].CTIsAgree)+"</td><td>"+getCTStatus(data.data[i].CTRRemark)+"</td></tr>";
				}
				$('#CTRTable').html(CTRData);
			}else{
				alert(data.msg)
				return false;
			}
		}
	})
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
					filedata += "<li><a href='./checkingtoolfile/"+file.ctfname+"' id='fileUrlA'</a>"+filename+"</li>"
				})
				$('#CTFileList_ul').html(filedata);
			}else if(data.code == 0){
				$('#CTFileList_ul').html(data.msg);
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