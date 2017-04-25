package com.IVMS.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NumberTest {
	public static void main(String[] args) {
//        int i = 8925;
        //得到一个NumberFormat的实例
//        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
//        nf.setGroupingUsed(false);
        //设置最大整数位数
//        nf.setMaximumIntegerDigits(5);
        //设置最小整数位数    
//        nf.setMinimumIntegerDigits(5);
        //输出测试语句
//        System.out.println(nf.format(i));
//		String[]copySendEmails=null;
//		 for(String email:copySendEmails){
//			 NotifyPersonnelEmail personnelEmail=new NotifyPersonnelEmail();
//	        	personnelEmail.setCfid("222");
//	        	personnelEmail.setNpenotifyemail(email);
//	        	System.out.println(personnelEmail);
//		 }
//		System.out.println(1);
//		List<String>list1=new ArrayList<String>();
//		list1.add("hello");
//		list1.add("world");
//		list1.add("java");
//		
//		List<String>list2=new ArrayList<String>();
//		list2.add("123");
//		list2.add("456");
//		
//		list1.addAll(list2);
//		for(String str:list1){
//			System.out.println(str);
//		}
//		Calendar calendar=Calendar.getInstance();
//		calendar.setTime(new Date());
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//        String time=sdf.format(new Date());
//		System.out.println(time);
//		calendar.add(Calendar.MONTH,9);
//		Date nextCheckTime=calendar.getTime();//得到设备下次检验时间
//		sdf=new SimpleDateFormat("yyyyMMdd");
//        time=sdf.format(nextCheckTime);
//		System.out.println(time);\
//		Integer cfid=1;
//		Integer SCFComponentId=2;
//		String str="你的检具送检已开始检测。"+"\r\n送检单号："+cfid+"\r\n检具编号："+SCFComponentId;
//		System.out.println(str);
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String time=sdf.format(date);
		System.out.println(time);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,-3);
		Date nextCheckTime=calendar.getTime();//得到设备下次检验时间
		sdf=new SimpleDateFormat("yyyyMMdd");
        String nextTime=sdf.format(nextCheckTime);
		System.out.println(nextTime);
	}
}
