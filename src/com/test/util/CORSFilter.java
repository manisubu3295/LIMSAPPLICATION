package com.test.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter{
	
	@Override
	public void destroy() {
		
	}
	@Override
	public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse,FilterChain chain) throws IOException,ServletException{
		HttpServletRequest request=(HttpServletRequest) servletRequest;
		HttpServletResponse response=(HttpServletResponse) servletResponse;
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,OPTIONS,POST,HEAD,PUT,POST");
		response.addHeader("Access-Control-Allow-Headers", "content-type");
		
		if(request.getMethod().equals("OPTIONS")) {
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return ;
		}
		chain.doFilter(servletRequest, servletResponse);
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException{
		
	}
	
	
}
