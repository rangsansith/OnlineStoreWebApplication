package com.onlineStore.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.onlineStore.domain.PageBean;
import com.onlineStore.domain.Product;
import com.onlineStore.service.ProductService;

public class FindProductByCategoryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String currentPageStr = request.getParameter("currentPage");
		int currentPage = 1;
		if (currentPageStr != null && !currentPageStr.equals("")) {
			currentPage = Integer.parseInt(currentPageStr);
		}
		PageBean<Product> pageBean = new PageBean<>();
		pageBean.setCurrentPage(currentPage);
		
		int currentCount = 12;
		pageBean.setCurrentCount(currentCount);
		
		ProductService productService = new ProductService();
		int totalCount = productService.findCountByCategory(cid);
		pageBean.setTotalCount(totalCount);
		
		int totalPage = (int) Math.ceil((double)totalCount / currentCount);
		pageBean.setTotalPage(totalPage);
		
		int index = (currentPage - 1) * currentCount;
		List<Product> productList = productService.findProductListByCategory(cid, index, currentCount);
		pageBean.setList(productList);
		
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		
		List<Product> historyList = getHistoryList(request.getCookies(), productService);
		request.setAttribute("historyList", historyList);
		
		request.getRequestDispatcher("product_list.jsp").forward(request, response);
	}

	private List<Product> getHistoryList(Cookie[] cookies, ProductService productService) {
		String[] historyPidList = {};
		for (Cookie cookie :cookies) {
			if ("pid_history".equals(cookie.getName())) {
				historyPidList = cookie.getValue().split("-");
			}
		}
		List<Product> historyList = new ArrayList<>();
		for (String pid : historyPidList) {
			Product product = productService.findProductByPid(pid);
			historyList.add(product);
		}
		return historyList;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}