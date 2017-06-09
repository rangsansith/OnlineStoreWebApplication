package com.onlineStore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.onlineStore.domain.User;
import com.onlineStore.service.UserService;
import com.onlineStore.utils.CommonUtils;
import com.onlineStore.utils.MailUtils;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		Map<String, String[]> userMap = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, userMap);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

//		private String uid;
		user.setUid(CommonUtils.getUUID());
//		private String telephone;
		user.setTelephone(null);
//		private int state; // 激活状态
		user.setState(0);
//		private String code; // 激活码
		String activeCode = CommonUtils.getUUID();
		user.setCode(activeCode);
		
		UserService userService = new UserService();
		boolean isRegisterSuccess = userService.register(user);
		
		if (isRegisterSuccess) {
			// send activation email
			String emailMsg = "Congratulations! Please clike this link to activate your account<a href='http://localhost:8080/OnlineStore/active?activeCode="
			+ activeCode + "'>http://localhost:8080/OnlineStore/active?activeCode=" +activeCode + "</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			// register success
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		} else {
			// register fail
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}