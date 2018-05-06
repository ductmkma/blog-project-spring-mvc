package com.zent.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zent.entity.Category;
import com.zent.entity.Tags;
import com.zent.mapper.CategoriesMapper;
import com.zent.mapper.TagsMapper;
import com.zent.utils.Constant;
import com.zent.utils.Security;

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
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[3];
		obj[0] = tag.getName();
		obj[1] = tag.getSlug();
		obj[2] = sdf.format(date);
		String sql  = "INSERT INTO tags(name,slug,created_at) values (?,?,?)";
		jdbcTemplateObject.update(sql,obj);
	}

	public void update(Tags tag) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Tags tag) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[2];
		obj[0] = sdf.format(date);
		obj[1] = tag.getId();
		String sql = "UPDATE tags SET DELETED_AT=? WHERE ID = ?";
		jdbcTemplateObject.update(sql,obj);
	}

	public int getMaxIdTag() {
		String sql = "SELECT max(id) FROM tags";
		Integer maxIdTag = (Integer) jdbcTemplateObject.queryForObject(sql, Integer.class);
		return maxIdTag;
	}

	public int getIdBySlug(String slug) {
		String sql = "SELECT id FROM tags where slug='"+slug+"'";
		Integer idBySlug = (Integer) jdbcTemplateObject.queryForObject(sql, Integer.class);
		return idBySlug;
	}

	public List<Tags> getTagsByPost(Integer id) {
		String sql = "SELECT t.id,t.name,t.slug,t.count,t.created_at,t.updated_at,t.deleted_at FROM posts p,tags t,posts_tags pt where p.id= pt.posts_id and t.id = pt.tags_id and pt.deleted_at is null and p.id = ?";
		Object[] obj = new Object[1];
		obj[0] = id;
		List<Tags> listTags = jdbcTemplateObject.query(sql, obj,new TagsMapper());
		return listTags;
	}

}
