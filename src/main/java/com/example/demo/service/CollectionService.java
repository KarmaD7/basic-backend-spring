package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.example.demo.dao.EduRepository;
import com.example.demo.dao.SearchHistoryRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.EduEntity;
import com.example.demo.model.SearchHistory;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {
  @Autowired
  EduRepository eduDao;

  @Autowired
  UserRepository userDao;

  public List<EduEntity> getCollectEntityHistory(Integer uid) {
    List<EduEntity> collectionList = eduDao.findByCollectUser_Uid(uid).orElse(null);
    if (collectionList == null) {
      System.out.println("null history.");
      return null;
    }
    return collectionList;
  }

  public boolean addCollectEntityHistory(Integer uid, String uri) {
    try {
      eduDao.setCollection(uid, uri);
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
    return true;
  }

  public boolean deleteCollection(Integer uid, String uri) {
    try {
      eduDao.deleteCollection(uid, uri);
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
    return true;
  }
}
