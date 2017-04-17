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


@WebFilter({ "/AdminLoginFilter", "/admin_index","/admin_tools","/admin_utils"})
public class AdminLoginFilter implements Filter {

    public AdminLoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse httpResponse=(HttpServletResponse) response;
		httpResponse.setContentType("text/html;charset=utf-8");
		User user = (User) req.getSession().getAttribute("user");
		String itemName=request.getServletContext().getContextPath();
		if(user == null) {
			response.getWriter().print("您还没有登录!");
			String path=itemName+"/login";
			httpResponse.setHeader("Refresh", "2;URL="+path);
			return;
		}
		if(user.getPager()==null||!user.getPager().equals("2")){
			response.getWriter().print("您不是检测人员，不能查看检测人员页面！");
			String path=itemName+"/user_index";
			httpResponse.setHeader("Refresh", "2;URL="+path);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
