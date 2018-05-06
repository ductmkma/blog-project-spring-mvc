package com.zent.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zent.entity.PostsTags;
import com.zent.entity.Tags;
import com.zent.mapper.PostsTagsMapper;
import com.zent.mapper.TagsMapper;

public class PostsTagsDAO implements IPostsTagsDAO{
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
	public void insert(PostsTags postsTags) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[3];
		obj[0] = postsTags.getPostsId();
		obj[1] = postsTags.getTagsId();
		obj[2] = sdf.format(date);
		String sql  = "INSERT INTO posts_tags(posts_id,tags_id,created_at) values (?,?,?)";
		jdbcTemplateObject.update(sql,obj);
		
	}

	public List<PostsTags> getAllTagsByPostId(Integer id) {
		String sql = "select * from posts_tags where deleted_at is null and posts_id ="+id;
		List<PostsTags> listPostsTags = jdbcTemplateObject.query(sql,new PostsTagsMapper());
		return listPostsTags;
	}

	public void deletePostsTags(PostsTags postsTags) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[3];
		obj[0] = sdf.format(date);
		obj[1] = postsTags.getPostsId();
		obj[2] = postsTags.getTagsId();
		String sql = "UPDATE posts_tags SET DELETED_AT=? WHERE posts_id = ? and tags_id=?";
		jdbcTemplateObject.update(sql,obj);
		
	}

	
	
	
}
