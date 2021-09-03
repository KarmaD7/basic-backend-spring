package com.example.demo.dao;

import java.util.Optional;

import com.example.demo.model.EduEntity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EduRepository extends CrudRepository<EduEntity, Integer> {
  public Optional<EduEntity> findByCourseAndEntityName(String course, String entityname);
  public Optional<EduEntity> findByUri(String uri);
  public List<EduEntity> findByVisitUser_Uid(Integer uid);
  public List<EduEntity> findByCollectUser_Uid(Integer uid);
}
