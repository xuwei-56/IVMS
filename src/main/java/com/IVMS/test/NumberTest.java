package com.IVMS.test;

import java.util.ArrayList;
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
		System.out.println(1);
		List<String>list1=new ArrayList<String>();
		list1.add("hello");
		list1.add("world");
		list1.add("java");
		
		List<String>list2=new ArrayList<String>();
		list2.add("123");
		list2.add("456");
		
		list1.addAll(list2);
		for(String str:list1){
			System.out.println(str);
		}
	}
}
