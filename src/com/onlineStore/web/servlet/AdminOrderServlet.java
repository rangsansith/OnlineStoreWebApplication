package com.onlineStore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.onlineStore.domain.Order;
import com.onlineStore.domain.OrderItem;
import com.onlineStore.domain.Product;
import com.onlineStore.service.AdminOrderService;

public class AdminOrderServlet extends BaseServlet {
	public void adminOrderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminOrderService adminOrderService = new AdminOrderService();
		List<Order> orderList = adminOrderService.findAllOrders();
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
	}
	
	public void adminFindOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) throws IOException{
		AdminOrderService orderService = new AdminOrderService();
		String oid = request.getParameter("oid");
		List<Map<String, Object>> mapList = orderService.findOrderInfoByOid(oid);
		List<OrderItem> orderItems = new ArrayList<>();
		for (Map<String, Object> map : mapList) {
			OrderItem item = new OrderItem();
			try {
				BeanUtils.populate(item, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			};
			item.setProduct((Product) map.get("product"));
			orderItems.add(item);
		}
		Gson gson = new Gson();
		String json = gson.toJson(orderItems);
		System.out.println(json);
		response.getWriter().write(json);
		
	}

}