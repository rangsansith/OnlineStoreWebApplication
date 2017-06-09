package com.onlineStore.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Servlet Filter implementation class DecodingFilter
 */
public class DecodingFilter implements Filter {
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hRequest = (HttpServletRequest) request;
		EnhanceRequest enhanceRequest = new EnhanceRequest(hRequest);
		
		chain.doFilter(enhanceRequest, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	class EnhanceRequest extends HttpServletRequestWrapper {
		private HttpServletRequest request;
		public EnhanceRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		
		@Override
		public String getParameter(String name) {
			String parameter = request.getParameter(name);
			try {
				parameter = new String(parameter.getBytes("iso8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return parameter;
		}
		
	}
}
