package com.x.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.x.dao.UserMapper;
import com.x.entity.User;


@Controller
@RequestMapping("go")
public class ControllerLogin {
  
	@Resource
	private UserMapper userMapper;
	
	@RequestMapping("/index.game")
	public String toIndex(){
		return "index";
		
	}
	@RequestMapping("/log.game")
	public String login(User user){
		System.out.println("½øÀ´ÁË");
		userMapper.add(user);
		return "go";
	}	
	
	
	
	
	
	
}
