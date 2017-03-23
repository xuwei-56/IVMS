package com.IVMS.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.IVMS.model.User;


@WebFilter({ "/UserLoginFilter", "/html/user" })
public class UserLoginFilter implements Filter {
	
    public UserLoginFilter() {
    }
    
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		System.out.println(user);
		if(user == null) {
			response.getWriter().print("您还没有登录!");
			HttpServletResponse httpResponse=(HttpServletResponse) response;
			httpResponse.sendRedirect("/login");
			return;
		}
		if(user.getDepartment().equals("QA")){
			response.getWriter().print("您不是送检人员，不能查看送检界面!");
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
