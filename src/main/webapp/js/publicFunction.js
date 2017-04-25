$(document).ready(function(){
  $('.quit_icon').click(function(){
    $.ajax({
      url:'./user/exit',
      type:'POST',
      data:{},
      datatype:'json',
      success:function(data){
        data = JSON.parse(data);
        if (data.code == 1) {
          location.href="./index"
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
//去除空格
function removeAllSpace(str) {
  return str.replace(/\s+/g, "");
}
//js脚本中过滤特殊字符的正则表达式代码：
function stripscript(s) 
{ 
  var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?@#￥&*]") 
  var rs = ""; 
  for (var i = 0; i < s.length; i++) { 
    rs = rs+s.substr(i, 1).replace(pattern, ' '); 
  } 
  return rs;

}
//得到检测状态
function getStatus(str){
  var status = "";
  switch(str){
    case 0:status = "未打印凭证";break;
    case 1:status = "待检"; break;
    case 2:status = "检测中"; break;
    case 3:status = "检验通过"; break;
    case 4:status = "检验部分通过"; break;
    case 5:status = "检验未通过"; break;
    default:status = "未知状态";break;
  }
  return status;
}
//得到检具状态
function getCTStatus(str){
  var status = "";
  switch(str){
    case 0:status = "未领用";break;
    case 1:status = "已领用"; break;
    case 2:status = "维修"; break;
    case 3:status = "封存"; break;
    case 4:status = "报废"; break;
    case 5:status = "正常"; break;
    case 6:status = "未确认"; break;
    default:status = "未知状态";break;
  }
  return status;
}
//是否同意检具检测结果
function getIsAgree(str){
  var status = "";
  switch(str){
    case 0:status = "未确认";break;
    case 1:status = "同意";break;
    case 2:status = "不同意";break;
    default:status = "未确认";break;
  }
  return status;
}
//是否接受检具检测结果
function getAccept(str){
  var status = "";
  switch(str){
    case 0:status = "未确认";break;
    case 1:status = "接受";break;
    case 2:status = "不接受";break;
    default:status = "未知状态";break;
  }
  return status;
}
// 得到检具检验周期
function getCTCycle(ctcheckcycle){
  var cycle;
  switch(ctcheckcycle){
    case 0: cycle = "无";break;
    case 1: cycle = 3;break;
    case 2: cycle = 6;break;
    case 3: cycle = 12;break;
  }
  return cycle;
}
// 刷新页面
function myrefresh() 
{ 
  window.location.reload(); 
}
// 判断检具送检的零件号是否正确，正确添加检具名字到零件名称 
function getCTNameIsTrueByCtid(ctid){
  var claid = $('#claId').val();
  console.log(claid);
  if (claid == 6) {
    $.ajax({
      url:'./user/judgeCtidAndGetCTName',
      type:'POST',
      data:{'ctid':ctid},
      datatype:'json',
      success:function(data){
        data = JSON.parse(data);
        if (data.code == 1) {
          $('#componentName').val(data.data);
        }else{
          alert(data.msg)
        }
      }
    })
  }
}
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
        var checkformdata = "<tr><th>检测单号</th><th>检测日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
        data.data.forEach(function(checkform){
          checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
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
        var checkformdata = "<tr><th>检测单号</th><th>检测日期</th><th>送检类型</th><th>送检人</th><th>零件号</th><th>零件名称</th><th>检测状态</th><th style='width:200px;'>操作</th></tr>";
        data.data.forEach(function(checkform){
          checkformdata += "<tr><td>"+checkform.cfid+"</a></td><td>"+$.UnixToDateTime(checkform.cftime)+"</td><td>"+checkform.cname+"</td><td>"+checkform.cfmovep+"</td><td>"+checkform.cfcomponentid+"</td><td>"+checkform.cfcomponentname+"</td><td>"+getStatus(checkform.cfstatus)+"</td><td><a href='#' class='inner_btn' id='checkformdetail'>详情</a></td></tr>";
          })
          $('#cfspecial').html(checkformdata);
      }
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
          Classify += "<option value='"+data.data[i].claid+"'>"+data.data[i].cname+"</option>"
        }
        $('#claId').html(Classify)
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
          Projects += "<option value='"+data.data[i].pid+"'>"+data.data[i].pname+"</option>";
        }
        $('#pId').html(Projects);
        $('#bypId').html(Projects);
      }else{
        alert("获取送检项目失败！错误信息：" + data.msg)
        //return false;
      }
    }
  })
}
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
        var department = "<option></option>";
        for (var i = 0; i < data.data.length; i++) {
          department += "<option value='"+data.data[i]+"'>"+data.data[i]+"</option>"
        }
        $('#departmentName').html(department)
      }else{
        alert("获取部门信息失败！错误信息：" + data.msg)
        //return false;
      }
    }
  })
}
function getLines(){
  // 获取产线
  $.ajax({
    url:'./user/getLines',
    type:'POST',
    data:{},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var Lines = "<option></option>";
        for (var i = 0; i < data.data.length; i++) {
          Lines += "<option value='"+data.data[i].lid+"'>"+data.data[i].lname+"</option>"
        }
        $('#lId').html(Lines)
      }else{
        alert("获取产线失败！错误信息：" + data.msg)
        //return false;
      }
    }
  })
}
function getSessionUser(){
  var notifyMailData = new Array();
  $.ajax({
    url:'./user/getSessionUser',
    type:'POST',
    data:{},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        $('#moveP').val(data.data.cn)
        $('#phoneNum').val(data.data.mobile)
        $('#mail').val(data.data.mail)
        notifyMailData[0] = data.data.mail; //通知送检人本人
        $('#userNameList').html("<span class='userNameDel'>"+data.data.cn+"<a href='#' class='deleteEmail'>X</a></span>")
        // 获取部门信息
        getDepartments();
        // 获取送检类型
        getClassify();
        // 获取产线
        getLines();
        // 获取项目
        getProject();
      }
      else{
        alert("请先登录")
        location.href = "./login";
      }
    }
  })
  return notifyMailData;
}
// 根据部门获取员工信息
function getUserInfoByDepartment(department){
  $.ajax({
    url:'./user/getUserInfoByDepartment',
    type:'POST',
    data:{'department':department},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var user = "<option></option>";
        /*console.log(userData)*/
        for (var i = 0; i < data.data.length; i++) {
          user += "<option value='"+data.data[i].mail+"'>"+data.data[i].cn+"</option>";
        }
        $('#userName').html(user);
      }else{
        alert(data.msg)
      }
    }
  })
  
}
// 获取对应送检类型下的检测类型
function getCheckingClassify(claid){
  $.ajax({
    url:'./user/getCheckingClassify',
    type:'POST',
    data:{'ClassifyId':claid},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var CheckingClassify = "";
        for (var i = 0; i < data.data.length; i++) {
          CheckingClassify += "<option value='"+data.data[i].ccid+"'>"+data.data[i].ccname+"</option>";
        }
        $('#cCId').html(CheckingClassify);
      }else{
        $('#cCId').html("<option value='0'>默认类型</option>");
        //return false;
      }
    }
  })
}
// 获取库位信息
function getWarehouse(claid){
  $.ajax({
    url:'./user/getWareHouse',
    type:'POST',
    data:{'ClassifyId':claid},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        var warehouse = "";
        for (var i = 0; i < data.data.length; i++) {
          warehouse += "<option value='"+data.data[i].wid+"'>"+data.data[i].wid+"</option>";
        }
        $('#wId').html(warehouse);
      }else{
        if (data.code < 0) {
          alert(data.msg)
        }
        //return false;
      }
    }
  })
}
// 获取对应产线下的单元
function getCellNames(lid){
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
          CellNames += "<option value='"+data.data[i].cid+"'>"+data.data[i].cname+"</option>";
        }
        $('#cId').html(CellNames);
      }else{
        alert(data.msg)
        //return false;
      }
    }
  })
}
// 判断零件号是不是检具编号
function isCheckingTool(ctid){
  var flag = false;
  $.ajax({
    url:'./user/judgeCtid',
    type:'POST',
    data:{'ctid':ctid},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        flag = true;
      }else{
        alert(data.msg)
      }
    }
  })
  return flag;
}
// 判断零件号是否合法，合法得到检具名
function getCTNameByCtid(ctid){
  $.ajax({
    url:'./user/judgeCtidAndGetCTName',
    type:'POST',
    data:{'ctid':ctid},
    datatype:'json',
    success:function(data){
      data = JSON.parse(data);
      if (data.code == 1) {
        $('#componentName').val(data.data);
      }else{
        alert(data.msg)
      }
    }
  })
}
//工具方法
$.extend({  
  //获取GET方式传递的参数
  getUrlVars: function(){  
    var vars = [], hash;  
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');  
    for(var i = 0; i < hashes.length; i++)  
    {  
      hash = hashes[i].split('=');  
      vars.push(hash[0]);  
      vars[hash[0]] = hash[1];  
    }  
    return vars;  
  },  
  getUrlVar: function(name){  
    return $.getUrlVars()[name];  
  },

  
  /**
   * 当前时间戳
   * @return <int>    unix时间戳(秒) 
   */
  CurTime: function(){
    return Date.parse(new Date())/1000;
  },
  /**       
   * 日期 转换为 Unix时间戳
   * @param <string> 2014-01-01 20:20:20 日期格式       
   * @return <int>    unix时间戳(秒)       
   */
  DateToUnix: function(string) {
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
        parseInt(d[0], 10) || null,
        (parseInt(d[1], 10) || 1) - 1,
        parseInt(d[2], 10) || null,
        parseInt(t[0], 10) || null,
        parseInt(t[1], 10) || null,
        parseInt(t[2], 10) || null
        )).getTime() ;
  },
  /**       
   * 时间戳转换日期       
   * @param <int> unixTime  待时间戳(秒)            
   * @param <int> timeZone  时区       
   */
  UnixToDateTime: function(unixTime) {//由于东八区 所以加8小时
    var time = new Date(unixTime + 8 * 60 * 60 * 1000);
    var ymdhis = "";
    ymdhis += time.getUTCFullYear() + "-";
    ymdhis += ((time.getUTCMonth()+1)<10?"0"+(time.getUTCMonth()+1):(time.getUTCMonth()+1)) + "-";
    ymdhis += (time.getUTCDate()<10?"0"+time.getUTCDate():time.getUTCDate());
    ymdhis += " " + (time.getUTCHours()<10?"0"+time.getUTCHours():time.getUTCHours()) + ":";
    ymdhis += (time.getUTCMinutes()<10?"0"+time.getUTCMinutes():time.getUTCMinutes()) + ":";
    ymdhis += (time.getUTCSeconds()<10?"0"+time.getUTCSeconds():time.getUTCSeconds());
    return ymdhis;
  },
  /**       
   * 时间戳转换日期 精确到天      
   * @param <int> unixTime  待时间戳(秒)            
   * @param <int> timeZone  时区       
   */
  UnixToDate: function(unixTime) {//由于东八区 所以加8小时
    var time = new Date(unixTime + 8 * 60 * 60 * 1000);
    var ymdhis = "";
    ymdhis += time.getUTCFullYear() + "-";
    ymdhis += ((time.getUTCMonth()+1)<10?"0"+(time.getUTCMonth()+1):(time.getUTCMonth()+1)) + "-";
    ymdhis += (time.getUTCDate()<10?"0"+time.getUTCDate():time.getUTCDate());
    return ymdhis;
  }
});  
/*//时间显示
$(function(){  
    setInterval("getTime();",1000); //每隔一秒执行一次  
})  */
//取得系统当前时间  
function getTime(){  
    var myDate = new Date();  
    var date = myDate.toLocaleDateString();  
    var hours = myDate.getHours();  
    var minutes = myDate.getMinutes();  
    var seconds = myDate.getSeconds();  
    $("#showDate").html(date+" "+hours+":"+minutes+":"+seconds); //将值赋给div  
} 