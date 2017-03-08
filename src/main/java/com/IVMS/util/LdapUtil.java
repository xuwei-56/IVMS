package com.IVMS.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.IVMS.model.User;

/**
 * 通过LDAP拿到登录人的个人信息，先要通过工具连接LDAP才能获取信息
 * @author as PiscesTong
 *
 */
public class LdapUtil{
	private static Properties props = null;
	
	public static User getUserInfo(String sAMAccountName,String password){
		InputStream in =LdapUtil.class.getClassLoader()
				.getResourceAsStream("ldap.properties");
		props = new Properties();
		try {
			props.load(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Properties env = new Properties();  
	    String adminName = sAMAccountName+"@"+props.getProperty("domain");//username@domain  
	    String adminPassword = password;//password  
	    String ldapURL = props.getProperty("ldapURL");//ip:port  
	    env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");  
	    env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"  
	    env.put(Context.SECURITY_PRINCIPAL, adminName);  
	    env.put(Context.SECURITY_CREDENTIALS, adminPassword);  
	    env.put(Context.PROVIDER_URL, ldapURL);  
	    User user=null;
	    try {  
	        LdapContext ctx = new InitialLdapContext(env, null);  
	        SearchControls searchCtls = new SearchControls();  
	        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);  
//	        String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=*))";
	        /*过滤器，通过定义变量的名字来进行相对应的过滤，这里过滤登录名，获取登录人的信息*/
	        String searchFilter = "(&(sAMAccountName="+sAMAccountName+"))"; 
	        String searchBase = props.getProperty("searchBase");  
	        /*过滤之后设置想获取登录人的字段名称，用于返回对应字段的值*/
	        String returnedAtts[] = {"description","mobile","cn","mail"};  
	        searchCtls.setReturningAttributes(returnedAtts);  
	        NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter,searchCtls);  
            while (answer.hasMoreElements()) {  
                SearchResult sr = (SearchResult) answer.next();  
                Attributes attributes=sr.getAttributes();
                user=new User();
                if (attributes.get("cn") != null){
                	user.setCn((String)attributes.get("cn").get());
				}
				if (attributes.get("mail") != null){
					user.setMail((String)attributes.get("mail").get());
				}
				if (attributes.get("mobile") != null){
					user.setMobile((String)attributes.get("mobile").get());
				}
				
				if (attributes.get("description") != null){
					user.setDescription((String)attributes.get("description").get());
				}
            } 
            System.out.println("user:"+user);
            ctx.close(); 
	    }catch (NamingException e) {  
	        e.printStackTrace();  
	        System.out.println("账号或密码错误！");
	    }  
	    return user;
	}
}
