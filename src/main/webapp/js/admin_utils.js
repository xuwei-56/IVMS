function getClassifyAdd(){
	// 获取送检类型
  $.ajax({
    url:'./user/getClassify',
    type:'POST',
    data:{},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var Classify = "<tr><th>ID</th><th>送检类型</th><th>操作</th></tr>";
        for (var i = 0; i < data.data.length; i++) {
          Classify += "<tr><td>"+data.data[i].claid+"</td><td>"+data.data[i].cname+"</td><td><a href='#' class='inner_btn' id='deleteClassify'>删除</a></td></tr>"
        }
        $('#classify_table').html(Classify)
      }else{
        alert("获取送检类型失败！错误信息：" + data.msg)
      }
    }
  })
}
function getCheckingClassifyAdd(claid){
	// 获取所有送检项目的检测项目
	$.ajax({
    url:'./user/getCheckingClassify',
    type:'POST',
    data:{'ClassifyId':claid},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var CheckingClassify = "<tr><th>ID</th><th>送检类型ID</th><th>检测类型</th><th>操作</th></tr>";
        for (var i = 0; i < data.data.length; i++) {
          CheckingClassify += "<tr><td>"+data.data[i].ccid+"</td><td>"+ claid +"</td><td>"+data.data[i].ccname+"</td><td><a href='#' class='inner_btn' id='deleteCheckingClassfy'>删除</a></td></tr>";
        }
        $('#checking_classify_table').html(CheckingClassify);
      }else{
        alert(data.msg + " 1")
        return false;
      }
    }
  })
}
function getWarehouseAdd(claid){
	// 得到库位
	$.ajax({
    url:'./user/getWareHouse',
    type:'POST',
    data:{'ClassifyId':claid},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var warehouse = "<tr><th>ID</th><th>送检类型ID</th><th>库位</th><th>操作</th></tr>";
        for (var i = 0; i < data.data.length; i++) {
          warehouse += "<tr><td>"+data.data[i].wid+"</td><td>"+ clid +"</td><td>"+data.data[i].wid+"</td><td><a href='#' class='inner_btn' id='deleteWarehouse'>删除</a></td></tr>";
        }
        $('#warehouse_table').html(warehouse);
      }else{
        if (data.code < 0) {
          alert(data.msg)
        }
        return false;
      }
    }
  })
}
function getLineAdd(){
	// 获取产线
  $.ajax({
    url:'./user/getLines',
    type:'POST',
    data:{},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var Lines = "<tr><th>ID</th><th>产线</th><th>操作</th></tr>";
        for (var i = 0; i < data.data.length; i++) {
          Lines += "<tr><td>"+data.data[i].lid+"</td><td>"+data.data[i].lname+"</td><td><a href='#' class='inner_btn' id='deleteLine'>删除</a></td></tr>"
        }
        $('#line_table').html(Lines)
      }else{
        alert("获取产线失败！错误信息：" + data.msg)
        return false;
      }
    }
  })
}
function getCellAdd(lid){
	// 获得单元
	$.ajax({
    url:'./user/getCellNames',
    type:'POST',
    data:{'LineId':lid},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var CellNames = "<tr><th>ID</th><th>产线ID</th><th>单元</th><th>操作</th></tr>";
        for (var i = 0; i < data.data.length; i++) {
          CellNames += "<tr><td>"+data.data[i].cid+"</td><td>"+lid+"</td><td>"+data.data[i].cname+"</td><td><a href='#' class='inner_btn' id='deleteCell'>删除</a></td></tr>";
        }
        $('#cell_table').html(CellNames);
      }else{
        alert(data.msg)
        return false;
      }
    }
  })
}
function getProjectAdd(){
	// 获取项目 
  $.ajax({
    url:'./user/getProjects',
    type:'POST',
    data:{},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var Projects = "<tr><th>ID</th><th>项目</th><th>操作</th></tr>";
        for (var i = 0; i < data.data.length; i++) {
          Projects += "<tr><td>"+data.data[i].pid+"</td><td>"+data.data[i].pname+"</td><td><a href='#' class='inner_btn' id='deleteProject'>删除</a></td></tr>";
        }
        $('#project_table').html(Projects);
      }else{
        alert("获取送检项目失败！错误信息：" + data.msg)
        return false;
      }
    }
  })
}
window.onload =  getClassifyAdd();
$(document).ready(function(){
	$(".utils_ul .utils_h").click(function(){
		var liindex = $(this).parent().parent().index();
		$(this).find("h2").addClass("active")
		$(this).parent().parent().siblings().find(".utils_h h2").removeClass("active");
		$(".utils_ul li").eq(liindex).find(".utils_div").fadeIn(250)
		$(this).parent().parent().siblings().find(".utils_div").hide();
		if (liindex == 0) {
			// 获取送检类型
			getClassifyAdd();
		}
		if (liindex == 1) {
			// 获取所有送检项目的检测项目
			// 获取送检类型
		  $.ajax({
		    url:'./user/getClassify',
		    type:'POST',
		    data:{},
		    datatype:'json',
		    success:function(data){
		      data = JSON.parse(data);
		      if (data.code == 1) {
		        var Classify = "";
		        for (var i = 0; i < data.data.length; i++) {
		          Classify += "<option value="+data.data[i].claid+">"+data.data[i].cname+"</option>"
		        }
		        getCheckingClassifyAdd(data.data[0].claid)
		        $('#claid').html(Classify)
		      }else{
		        alert("获取送检类型失败！错误信息：" + data.msg)
		        return false;
		      }
		    }
		  })
			
		}
		if (liindex == 2) {
			//得到库位
			// 获取送检类型
		  $.ajax({
		    url:'./user/getClassify',
		    type:'POST',
		    data:{},
		    datatype:'json',
		    success:function(data){
		      data = JSON.parse(data);
		      if (data.code == 1) {
		        var Classify = "";
		        for (var i = 0; i < data.data.length; i++) {
		          Classify += "<option value="+data.data[i].claid+">"+data.data[i].cname+"</option>"
		        }
		        getWarehouseAdd(data.data[0].claid)
		        $('#claid_warehouse').html(Classify)
		      }else{
		        alert("获取送检类型失败！错误信息：" + data.msg)
		        return false;
		      }
		    }
		  })
			
		}
		if (liindex == 3) {
			// 获得产线
			getLineAdd();
		}
		if (liindex == 4) {
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
		          Lines += "<option value="+data.data[i].lid+">"+data.data[i].lname+"</option>"
		        }
		        // 获得单元
						getCellAdd(data.data[0].lid);
		        $('#lid').html(Lines)
		      }else{
		        alert("获取产线失败！错误信息：" + data.msg)
		        return false;
		      }
		    }
		  })
			
		}
		if (liindex == 5) {
			// 获取项目
			getProjectAdd()
		}
	});
	// 增加送检类型
	$('#addClassify').click(function(){
		var cname = $('#cname').val()
		if (cname == "" || cname == null) {
			alert("请输入添加的送检类型名称！")
			return false;
		} 
	  $.ajax({
	    url:'./user/addClassify',
	    type:'POST',
	    data:{"cName":cname},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("添加成功")
	       getClassifyAdd();
	      }else{
	        alert("获取送检类型失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 删除送检类型
	$('#classify_table').delegate('#deleteClassify','click',function(){
		var claid = $(this).parent().parent().find("td:eq(0)").text()
		console.log(claid)
		$.ajax({
	    url:'./user/deleteClassify',
	    type:'POST',
	    data:{"claid":claid},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("删除成功")
	       getClassifyAdd();
	      }else{
	        alert("删除送检类型失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 增加检测类型
	$('#addCheckingClassify').click(function(){
		var ccname = $('#ccname').val()
		var claid = $('#claid').val()
		if (ccname == "" || ccname == null) {
			alert("请输入添加的检测类型名称！")
			return false;
		} 
	  $.ajax({
	    url:'./user/addCheckingClassify',
	    type:'POST',
	    data:{"claId":claid,"ccName":ccname},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("添加成功")
	       getCheckingClassifyAdd(claid);
	      }else{
	        alert("获取检测类型失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 删除检测类型
	$('#checking_classify_table').delegate('#deleteCheckingClassfy','click',function(){
		var ccid = $(this).parent().parent().find("td:eq(0)").text()
		var claid = $('#claid').val()
		$.ajax({
	    url:'./user/deleteCheckingClassify',
	    type:'POST',
	    data:{"ccId":ccid},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("删除成功")
	       getCheckingClassifyAdd(claid);
	      }else{
	        alert("删除检测类型失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 查询对应的检测类型
	$('#claid').change(function(){
		var claid = $('#claid').val()
		getCheckingClassifyAdd(claid);
	})

	// 增加库位
	$('#addWarehouse').click(function(){
		var claid = $('#claid').val()
		var warehouse = $('#warehouse').val()
		if (warehouse == "" || warehouse == null) {
			alert("请输入添加的库位名称！")
			return false;
		} 
	  $.ajax({
	    url:'./user/',
	    type:'POST',
	    data:{"0":0},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("添加成功")
	       getWarehouseAdd(clid);
	      }else{
	        alert("获取库位信息失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 删除库位
	$('#warehouse_table').delegate('#deleteWarehouse','click',function(){
		var warehouse = $(this).parent().parent().find("td:eq(0)").text()
		var claid = $('#claid').val()
		$.ajax({
	    url:'./user/',
	    type:'POST',
	    data:{"0":0},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("删除成功")
	       getWarehouseAdd(clid);
	      }else{
	        alert("删除库位信息失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})
	// 查询对应的检测类型
	$('#claid_warehouse').change(function(){
		var claid = $('#claid_warehouse').val()
		getWarehouseAdd(claid);
	})

	// 增加产线
	$('#addLine').click(function(){
		var lName = $('#lname').val()
		if (lName == "" || lName == null) {
			alert("请输入添加的产线名称！")
			return false;
		} 
	  $.ajax({
	    url:'./user/addLine',
	    type:'POST',
	    data:{"lName":lName},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("添加成功")
	       getLineAdd();
	      }else{
	        alert("获取产线信息失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 删除产线
	$('#line_table').delegate('#deleteLine','click',function(){
		var lid = $(this).parent().parent().find("td:eq(0)").text()
		$.ajax({
	    url:'./user/deleteLine',
	    type:'POST',
	    data:{"lid":lid},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("删除成功")
	       getLineAdd();
	      }else{
	        alert("删除产线信息失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 增加单元
	$('#addCell').click(function(){
		var lid = $('#lid').val();
		var cellname = $('#cellname').val()
		if (cellname == "" || cellname == null) {
			alert("请输入添加的单元名称！")
			return false;
		} 
	  $.ajax({
	    url:'./user/addCell',
	    type:'POST',
	    data:{"lid":lid,"cname":cellname},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("添加成功")
	       getCellAdd(lid);
	      }else{
	        alert("获取单元信息失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 删除单元
	$('#cell_table').delegate('#deleteCell','click',function(){
		var cid = $(this).parent().parent().find("td:eq(0)").text()
		var lid = $('#lid').val();
		$.ajax({
	    url:'./user/deleteCell',
	    type:'POST',
	    data:{"cid":cid},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("删除成功")
	       getCellAdd(lid);
	      }else{
	        alert("删除单元信息失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 查询对应的检测类型
	$('#lid').change(function(){
		var lid = $('#lid').val()
		getCellAdd(lid);
	})

	// 增加项目
	$('#addProject').click(function(){
		var pname = $('#pname').val()
		if (pname == "" || pname == null) {
			alert("请输入添加的项目信息名称！")
			return false;
		} 
	  $.ajax({
	    url:'./user/addProject',
	    type:'POST',
	    data:{"pName":pname},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("添加成功")
	       getProjectAdd();
	      }else{
	        alert("获取项目信息失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

	// 删除项目
	$('#project_table').delegate('#deleteProject','click',function(){
		var pid = $(this).parent().parent().find("td:eq(0)").text()
		$.ajax({
	    url:'./user/deleteProject',
	    type:'POST',
	    data:{"pId":pid},
	    datatype:'json',
	    success:function(data){
	      data = JSON.parse(data);
	      if (data.code == 1) {
	       alert("删除成功")
	       getProjectAdd();
	      }else{
	        alert("删除项目信息失败！错误信息：" + data.msg)
	        return false;
	      }
	    }
	  })
	})

})