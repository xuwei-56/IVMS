package com.IVMS.test;

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
import com.IVMS.util.LdapUtil;
/**
 * 通过LDAP拿到登录人的个人信息
 * @author as PiscesTong
 *
 */
public class LDAPTest {  
    public static void main(String[] args) {  
    	User user=LdapUtil.getUserInfo("nancy.he", "nancy.");
    	System.out.println(user);
    }
}
