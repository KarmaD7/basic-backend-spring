package com.example.demo.dao;

import com.example.demo.model.User;

public interface UserDao {
  int createUser(User user);

  User getUserByName(String name);

  User getUserByEmail(String email);

  User setUserPassword(String newpwd);
}
