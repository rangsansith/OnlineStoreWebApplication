package com.onlineStore.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.onlineStore.dao.OrderDao;
import com.onlineStore.domain.Order;
import com.onlineStore.domain.OrderItem;
import com.onlineStore.utils.DataSourceUtils;

public class OrderService {

	public void submitOrder(Order order) {

		OrderDao orderDao = new OrderDao();
		try {
			DataSourceUtils.startTransaction();			
			orderDao.addOrder(order);
			for (OrderItem orderItem : order.getOrderItems()) {
				orderDao.addOrderItem(orderItem);
			}
		} catch (SQLException e) {
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateOrderInfo(Order order) {
		OrderDao orderDao = new OrderDao();
		try {
			orderDao.updateOrderInfo(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Order> findMyOrders(String uid) {
		OrderDao orderDao = new OrderDao();
		List<Order> orderList = null;
		try {
			orderList = orderDao.findMyOrders(uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
	}

	public List<Map<String, Object>> findOrderItemByOid(String oid) {
		OrderDao orderDao = new OrderDao();
		List<Map<String, Object>> orderItemMapList = null;
		try {
			orderItemMapList = orderDao.findOrderItemByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderItemMapList;
	}

}
