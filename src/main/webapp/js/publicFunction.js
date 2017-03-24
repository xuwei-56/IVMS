//去除空格
function removeAllSpace(str) {
	return str.replace(/\s+/g, "");
}
//js脚本中过滤特殊字符的正则表达式代码：
function stripscript(s) 
{ 
  var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]") 
  var rs = ""; 
  for (var i = 0; i < s.length; i++) { 
    rs = rs+s.substr(i, 1).replace(pattern, ''); 
  } 
  return rs;

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
        )).getTime() / 1000;
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
    ymdhis += (time.getUTCMonth()+1) + "-";
    ymdhis += time.getUTCDate();
    ymdhis += " " + time.getUTCHours() + ":";
  	ymdhis += time.getUTCMinutes() + ":";
  	ymdhis += time.getUTCSeconds();
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
    ymdhis += (time.getUTCMonth()+1) + "-";
    ymdhis += time.getUTCDate();
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