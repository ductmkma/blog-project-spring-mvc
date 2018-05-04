package com.zent.dao;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zent.entity.Posts;
import com.zent.mapper.PostsMapper;
import com.zent.utils.Constant;
public class PostsDAO implements IPostsDAO {
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
	public List<Posts> getAll() {
		String sql = "SELECT p.id,p.title,p.slug,p.thumbnail,p.description,p.status,p.content,p.view_count,c.name,c.id,u.id,u.fullname,p.created_at FROM posts p,categories c, users u WHERE p.categories_id = c.id and p.deleted_at is null and p.user_id = u.id";
		List<Posts> listPost = jdbcTemplateObject.query(sql,new PostsMapper());
		return listPost;
	}

	public List<Posts> search(Posts post, Integer page) {
		String sql = "SELECT p.id,p.title,p.slug,p.thumbnail,p.description,p.status,p.content,p.view_count,c.name,c.id,u.id,u.fullname,p.created_at FROM posts p,categories c, users u WHERE p.categories_id = c.id and p.deleted_at is null and p.user_id = u.id";
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		Integer count = -1;
		if (post.getTitle() != null) {
			count++;
			map.put(count, "%" + post.getTitle() + "%");
			sql += " AND p.title LIKE ? ";
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
		List<Posts> listPost = jdbcTemplateObject.query(sql, args, new PostsMapper());
		return listPost;
	}

	public void insert(Posts post) {
		// TODO Auto-generated method stub
		
	}

	public void update(Posts post) {
		// TODO Auto-generated method stub
		
	}

	public void deleteTemp(Posts post) {
		// TODO Auto-generated method stub
		
	}

	public void deleted(Posts post) {
		// TODO Auto-generated method stub
		
	}

	public String getPathThumbnail(Integer id) {
		Object[] args = new Object[1];
		args[0] = id;
		String sql = "SELECT thumbnail FROM posts WHERE id=?";
		String filename = (String) jdbcTemplateObject.queryForObject(sql, args, String.class);
		return filename;
		
	}
	
}
