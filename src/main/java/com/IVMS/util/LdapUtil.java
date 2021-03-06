package com.IVMS.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.IVMS.model.User;

/**
 * 通过LDAP拿到登录人的个人信息，部门信息，通过部门拿到登录人的各种信息，
 * 先要通过工具连接LDAP才能获取信息
 * @author as PiscesTong
 *
 */
public class LdapUtil{
	private static Logger logger= LoggerFactory.getLogger(LdapUtil.class);
	private static Properties props = null;
	
	public static LdapContext getLdapContext(String sAMAccountName,String password){
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
	    LdapContext ctx=null;
		try {
			ctx = new InitialLdapContext(env, null);
			System.out.println("身份验证成功！");
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println("身份验证失败！");
		}  
	    return ctx;
	}
	
	public static User getLoginUserInfo(LdapContext ctx,String sAMAccountName){
		logger.info("LDAP 获取登录的个人信息");
		User user = null;
		SearchControls searchCtls = new SearchControls();  
	    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);  
//	    String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=*))";
	    /*过滤器，通过定义变量的名字来进行相对应的过滤，这里过滤登录名，获取登录人的信息*/
	    String searchFilter = "(&(sAMAccountName="+sAMAccountName+"))"; 
	    String searchBase = props.getProperty("searchBase");  
	    /*过滤之后设置想获取登录人的字段名称，用于返回对应字段的值*/
	    String returnedAtts[] = {"description","mobile","cn","mail","department","pager"};  
	    searchCtls.setReturningAttributes(returnedAtts);  
	    NamingEnumeration<SearchResult> answer;
		try {
			answer = ctx.search(searchBase, searchFilter,searchCtls);
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
				if (attributes.get("department") != null){
					user.setDepartment((String)attributes.get("department").get());
				}
				if (attributes.get("pager") != null){
					user.setPager((String)attributes.get("pager").get());
				}
		    } 
			logger.info("user:"+user); //写入日志文件
		    System.out.println("user:"+user);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally{
            if(ctx!=null){
                try {
                    ctx.close();
                    ctx=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
		return user;
	}
	public static Set<String> getDepartmentsInfo(LdapContext ctx){
		logger.info("LDAP 获取整个部门信息");
		SearchControls searchCtls = new SearchControls();  
	    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);  
//	    String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=*))";
	    String searchFilter = "(&(objectClass=user))"; 
	    String searchBase = props.getProperty("searchBase");  
	    String returnedAtts[] = {"department"};  
	    searchCtls.setReturningAttributes(returnedAtts);  
	    NamingEnumeration<SearchResult> answer;
	    Set<String>departments=null;
		try {
			answer = ctx.search(searchBase, searchFilter,searchCtls);
	        departments=new HashSet<String>();
			while (answer.hasMoreElements()) {  
		        SearchResult sr = (SearchResult) answer.next();  
		        Attributes attributes=sr.getAttributes();
				if (attributes.get("department") != null){
					departments.add((String)attributes.get("department").get());
				}	
		    } 
			logger.info("departments:"+departments); //写入日志文件
		    System.out.println("departments:"+departments);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally{
            if(ctx!=null){
                try {
                    ctx.close();
                    ctx=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
		return departments;
	}
	public static List<User> getUserInfoByDepartment(LdapContext ctx,String department){
		logger.info("LDAP 通过指定部门获取员工信息");
		User user=null;
		SearchControls searchCtls = new SearchControls();  
	    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);  
//	    String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=*))";
	    String searchFilter = "(&(department="+department+"))"; 
	    String searchBase = props.getProperty("searchBase");  
	    String returnedAtts[] = {"description","cn","mail"};  
	    searchCtls.setReturningAttributes(returnedAtts);  
	    NamingEnumeration<SearchResult> answer;
	    List<User>userInfo=null;
		try {
			answer = ctx.search(searchBase, searchFilter,searchCtls);
			userInfo=new ArrayList<User>();
			while (answer.hasMoreElements()) {  
				user=new User();
		        SearchResult sr = (SearchResult) answer.next();  
		        Attributes attributes=sr.getAttributes();
				if (attributes.get("description") != null){
					user.setDescription(((String)attributes.get("description").get()));
				}
				if (attributes.get("cn") != null){
					user.setCn(((String)attributes.get("cn").get()));
				}	
				if (attributes.get("mail") != null){
					user.setMail(((String)attributes.get("mail").get()));
				}
				userInfo.add(user);
		    } 
			logger.info("userInfoByDepartment:"+userInfo); //写入日志文件
			System.out.println("userInfoByDepartment:"+userInfo);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally{
            if(ctx!=null){
                try {
                    ctx.close();
                    ctx=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
		return userInfo;
	}
	
	public static String getEmailByCn(LdapContext ctx,String cn){
		logger.info("LDAP 通过员工名字获取员工邮箱");
		SearchControls searchCtls = new SearchControls();  
	    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);  
//	    String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=*))";
	    String searchFilter = "(&(cn="+cn+"))"; 
	    String searchBase = props.getProperty("searchBase");  
	    String returnedAtts[] = {"mail"};  
	    searchCtls.setReturningAttributes(returnedAtts);  
	    NamingEnumeration<SearchResult> answer;
	    String email=null;
		try {
			answer = ctx.search(searchBase, searchFilter,searchCtls);
			while (answer.hasMoreElements()) {  
		        SearchResult sr = (SearchResult) answer.next();  
		        Attributes attributes=sr.getAttributes();	
				if (attributes.get("mail") != null){
					email=(String) attributes.get("mail").get();
				}
		    } 
			logger.info("email:"+email); //写入日志文件
		} catch (NamingException e) {
			e.printStackTrace();
		} finally{
            if(ctx!=null){
                try {
                    ctx.close();
                    ctx=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
		return email;
	}
}
