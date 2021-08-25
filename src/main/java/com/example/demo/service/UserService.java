package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import com.example.demo.utils.RegisterStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
      user = userDao.findByName(nameOrEmail).orElse(null);
    } else {
      user = userDao.findByEmail(nameOrEmail).orElse(null);
    }
    if (user == null || !user.getPassword().equals(pwd)) {
      return null;
    }
    return user;
  }
  public RegisterStatus createUser(String name, String email, String pwd) {
    User user = new User(name, email, pwd);
    User userByName = userDao.findByName(name).orElse(null);
    if (userByName != null) {
      return RegisterStatus.duplicated_name;
    }    
    User userByEmail = userDao.findByEmail(email).orElse(null);
    if (userByEmail != null) {
      return RegisterStatus.duplicated_email;
    }    
    userDao.save(user);
    return RegisterStatus.success;
  }
  public boolean changepassword(int id, String oldpwd, String newpwd) {
    User user = userDao.findById(id).orElse(null);
    if (user == null) return false;
    if (!user.getPassword().equals(oldpwd)) return false;
    userDao.setUserPassword(id, newpwd);   
    return true;
  }

}
