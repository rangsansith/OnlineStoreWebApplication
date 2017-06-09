package com.onlineStore.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.onlineStore.domain.Order;
import com.onlineStore.domain.OrderItem;
import com.onlineStore.domain.Product;
import com.onlineStore.utils.DataSourceUtils;

public class AdminOrderDao {

	public List<Order> findAllOrders() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders";
		return queryRunner.query(sql, new BeanListHandler<Order>(Order.class));
	}
	
	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		List<Map<String, Object>> mapList = findOrderItemByOid(oid);
		String sql = "select * from product where pid = ?";
		for (Map<String, Object> map : mapList) {
			String pid = (String) map.get("pid");
			QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
			Product product = queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
			map.put("product", product);
		}
		return mapList;

	}
	
	public List<Map<String, Object>> findOrderItemByOid(String oid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orderitem where oid = ?";
		return queryRunner.query(sql, new MapListHandler(), oid);
	}
}
