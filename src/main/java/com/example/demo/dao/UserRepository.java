package com.example.demo.dao;

import com.example.demo.model.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>  {
  User findByName(String name);
  User findByEmail(String email);

  @Modifying
  @Query("update User u set u.password = ?2 where u.id = ?1")
  User setUserPassword(Integer id, String newpwd);
}
