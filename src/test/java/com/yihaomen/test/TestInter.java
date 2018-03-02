package com.yihaomen.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yihaomen.mybatis.model.Article;
import com.yihaomen.mybatis.model.User;
import com.yihaomen.mybatis.model.UserMapper;

public class TestInter {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	static {
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	public void getUserList(String userName){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	UserMapper userOperation=session.getMapper(UserMapper.class);          
            List<User> users = userOperation.selectUsers(userName);
            for(User user:users){
                System.out.println(user.getId()+":"+user.getUserName()+":"+user.getUserAddress());
            }
            
        } finally {
            session.close();
        }
    }
	
	public void selectUserByID(){
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userOperation=session.getMapper(UserMapper.class);
			User user = userOperation.selectUserByID(1);
			System.out.println(user.getUserAddress());
			System.out.println(user.getUserName());
		} finally {
			session.close();
		}
	}
	
	/**
     * 测试增加,增加后，必须提交事务，否则不会写入到数据库.
     */
    public void addUser(){
        User user=new User();
        user.setUserAddress("人民广场");
        user.setUserName("飞鸟");
        user.setUserAge("80");
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper userOperation=session.getMapper(UserMapper.class);
            userOperation.addUser(user);
            session.commit();
            System.out.println("当前增加的用户 id为:"+user.getId());
        } finally {
            session.close();
        }
    }
    
    public void updateUser(){
        //先得到用户,然后修改，提交。
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper userOperation=session.getMapper(UserMapper.class);
            User user = userOperation.selectUserByID(2);            
            user.setUserAddress("原来是魔都的浦东创新园区");
            userOperation.updateUser(user);
            session.commit();
            
        } finally {
            session.close();
        }
    }
    
    /**
     * 删除数据，删除一定要 commit.
     * @param id
     */
    public void deleteUser(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper userOperation=session.getMapper(UserMapper.class);          
            userOperation.deleteUser(id);
            session.commit();            
        } finally {
            session.close();
        }
    }
    
    public void getUserArticles(int userid){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	UserMapper userOperation=session.getMapper(UserMapper.class);          
            List<Article> articles = userOperation.getUserArticles(userid);
            for(Article article:articles){
                System.out.println(article.getTitle()+":"+article.getContent()+
                        ":作者是:"+article.getUser().getUserName()+":地址:"+
                         article.getUser().getUserAddress());
            }
        } finally {
            session.close();
        }
    }
    public void getUserArticles2(int userid){
    	SqlSession session = sqlSessionFactory.openSession();
    	try {
    		UserMapper userOperation=session.getMapper(UserMapper.class);          
    		List<Article> articles = userOperation.getUserArticles2(userid);
    		for(Article article:articles){
    			System.out.println(article.getTitle()+":"+article.getContent()+
    					":作者是:"+article.getUser().getUserName()+":地址:"+
    					article.getUser().getUserAddress());
    		}
    	} finally {
    		session.close();
    	}
    }
	
	public static void main(String[] args) {
		TestInter testUser=new TestInter();
//		testUser.selectUserByID();
//        testUser.getUserList("%");
//        testUser.addUser();
//        testUser.updateUser();
        //testUser.deleteUser(2);
		testUser.getUserArticles(1);
		testUser.getUserArticles2(1);
	}
}
