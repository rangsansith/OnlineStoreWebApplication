package com.onlineStore.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlineStore.domain.Product;
import com.onlineStore.service.ProductService;

public class IndexProductServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService productService = new ProductService();
		List<Product> hotProductList = productService.findHotProductList();
		List<Product> newProductList = productService.findNewProductList();
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}