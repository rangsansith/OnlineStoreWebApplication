package com.onlineStore.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("currentUser");
		Cookie usernameCookie = new Cookie("username", "");
		Cookie passwordCookie = new Cookie("password", "");
		usernameCookie.setMaxAge(0);
		passwordCookie.setMaxAge(0);
		response.addCookie(usernameCookie);
		response.addCookie(passwordCookie);
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}