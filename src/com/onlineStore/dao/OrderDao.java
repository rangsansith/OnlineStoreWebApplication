package com.onlineStore.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.onlineStore.domain.Order;
import com.onlineStore.domain.OrderItem;
import com.onlineStore.utils.DataSourceUtils;

public class OrderDao {

	public void addOrder(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner();
		Connection conn = DataSourceUtils.getConnection();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?);";
		runner.update(conn, sql, order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid());
	}

	public void addOrderItem(OrderItem orderItem) throws SQLException {
		QueryRunner runner = new QueryRunner();
		Connection conn = DataSourceUtils.getConnection();
		String sql = "insert into orderitem values(?,?,?,?,?);";
		runner.update(conn, sql, orderItem.getItemid(), orderItem.getCount(), orderItem.getSubtotal(), orderItem.getProduct().getPid(), orderItem.getOrder().getOid());
	}

	public void updateOrderInfo(Order order) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set address = ?, name = ?, telephone = ? where oid = ?;";
		queryRunner.update(sql, order.getAddress(), order.getName(), order.getTelephone(), order.getOid());
	}

	public List<Order> findMyOrders(String uid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid = ?;";
		return queryRunner.query(sql, new BeanListHandler<Order>(Order.class), uid);
	}

	public List<Map<String, Object>> findOrderItemByOid(String oid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select p.pimage, p.pname, p.shop_price, o.count, o.subtotal, o.pid from product p, orderitem o where p.pid = o.pid and o.oid = ?;";
		List<Map<String, Object>> orderItemMapList = queryRunner.query(sql, new MapListHandler(), oid);
		return orderItemMapList;
	} 

}
