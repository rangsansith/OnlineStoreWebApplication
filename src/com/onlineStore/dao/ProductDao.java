package com.onlineStore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.onlineStore.domain.Product;
import com.onlineStore.utils.DataSourceUtils;

public class ProductDao {

	public List<Product> findHotProductList() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = ? limit ? , ?;";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);
	}

	public List<Product> findNewProductList() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ? , ?;";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);	
	}

	public List<Product> findProductListByCategory(String cid, int index, int currentCount) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? limit ? , ?;";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid, index, currentCount);
	}

	public static int findCountByCategory(String cid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid = ?;";
		Long query = (Long) queryRunner.query(sql, new ScalarHandler(), cid);
		return query.intValue();
	}

	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ?;";
		return queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}
	
}
