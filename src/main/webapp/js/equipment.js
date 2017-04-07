function getDepartments(){
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
        $('#departmentName').html(department)
      }else{
        alert("获取部门信息失败！错误信息：" + data.msg)
        //return false;
      }
    }
  })
}
// 根据部门获取员工信息
function getUserByDepartment(department,ui){
  $.ajax({
    url:'./user/getUserInfoByDepartment',
    type:'POST',
    data:{'department':department},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var user = "";
        for (var i = 0; i < data.data.length; i++) {
          user += "<option value="+data.data[i].mail+">"+data.data[i].cn+"</option>";
        }
        $("#"+ ui +"").html(user);
      }else{
        alert(data.msg)
        //return false;
      }
    }
  }) 
}
function getLinesEM(){
  // 获取产线
  $.ajax({
    url:'./user/getLines',
    type:'POST',
    data:{},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var Lines = "<option value=''>请选择产线</option>";
        for (var i = 0; i < data.data.length; i++) {
          Lines += "<option value="+data.data[i].lid+">"+data.data[i].lname+"</option>"
        }
        $('#lId').html(Lines)
      }else{
        alert("获取产线失败！错误信息：" + data.msg)
        //return false;
      }
    }
  })
}
// 获取对应产线下的单元
function getCellEM(lid,ui){
  $.ajax({
    url:'./user/getCellNames',
    type:'POST',
    data:{'LineId':lid},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var CellNames = "";
        for (var i = 0; i < data.data.length; i++) {
          CellNames += "<option value="+data.data[i].cid+">"+data.data[i].cname+"</option>";
        }
        $("#"+ ui +"").html(CellNames);
      }else{
        alert(data.msg)
        //return false;
      }
    }
  })
}
$(document).ready(function(){
  //验证码
  createCode();
  //前端验证验证码
  $('#Vcode').blur(function(){
    validate ();
  })

  //检测登录
  $('#button_login').click(function() {

    var userId = removeAllSpace($('#user').val());
    var password = removeAllSpace($('#password').val());
    var verifyCode = removeAllSpace($('#Vcode').val());
    
    if (userId == "" || password == "" || verifyCode == "") {
      if (userId == "") {
        alert("请输入管理员帐号");
        return false;
      }
      if (password == "") {
        alert("请输入管理员密码");
        return false;
      }
      if (verifyCode == "") {
        alert("请输入管理员密码");
        return false;
      }
    }else{
      $.ajax({
        url:"./user/login",
        type:"POST",
        data:{"userId":userId,"password":password,"verifyCode":verifyCode},
        datatype:"json",
        success:function(data){
          data = JSON.parse(data);
          if(data.code == 1){
            alert("登录成功！")
            $('#equipment_login').fadeOut();
            $('#login_li').hide();
            $('#logout_li').show();
          }else{
            alert(data.msg)
          }
        }
      });
    }
  })
  $('#button_cancel').click(function(){
    $('#equipment_login').fadeOut();
  })
  // 登录弹框
  $('#login').click(function(){
    $('#equipment_login').fadeIn();
  })

  // 加载页面内容
  $.ajax({
    url:"./user/equipmentInfo",
    type:"POST",
    data:{"cid":null,"eworker":0,"ename":0,'requestPageNum':1},
    datatype:"json",
    success:function(data){
      data = JSON.parse(data);
      if(data.code == 1){
        Count = parseInt(data.msg);
        $('#pageTool').html("");
        var pageSize = 20;
        $('#pageTool').Paging({pagesize:pageSize,count:Count,callback:function(page,size,count){
          $.ajax({
            url:"./user/equipmentInfo",
            type:"POST",
            data:{"cid":null,"eworker":0,"ename":0,'requestPageNum':page},
            datatype:"json",
            success:function(data){
              data = JSON.parse(data);
              if(data.code == 1){
                Count = parseInt(data.msg);
                var emdata = "<tr><th>设备号</th><th>设备名</th><th>负责人</th><th>所属产线</th><th>所属单元</th><th>上次检测时间</th><th>下次检测时间</th><th style='width: 250px'>操作</th></tr>"
                data.data.forEach(function(em){
                  emdata += "<tr><td>"+ em.EId +"</td><td>"+ em.EName +"</td><td>"+ em.EWorker +"</td><td>"+ em.LName +"</td><td>"+ em.CName +"</td><td>"+ em.ECTime +"</td><td>"+ em.ECNextTime +"</td><a class='inner_btn' id='comfirm_check'>确认检测</a><a class='inner_btn' id='updete_equipment'>详情</a><a class='inner_btn' id='delete_equipment'>删除</a><td></td></tr>"
                })
                $('#emtable').html(emdata);
              }
            }
          })
        }});
        var emdata = "<tr><th>设备号</th><th>设备名</th><th>负责人</th><th>所属产线</th><th>所属单元</th><th>上次检测时间</th><th>下次检测时间</th><th style='width: 250px'>操作</th></tr>"
        data.data.forEach(function(em){
          emdata += "<tr><td>"+ em.EId +"</td><td>"+ em.EName +"</td><td>"+ em.EWorker +"</td><td>"+ em.LName +"</td><td>"+ em.CName +"</td><td>"+ em.ECTime +"</td><td>"+ em.ECNextTime +"</td><a class='inner_btn' id='comfirm_check'>确认检测</a><a class='inner_btn' id='updete_equipment'>详情</a><a class='inner_btn' id='delete_equipment'>删除</a><td></td></tr>"
        })
        $('#emtable').html(emdata);
      }else{
        alert(data.msg)
      }
    }
  });

  // 搜索
  var cid = null;
  var eworker = 0;
  var ename = 0;
  $('#button_searchequipment').click(function(){
    cid = $('#bycId').val()
    eworker = $('#byuserName').val()
    ename = $('#byename').val();
    if (eworker == "" || eworker == null) {eworker = 0}
    if (ename == "" || ename == null) {ename = 0}
    $.ajax({
      url:"./user/equipmentInfo",
      type:"POST",
      data:{"cid":cid,"eworker":eworker,"ename":ename,'requestPageNum':1},
      datatype:"json",
      success:function(data){
        data = JSON.parse(data);
        if(data.code == 1){
          Count = parseInt(data.msg);
          $('#pageTool').html("");
          var pageSize = 20;
          $('#pageTool').Paging({pagesize:pageSize,count:Count,callback:function(page,size,count){
            $.ajax({
              url:"./user/equipmentInfo",
              type:"POST",
              data:{"cid":null,"eworker":0,"ename":0,'requestPageNum':page},
              datatype:"json",
              success:function(data){
                data = JSON.parse(data);
                if(data.code == 1){
                  Count = parseInt(data.msg);
                  var emdata = "<tr><th>设备号</th><th>设备名</th><th>负责人</th><th>所属产线</th><th>所属单元</th><th>上次检测时间</th><th>下次检测时间</th><th style='width: 250px'>操作</th></tr>"
                  data.data.forEach(function(em){
                    emdata += "<tr><td>"+ em.EId +"</td><td>"+ em.EName +"</td><td>"+ em.EWorker +"</td><td>"+ em.LName +"</td><td>"+ em.CName +"</td><td>"+ em.ECTime +"</td><td>"+ em.ECNextTime +"</td><a class='inner_btn' id='comfirm_check'>确认检测</a><a class='inner_btn' id='updete_equipment'>详情</a><a class='inner_btn' id='delete_equipment'>删除</a><td></td></tr>"
                  })
                  $('#emtable').html(emdata);
                }
              }
            })
          }});
          var emdata = "<tr><th>设备号</th><th>设备名</th><th>负责人</th><th>所属产线</th><th>所属单元</th><th>上次检测时间</th><th>下次检测时间</th><th style='width: 250px'>操作</th></tr>"
          data.data.forEach(function(em){
            emdata += "<tr><td>"+ em.EId +"</td><td>"+ em.EName +"</td><td>"+ em.EWorker +"</td><td>"+ em.LName +"</td><td>"+ em.CName +"</td><td>"+ em.ECTime +"</td><td>"+ em.ECNextTime +"</td><a class='inner_btn' id='comfirm_check'>确认检测</a><a class='inner_btn' id='updete_equipment'>详情</a><a class='inner_btn' id='delete_equipment'>删除</a><td></td></tr>"
          })
          $('#emtable').html(emdata);
        }else{
          alert(data.msg)
        }
      }
    });
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
        var Lines = "<option>所有产线</option>";
        for (var i = 0; i < data.data.length; i++) {
          Lines += "<option value="+data.data[i].lid+">"+data.data[i].lname+"</option>"
        }
        $('#bylId').html(Lines)
      }else{
        alert("获取产线失败！错误信息：" + data.msg)
        //return false;
      }
    }
  })
  // 获取对应产线下的单元
  $('#bylId').change(function(){
    var lid = $('#bylId').val();
    var ui = "bycId"
    if (lid == null) { return false }
    getCellEM(lid,ui);
  })


  // 得到部门
  $.ajax({
    url:'./user/getDepartments',
    type:'POST',
    data:{},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var department = "<option>全部部门</option>";
        for (var i = 0; i < data.data.length; i++) {
          department += "<option value="+data.data[i]+">"+data.data[i]+"</option>"
        }
        $('#bydepartmentName').html(department)
      }else{
        alert("获取部门信息失败！错误信息：" + data.msg)
        //return false;
      }
    }
  })
  // 得到部门下的所有员工
  $('#bydepartmentName').change(function(){
    var department = $('#bydepartmentName').val();
    var ui = "byuserName"
    if (department == null || department == "") {
      return false;
    }
    getUserByDepartment(department,ui);
  })

  // 退出登录
  $('#logout').click(function(){
    $.ajax({
      url:'./user/exit',
      type:'POST',
      data:{},
      datatype:'json',
      success:function(data){
        data = JSON.parse(data);
        if (data.code == 1) {
          window.location.reload()
        }else{
          if (data.code < 0) {
            alert(data.msg)
          }
          //return false;
        }
      }
    })
  })
  // 添加设备弹框
  $('#add_equipment_a').click(function(){
    $('#add_equipment_pop').fadeIn(200);
    getLinesEM();
    getDepartments();
  })
  // 设备添加产线
  $('#lid').change(function(){
     var lid = $('#lid').val();
     var ui = "cid";
    if (lid == null) { return false }
    getCellEM(lid,cid);
  })
  // 设备添加得到部门下的所有员工
  $('#departmentname').change(function(){
    var department = $('#departmentname').val();
    var ui = "username"
    if (department == null || department == "") {
      return false;
    }
    getUserByDepartment(department,ui);
  })
  // 取消弹框
  $('.falseBtn').click(function(){
    $('#add_equipment_pop').fadeOut(200);
  })

  // 提交增加设备
  $('#add_equipment_btn').click(function(){
    var addename = $('#ename').val()
    var addecheckcycle = $('#checkcycle').val()
    var addcid = $('#cid').val()
    var addeworker = $('#username').val()
    $.ajax({
      url:'./user/addEquipment',
      type:'POST',
      data:{'ename':addename,'echeckcycle':addecheckcycle,'cid':addcid,'eworker':addeworker},
      datatype:'json',
      success:function(data){
        data = JSON.parse(data);
        if (data.code == 1) {
          alert('添加成功！')
          window.location.reload()
        }else{
          if (data.code < 0) {
            alert(data.msg)
          }
          //return false;
        }
      }
    })
  })

})