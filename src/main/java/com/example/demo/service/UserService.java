package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;

// enum UserOpStatus {
//   SUCCESS,
//   INVALID_NAME,
//   INVALID_EMAIL,
//   INVALID_PWD,
//   WRONG_NAME,
//   WRONG_EMAIL,
//   WRONG_PWD
// }


public class UserService {
  private final UserDao userDao;
  UserService(UserDao userDao) {
    this.userDao = userDao;
  }
  public User getUser(String nameOrEmail, String pwd, int mode) {
    /** 
    /* @param 
      
    */
    return new User();
  }
  public boolean createUser(String name, String Email, String pwd) {
    return true;
  }
  public boolean changepassword(int id, String pwd) {
    return true;
  }

}
