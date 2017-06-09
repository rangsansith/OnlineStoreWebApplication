package com.onlineStore.service;

import java.sql.SQLException;
import java.util.List;

import com.onlineStore.dao.CategoryDao;
import com.onlineStore.domain.Category;

public class CategoryService {

	public List<Category> findAllCategories() {
		CategoryDao categoryDao = new CategoryDao();
		List<Category> categoryList = null;
		try {
			categoryList = categoryDao.findAllCategories();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

}
