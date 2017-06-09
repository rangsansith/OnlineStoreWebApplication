package com.onlineStore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.onlineStore.domain.Category;
import com.onlineStore.utils.DataSourceUtils;

public class CategoryDao {

	public List<Category> findAllCategories() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category;";
		return queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
	}

}
