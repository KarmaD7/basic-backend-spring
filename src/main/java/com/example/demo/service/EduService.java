package com.example.demo.service;

import com.example.demo.dao.EduRepository;
import com.example.demo.model.EduEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EduService {
  @Autowired
  private EduRepository eduDao;

  public EduEntity getEduEntityByUri(String uri) {
    return eduDao.findByUri(uri).orElse(null);
  }

  public EduEntity getEduEntityByCourseAndName(String course, String name) {
    return eduDao.findByCourseAndEntityName(course, name).orElse(null);
  }
  
  public void saveEduEntity(EduEntity entity) {
    eduDao.save(entity);
  }
}
