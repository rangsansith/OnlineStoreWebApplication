package com.onlineStore.service;

import java.sql.SQLException;
import java.util.List;

import com.onlineStore.dao.AdminCategoryDao;
import com.onlineStore.domain.Category;

public class AdminCategoryService {

	public List<Category> findCategoryList() {
		AdminCategoryDao adminCategoryDao = new AdminCategoryDao();
		List<Category> categoryList = null;
		try {
			categoryList = adminCategoryDao.findCategoryList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	public void editCategoryName(String cid, String cname) {
		AdminCategoryDao adminCategoryDao = new AdminCategoryDao();
		try {
			adminCategoryDao.editCategoryName(cid, cname);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addCategory(String cname, String cid) {
		AdminCategoryDao adminCategoryDao = new AdminCategoryDao();
		try {
			adminCategoryDao.addCategory(cname, cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCategory(String cid) throws SQLException {
		AdminCategoryDao adminCategoryDao = new AdminCategoryDao();
		adminCategoryDao.deleteCategory(cid);
	}
}
