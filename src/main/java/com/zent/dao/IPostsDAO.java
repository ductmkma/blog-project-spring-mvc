package com.zent.dao;

import java.util.List;

import com.zent.entity.Posts;

public interface IPostsDAO {
	public List<Posts> getAll();
	public List<Posts> search(Posts post,Integer page);
	public void insert(Posts post);
	public void update(Posts post);
	public void deleteTemp(Posts post);
	public void deleted(Posts post);
	public String getPathThumbnail(Integer id);
	
}
