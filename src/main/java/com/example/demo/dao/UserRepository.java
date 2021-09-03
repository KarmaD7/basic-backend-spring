package com.example.demo.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import com.example.demo.model.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>  {
  Optional<User> findByName(String name);
  Optional<User> findByEmail(String email);

  @Transactional
  @Modifying
  @Query("update User u set u.password = ?2 where u.uid = ?1")
  void setUserPassword(Integer id, String newpwd);
}
