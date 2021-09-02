package com.example.demo.dao;

import java.lang.StackWalker.Option;
import java.util.Optional;

import com.example.demo.model.Edu;

import org.springframework.data.repository.CrudRepository;

public interface EduRepository extends CrudRepository<Edu, Integer> {
  public Optional<Edu> findByCourseAndEntityname(String course, String entityname);
  public Optional<Edu> findByUri(String uri);
}
