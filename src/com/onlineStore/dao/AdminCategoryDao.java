package com.onlineStore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.onlineStore.domain.Category;
import com.onlineStore.utils.DataSourceUtils;

public class AdminCategoryDao {

	public List<Category> findCategoryList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category;";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	public void editCategoryName(String cid, String cname) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update category set cname = ? where cid = ?;";
		runner.update(sql, cname, cid);
	}

	public void addCategory(String cname, String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into category values(?, ?);";
		runner.update(sql, cid, cname);
	}

	public void deleteCategory(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from category where cid = ?;";
		runner.update(sql, cid);
	}

}
