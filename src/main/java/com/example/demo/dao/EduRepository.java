package com.example.demo.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import com.example.demo.model.EduEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EduRepository extends CrudRepository<EduEntity, Integer> {
  public Optional<EduEntity> findByCourseAndEntityName(String course, String entityname);
  public Optional<EduEntity> findByUri(String uri);
  public Optional<List<EduEntity>> findByVisitUser_Uid(Integer uid);
  public Optional<List<EduEntity>> findByCollectUser_Uid(Integer uid);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO user_visit_entity(uid, eid)" 
  + " select user.uid as uid, edu_entity.eid as eid"
  + " FROM user, edu_entity"
  + " WHERE user.uid = ?1 and edu_entity.uri = ?2", nativeQuery = true)
  public void setVisitHistory(Integer uid, String uri);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO user_collect_entity(uid, eid)" 
  + " select user.uid as uid, edu_entity.eid as eid"
  + " FROM user, edu_entity"
  + " WHERE user.uid = ?1 and edu_entity.uri = ?2", nativeQuery = true)
  public void setCollection(Integer uid, String uri);
}
