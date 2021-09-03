package com.example.demo.service;

import java.util.List;

import com.example.demo.dao.EduRepository;
import com.example.demo.dao.SearchHistoryRepository;
import com.example.demo.model.EduEntity;
import com.example.demo.model.SearchHistory;

import org.springframework.beans.factory.annotation.Autowired;


public class HistoryService {
  @Autowired
  SearchHistoryRepository historyDao;

  @Autowired
  EduRepository eduDao;

  public List<String> getSearchHistory(Integer uid) {
    return null;
  }

  public boolean addSearchHistory(Integer uid, String content) {
    return true;
  }
}
