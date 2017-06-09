package com.onlineStore.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.onlineStore.domain.User;
import com.onlineStore.utils.DataSourceUtils;

public class UserDao {

	public int register(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return runner.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getTelephone(), user.getBirthday(), user.getGender(), user.getState(),user.getCode());
	}

	public Long checkUsername(String username) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from user where username = ?";
		Long query = (Long) runner.query(sql, new ScalarHandler(), username);
		return query;
	}

	public User login(String username, String password) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ?;";
		User user = runner.query(sql, new BeanHandler<User>(User.class), username, password);
		return user;
	}

}
