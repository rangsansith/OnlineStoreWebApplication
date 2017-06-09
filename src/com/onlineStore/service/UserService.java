package com.onlineStore.service;

import java.sql.SQLException;

import com.onlineStore.dao.UserDao;
import com.onlineStore.domain.User;

public class UserService {

	public boolean register(User user) {
		
		UserDao userDao = new UserDao();
		int rowNumber = 0;
		try {
			rowNumber = userDao.register(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowNumber > 0 ? true : false;
	}

	public boolean checkUsername(String username) {
		UserDao userDao = new UserDao();
		Long isExist = 0L;
		try {
			isExist = userDao.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isExist > 0 ? true : false;
	}

	public User login(String username, String password) {
		UserDao userDao = new UserDao();
		User user = null;
		try {
			user = userDao.login(username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return user;
	}

	
}
