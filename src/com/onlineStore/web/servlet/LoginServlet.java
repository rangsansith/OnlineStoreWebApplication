package com.onlineStore.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlineStore.domain.User;
import com.onlineStore.service.UserService;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		UserService userService = new UserService();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.login(username, password);
		
		if (user == null) {
			request.setAttribute("errorMsg", "用户名或密码不存在");
			session.removeAttribute("currentUser");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			session.setAttribute("currentUser", user);
			String autologin = request.getParameter("autologin");
			if ("autologin".equals(autologin)) {
				Cookie usernameCookie = new Cookie("username", username);
				Cookie passwordCookie = new Cookie("password", password);
				usernameCookie.setMaxAge(7*24*60*60);
				passwordCookie.setMaxAge(7*24*60*60);
				response.addCookie(usernameCookie);
				response.addCookie(passwordCookie);
			}
			response.sendRedirect(request.getContextPath() + "/index");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}