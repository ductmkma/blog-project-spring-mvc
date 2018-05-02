package com.zent.dao;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zent.entity.Category;
import com.zent.entity.Tags;
import com.zent.mapper.CategoriesMapper;
import com.zent.mapper.TagsMapper;
import com.zent.utils.Constant;

public class TagsDAO implements ITagsDAO {
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

	public List<Tags> getAll() {
		String sql = "SELECT * FROM tags where deleted_at is null";
		List<Tags> listTags = jdbcTemplateObject.query(sql,new TagsMapper());
		return listTags;
	}

	public List<Tags> searchTags(Tags tag, Integer page) {
		String sql = "SELECT * FROM tags WHERE DELETED_AT is null";
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		Integer count = -1;
		if (tag.getName() != null) {
			count++;
			map.put(count, "%" + tag.getName() + "%");
			sql += " AND name LIKE ? ";
		}

		if (tag.getSlug() != null) {
			count++;
			map.put(count, "%" + tag.getSlug() + "%");
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
		List<Tags> listTags = jdbcTemplateObject.query(sql, args, new TagsMapper());
		return listTags;
	}

	public void insert(Tags tag) {
		// TODO Auto-generated method stub
		
	}

	public void update(Tags tag) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Tags tag) {
		// TODO Auto-generated method stub
		
	}

}
