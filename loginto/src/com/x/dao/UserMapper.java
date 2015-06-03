package com.x.dao;

import java.util.List;

import com.x.entity.User;

public interface UserMapper {
  public List<User> findAll();
  public void add(User user);
}
