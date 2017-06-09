package com.onlineStore.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlineStore.domain.Cart;
import com.onlineStore.domain.CartItem;
import com.onlineStore.domain.Product;
import com.onlineStore.service.CartService;
import com.onlineStore.service.ProductService;

public class CartServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ProductService productService = new ProductService();
		CartService cartService = new CartService();
		
		String pid = request.getParameter("pid");
		Product product = productService.findProductByPid(pid);
				
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));		
		cart = cartService.addProductToCart(cart, product, buyNum);
		
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	public void deleteItemFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CartService cartService = new CartService();
		ProductService productService = new ProductService();
		
		String pid = request.getParameter("pid");		
		Product product = productService.findProductByPid(pid);

		HttpSession session = request.getSession();	
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			cartService.deleteItemFromCart(cart, product);
		} else {
			cart = new Cart();
		}
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
		
	}
	
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath() + "/cart.jsp");		
	}
	
	public void changeItemQuantityInCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CartService cartService = new CartService();
		ProductService productService = new ProductService();
		
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			String pid = request.getParameter("pid");
			String number = request.getParameter("number");
			Product product = productService.findProductByPid(pid);
			cartService.changeItemQuantityInCart(cart, product, number);
		}
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/cart.jsp");		
	}
}