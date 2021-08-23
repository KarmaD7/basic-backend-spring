package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// enum UserOpStatus {
//   SUCCESS,
//   INVALID_NAME,
//   INVALID_EMAIL,
//   INVALID_PWD,
//   WRONG_NAME,
//   WRONG_EMAIL,
//   WRONG_PWD
// }

@Service
public class UserService {
  private UserRepository userDao;

  @Autowired
  public UserService(UserRepository userDao) {
    this.userDao = userDao;
  }

  public User getUser(String nameOrEmail, String pwd, int mode) {
    User user;
    if (mode == 0) {
      user = userDao.findByName(nameOrEmail);
    } else {
      user = userDao.findByEmail(nameOrEmail);
    }
    if (user.getPassword() != pwd) {
      return null;
    }
    return user;
  }
  public boolean createUser(String name, String Email, String pwd) {
    return true;
  }
  public boolean changepassword(int id, String pwd) {
    return true;
  }

}
