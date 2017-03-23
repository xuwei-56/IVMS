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


@WebFilter({ "/AdminLoginFilter", "/html/admin" })
public class AdminLoginFilter implements Filter {

    public AdminLoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		if(user == null) {
			response.getWriter().print("您还没有登录!");
			HttpServletResponse httpResponse=(HttpServletResponse) response;
			httpResponse.sendRedirect("/login");
			return;
		}
		if(!user.getDepartment().equals("QA")) {
			response.getWriter().print("您的等级不够！");
			HttpServletResponse httpResponse=(HttpServletResponse) response;
			httpResponse.sendRedirect("/user_index");
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
