package com.onlineStore.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlineStore.domain.Product;
import com.onlineStore.service.ProductService;

public class ProductInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		ProductService productService = new ProductService();
		Product product = productService.findProductByPid(pid);
		request.setAttribute("product", product);
		
		String cid = request.getParameter("cid");
		request.setAttribute("cid", cid);
		
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr != null) {
			int currentPage = Integer.parseInt(currentPageStr);
			request.setAttribute("currentPage", currentPage);
		}
		
		String historyCookieStr = generateHistoryCookie(request, product);
		Cookie historyCookie = new Cookie("pid_history", historyCookieStr);
		historyCookie.setMaxAge(24*60*60);
		response.addCookie(historyCookie);
		request.getRequestDispatcher("product_info.jsp").forward(request, response);
	}

	private String generateHistoryCookie(HttpServletRequest request, Product product) {
		String[] historyList = {};
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("pid_history")) {
				historyList = cookie.getValue().split("-");
			}
		}
		List<String> asList = Arrays.asList(historyList);
		LinkedList<String> historyLinkedList = new LinkedList<>(asList);
		if (historyLinkedList.contains(product.getPid())) {
			historyLinkedList.remove(product.getPid());
		}
		historyLinkedList.addFirst(product.getPid());
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < historyLinkedList.size() && i < 7; i++) {
			sBuffer.append(historyLinkedList.get(i) + "-");
		}
		return sBuffer.substring(0, sBuffer.length() - 1);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}