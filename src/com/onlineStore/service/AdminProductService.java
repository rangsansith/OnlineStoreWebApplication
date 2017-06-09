package com.onlineStore.service;

import java.sql.SQLException;
import java.util.List;

import com.onlineStore.dao.AdminProductDao;
import com.onlineStore.domain.Product;

public class AdminProductService {

	public List<Product> adminProductList() {
		AdminProductDao adminProductDao = new AdminProductDao();
		List<Product> productList = null;
		try {
			productList = adminProductDao.adminProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public void deleteProductById(String pid) {
		AdminProductDao adminProductDao = new AdminProductDao();
		try {
			adminProductDao.deleteProductById(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addProduct(Product product) {
		AdminProductDao adminProductDao = new AdminProductDao();
		try {
			adminProductDao.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateProduct(Product product) {
		AdminProductDao adminProductDao = new AdminProductDao();
		try {
			adminProductDao.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

}
