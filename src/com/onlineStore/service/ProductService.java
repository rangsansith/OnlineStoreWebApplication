package com.onlineStore.service;

import java.sql.SQLException;
import java.util.List;

import com.onlineStore.dao.ProductDao;
import com.onlineStore.domain.Product;


public class ProductService {

	public List<Product> findHotProductList() {
		ProductDao productDao = new ProductDao();
		List<Product> hotProductList = null;
		try {
			hotProductList = productDao.findHotProductList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hotProductList;
	}

	public List<Product> findNewProductList() {
		ProductDao productDao = new ProductDao();
		List<Product> newProductList = null;
		try {
			newProductList = productDao.findNewProductList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newProductList;
	}

	public List<Product> findProductListByCategory(String cid, int index, int currentCount) {
		ProductDao productDao = new ProductDao();
		List<Product> categoryProductList = null;
		try {
			categoryProductList = productDao.findProductListByCategory(cid, index, currentCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categoryProductList;
	}

	public int findCountByCategory(String cid) {
		int count = 0;
		try {
			count = ProductDao.findCountByCategory(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Product findProductByPid(String pid) {
		ProductDao productDao = new ProductDao();
		Product product = null;
		try {
			product = productDao.findProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	
}
