package com.zent.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zent.entity.Category;
import com.zent.entity.User;
import com.zent.mapper.CategoriesMapper;
import com.zent.mapper.CategoriesMostUsedMapper;
import com.zent.mapper.UserMapper;
import com.zent.utils.Constant;
import com.zent.utils.Security;

public class CategoriesDAO implements ICategoriesDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	

	public DataSource getDataSoure() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}



	public JdbcTemplate getJdbcTemplateObject() {
		return jdbcTemplateObject;
	}



	public void setJdbcTemplateObject(JdbcTemplate jdbcTemplateObject) {
		this.jdbcTemplateObject = jdbcTemplateObject;
	}

	public List<Category> getAll() {
		String sql = "SELECT * FROM categories where deleted_at is null";
		List<Category> listCate = jdbcTemplateObject.query(sql,new CategoriesMapper());
		return listCate;
	}

	public List<Category> search(Category cate, Integer page) {
		String sql = "SELECT * FROM categories WHERE DELETED_AT is null";
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		Integer count = -1;
		if (cate.getName() != null) {
			count++;
			map.put(count, "%" + cate.getName() + "%");
			sql += " AND name LIKE ? ";
		}

		if (cate.getSlug() != null) {
			count++;
			map.put(count, "%" + cate.getSlug() + "%");
			sql += " AND slug LIKE ? ";
		}

		Object[] args = new Object[count + 1];

		if (count < 0)
			args = new Object[] {};
		else {
			for (Integer key : map.keySet()) {
				args[key] = map.get(key);
			}
		}

		sql += "LIMIT " + (page-1) * Constant.pageSize + "," + Constant.pageSize;
		List<Category> listCate = jdbcTemplateObject.query(sql, args, new CategoriesMapper());
		return listCate;
	}

	public void insert(Category cate) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[5];
		obj[0] = cate.getName();
		obj[1] = cate.getSlug();
		obj[2] = cate.getDescription();
		obj[3] = cate.getStatus();
		obj[4] = sdf.format(date);
		String sql  = "INSERT INTO categories(name,slug,description,status,created_at) values (?,?,?,?,?)";
		jdbcTemplateObject.update(sql,obj);
		
	}

	public void update(Category cate) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[6];
		obj[0] = cate.getName();
		obj[1] = cate.getSlug();
		obj[2] = cate.getDescription();
		obj[3] = cate.getStatus();
		obj[4] = sdf.format(date);
		obj[5] = cate.getId();
		String sql = "UPDATE categories SET NAME = ?,SLUG=?,DESCRIPTION=?,STATUS=?,UPDATED_AT=? WHERE ID = ?";
		jdbcTemplateObject.update(sql,obj);
		
	}

	public void delete(Category cate) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[2];
		obj[0] = sdf.format(date);
		obj[1] = cate.getId();
		String sql = "UPDATE categories SET DELETED_AT=? WHERE ID = ?";
		jdbcTemplateObject.update(sql,obj);
		
	}

	public Category getCategory(Integer id) {
		Category cate = new Category();
		Object[] obj = new Object[1];
		obj[0] = id;
		String sql = "SELECT * FROM categories WHERE ID = ?";
		cate = jdbcTemplateObject.queryForObject(sql, new CategoriesMapper(), obj);
		return cate;
	}

	public List<Category> getMostUserCate() {
		String sql = "SELECT c.id,c.name,count(c.id) dem FROM posts p, categories c where p.categories_id = c.id GROUP BY c.id HAVING dem>5";
		List<Category> listCate = jdbcTemplateObject.query(sql,new CategoriesMostUsedMapper());
		return listCate;
	}
}
