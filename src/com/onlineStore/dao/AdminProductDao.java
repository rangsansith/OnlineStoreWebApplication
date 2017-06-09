package com.onlineStore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.onlineStore.domain.Product;
import com.onlineStore.utils.DataSourceUtils;

public class AdminProductDao {

	public List<Product> adminProductList() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	public void deleteProductById(String pid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid = ?";
		queryRunner.update(sql, pid);
	}

	public void addProduct(Product product) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product value(?,?,?,?,?,?,?,?,?,?)";
		queryRunner.update(sql, product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(), product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(), product.getCid());		
	}

	public void updateProduct(Product product) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname = ?, market_price = ?, shop_price = ?, pimage = ?, is_hot = ?, pdesc = ?, cid = ? where pid = ?";
		queryRunner.update(sql, product.getPname(), product.getMarket_price(), product.getShop_price(), product.getPimage(), product.getIs_hot(), product.getPdesc(), product.getCid(), product.getPid());
	}

}
