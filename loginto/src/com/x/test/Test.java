package com.x.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.x.dao.UserMapper;
import com.x.entity.User;

public class Test {
		public static void main(String[] args) {
			
		}
	String cfg="applicationContext.xml";
	ApplicationContext ctx=new ClassPathXmlApplicationContext(cfg);
	private UserMapper userMapper=ctx.getBean("userMapper",UserMapper.class);
	@org.junit.Test
	public void testConnect(){
		User user=new User();
		user.setUserName("admin");
		user.setPassWord("123456");
		userMapper.add(user);
	  List<User>  users= userMapper.findAll();	
	    for(User u:users){
	    	System.out.println(u);
	    }
	}
	
	
}
