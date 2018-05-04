package com.zent.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zent.entity.Posts;

public class PostsMapper implements RowMapper<Posts>{

	public Posts mapRow(ResultSet rs, int numRow) throws SQLException {
		Posts post = new Posts();
		post.setId(rs.getInt("p.id"));
		post.setTitle(rs.getString("p.title"));
		post.setSlug(rs.getString("p.slug"));
		post.setNameThumbnail(rs.getString("p.thumbnail"));
		post.setDescription(rs.getString("p.description"));
		post.setContent(rs.getString("p.content"));
		post.setStatus(rs.getInt("p.status"));
		post.setUserId(rs.getInt("u.id"));
		post.setUsername(rs.getString("u.fullname"));
		post.setCategoriesId(rs.getInt("c.id"));
		post.setCategoriesName(rs.getString("c.name"));
		post.setViewCount(rs.getInt("p.view_count"));
		post.setCreatedAt(rs.getTimestamp("p.created_at"));
		return post;
	}

}
