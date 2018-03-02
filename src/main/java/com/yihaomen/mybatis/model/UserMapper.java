package com.yihaomen.mybatis.model;

import java.util.List;

public interface UserMapper {
	public User selectUserByID(int id);
	
	public List<User> selectUsers(String userName);
	
	
	public void addUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUser(int id);
	
	List<Article> getUserArticles(int userid);
	List<Article> getUserArticles2(int userid);
	
}
