package com.IVMS.test;

import java.util.List;

import javax.naming.ldap.LdapContext;

import com.IVMS.model.User;
import com.IVMS.util.LdapUtil;
/**
 * 通过LDAP拿到登录人的个人信息
 * @author as PiscesTong
 *
 */
public class LDAPTest {  
    public static void main(String[] args) {  
    	LdapContext ctx=LdapUtil.getLdapContext("tianpeng.tang", "1234.abcD");
//    	System.out.println(ctx);//ctx=null,可能是未用工具连接vpn，也有可能是账号或密码错误
//    	User user=null;
//    	if(ctx!=null){
//    		user=LdapUtil.getLoginUserInfo(ctx,"nancy.he");
//    	}
//    	Set<String>departments=LdapUtil.getDepartmentsInfo(ctx);
//    	System.out.println(departments);
//    	List<User>userInfo=LdapUtil.getUserInfoByDepartment(ctx, "IT");
    	LdapUtil.getEmailByCn(ctx, "Tianpeng Tang");
    }
}
