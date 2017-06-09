package com.onlineStore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.onlineStore.domain.Cart;
import com.onlineStore.domain.CartItem;
import com.onlineStore.domain.Order;
import com.onlineStore.domain.OrderItem;
import com.onlineStore.domain.Product;
import com.onlineStore.domain.User;
import com.onlineStore.service.OrderService;

public class OrderServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		OrderService orderService = new OrderService();
		User user = (User) session.getAttribute("currentUser");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		Cart cart = (Cart) request.getSession().getAttribute("cart");
		Order order = new Order();
		order = turnCartIntoOrder(cart, order);
		order.setUser(user);

		orderService.submitOrder(order);
		session.setAttribute("order", order);
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath() + "/order_info.jsp");

	}

	public void confirmOrder(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Order order = new Order();
		try {
			BeanUtils.populate(order, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		OrderService orderService = new OrderService();
		orderService.updateOrderInfo(order);
	}
	
	public void findMyOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService orderService = new OrderService();
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		String uid = currentUser.getUid();
		List<Order> orderList = orderService.findMyOrders(uid);
		if (orderList != null) {
			for (Order order : orderList) {
				List<Map<String, Object>> orderItemMapList = orderService.findOrderItemByOid(order.getOid());
				for(Map<String, Object> properties : orderItemMapList) {
					OrderItem orderItem = null;
					Product product = null;
					try {
						orderItem = new OrderItem();
						product = new Product();
						BeanUtils.populate(orderItem, properties);
						BeanUtils.populate(product, properties);
						orderItem.setProduct(product);
					} catch (Exception e) {
						e.printStackTrace();
					}
					order.getOrderItems().add(orderItem);
				}
			}
		}
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}
	
	private Order turnCartIntoOrder(Cart cart, Order order) {
		order.setOid(UUID.randomUUID().toString());
		order.setTotal(cart.getTotal());
		order.setState(0);

		List<OrderItem> orderItems = order.getOrderItems();
		Map<String, CartItem> cartItemMap = cart.getCartItemMap();
		Set<Entry<String, CartItem>> cartItemEntrySet = cartItemMap.entrySet();

		for (Entry<String, CartItem> entry : cartItemEntrySet) {
			orderItems.add(turnCartItemIntoOrderItem(entry.getValue(), order));
		}
		order.setOrderItems(orderItems);
		return order;
	}

	private OrderItem turnCartItemIntoOrderItem(CartItem cartItem, Order order) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItemid(UUID.randomUUID().toString());
		orderItem.setOrder(order);
		orderItem.setProduct(cartItem.getProduct());
		orderItem.setCount(cartItem.getNum());
		orderItem.setSubtotal(cartItem.getSubtotal());
		return orderItem;
	}
}