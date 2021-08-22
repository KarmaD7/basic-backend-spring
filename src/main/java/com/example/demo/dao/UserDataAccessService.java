package com.example.demo.dao;

import com.example.demo.model.User;

public class UserDataAccessService implements UserDao {
  public int createUser(User user) {
    return 1;
  }

  public User getUserByName(String name) {
    return new User("1", "1", "1");
  }

  public User getUserByEmail(String email) {
    return new User("1", "1", "1");
  }

  public User setUserPassword(String newpwd) {
    return new User("1", "1", "1");
  }
}
