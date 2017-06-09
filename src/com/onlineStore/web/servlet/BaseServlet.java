package com.onlineStore.web.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("all")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			Class myClass = this.getClass();
			String methodName = request.getParameter("method");
			Method method = myClass.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}