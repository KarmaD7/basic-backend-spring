package com.example.demo.dao;

import java.util.Optional;

import com.example.demo.model.EduEntity;

import org.springframework.data.repository.CrudRepository;

public interface EduRepository extends CrudRepository<EduEntity, Integer> {
  public Optional<EduEntity> findByCourseAndEntityName(String course, String entityname);
  public Optional<EduEntity> findByUri(String uri);
}
