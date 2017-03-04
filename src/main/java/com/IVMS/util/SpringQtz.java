package com.IVMS.util;



import org.springframework.transaction.annotation.Transactional;


/**
 * @description 定时器任务,每隔一分钟执行一次,查找order_state=1的订单,order_creat_time
 * @author gr
 *
 */
@Transactional(readOnly = true)
public class SpringQtz {
	
	/*
    * 测试定时
    * */
	protected void executeSource(){
		System.out.println("测试定时");
//		orderQuartzService.UpdateNotPayOrder();
	}
	
   
}
