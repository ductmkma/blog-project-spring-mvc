package com.zent.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zent.entity.User;
import com.zent.mapper.UserMapper;
import com.zent.utils.Constant;
import com.zent.utils.Security;


public class UserDAO implements IUserDAO {
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

	public List<User> getAll() {
		String sql = "SELECT * FROM users where deleted_at is null";
		List<User> listUser = jdbcTemplateObject.query(sql,new UserMapper());
		return listUser;
	}

	public List<User> search(User user, Integer page) {
		String sql = "SELECT * FROM users WHERE DELETED_AT is null";
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		Integer count = -1;
		if (user.getFullname() != null) {
			count++;
			map.put(count, "%" + user.getFullname() + "%");
			sql += " AND FULLNAME LIKE ? ";
		}

		if (user.getEmail() != null) {
			count++;
			map.put(count, "%" + user.getEmail() + "%");
			sql += " AND email LIKE ? ";
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
		List<User> users = jdbcTemplateObject.query(sql, args, new UserMapper());
		return users;
	}

	public void insert(User user) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[8];
		obj[0] = user.getFullname();
		obj[1] = user.getEmail();
		String email = user.getEmail();
		String arr[] = email.split("\\@");
		obj[2] = new Security().md5(arr[0]);
		obj[3] = user.getGroupId();
		obj[4] = 1;//status
		obj[5] = user.getPhone();
		obj[6] = user.getAvatar();
		obj[7] = sdf.format(date);
		String sql  = "INSERT INTO users(fullname,email,password,group_id,status,phone,avatar,created_at) values (?,?,?,?,?,?,?,?)";
		jdbcTemplateObject.update(sql,obj);
		
	}

	public void update(User user) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[8];
		obj[0] = user.getFullname();
		obj[1] = user.getEmail();
		String email = user.getEmail();
		String arr[] = email.split("\\@");
		obj[2] = new Security().md5(arr[0]);//password
		obj[3] = user.getGroupId();
		obj[4] = user.getPhone();
		obj[5] = user.getAvatar();
		obj[6] = sdf.format(date);
		obj[7] = user.getId();
		String sql = "UPDATE users SET FULLNAME = ?,EMAIL=?,PASSWORD=?,GROUP_ID=?,PHONE=?,AVATAR=?,UPDATED_AT=? WHERE ID = ?";
		jdbcTemplateObject.update(sql,obj);
		
	}

	public void delete(User user) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Object[] obj = new Object[2];
		obj[0] = sdf.format(date);
		obj[1] = user.getId();
		String sql = "UPDATE users SET DELETED_AT=? WHERE ID = ?";
		jdbcTemplateObject.update(sql,obj);
		
	}
	public boolean checkLogin(User user) {
		Object[] args = new Object[2];
		args[0] = user.getEmail();
		args[1] = new Security().md5(user.getPassword());
		String sql = "SELECT * FROM users WHERE group_id=1 and email=? and password=? ";
		List<User> users = jdbcTemplateObject.query(sql, args, new UserMapper());
		if (!users.isEmpty()) {
			return true; 
		}
		return false;
	}

	public String getFullName(User user) {
		Object[] args = new Object[2];
		args[0] = user.getEmail();
		args[1] = new Security().md5(user.getPassword());
		String sql = "SELECT fullname FROM users WHERE email=? and password=?";
		String fullname = (String) jdbcTemplateObject.queryForObject(sql, args, String.class);
		return fullname;
	}

	public String getPathAvata(Integer id) {
		Object[] args = new Object[1];
		args[0] = id;
		String sql = "SELECT avatar FROM users WHERE id=?";
		String filename = (String) jdbcTemplateObject.queryForObject(sql, args, String.class);
		return filename;
	}

	public int getUserId(User user) {
		Object[] args = new Object[2];
		args[0] = user.getEmail();
		args[1] = new Security().md5(user.getPassword());
		String sql = "SELECT id FROM users WHERE email=? and password=?";
		Integer userId = (Integer) jdbcTemplateObject.queryForObject(sql, args, Integer.class);
		return userId;
	}

	
}
