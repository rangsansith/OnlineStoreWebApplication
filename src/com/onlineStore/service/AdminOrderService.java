package com.onlineStore.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.onlineStore.dao.AdminOrderDao;
import com.onlineStore.domain.Order;

public class AdminOrderService {

	public List<Order> findAllOrders() {
		AdminOrderDao adminOrderDao = new AdminOrderDao();
		List<Order> orders = null;
		try {
			orders = adminOrderDao.findAllOrders();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		AdminOrderDao adminOrderDao = new AdminOrderDao();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = adminOrderDao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}

}
