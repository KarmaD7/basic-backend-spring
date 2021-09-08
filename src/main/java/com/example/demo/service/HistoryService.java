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
public class HistoryService {
  @Autowired
  SearchHistoryRepository historyDao;

  @Autowired
  EduRepository eduDao;

  @Autowired
  UserRepository userDao;

  public List<String> getSearchHistory(Integer uid) {
    List<SearchHistory> historyList = historyDao.findByUser_Uid(uid).orElse(null);
    if (historyList == null) {
      return null;
    }
    List<String> historyAsStr = historyList.stream().map(SearchHistory::getContent).collect(Collectors.toList());
    return historyAsStr;
  }

  public List<String> getTopNSearchHistory(Integer uid, Integer number) {
    List<SearchHistory> historyList = historyDao.findTopN(uid, number).orElse(null);
    if (historyList == null) {
      return null;
    }
    List<String> historyAsStr = historyList.stream().map(SearchHistory::getContent).collect(Collectors.toList());
    return historyAsStr;
  }

  public boolean addSearchHistory(Integer uid, String content) {
    SearchHistory searchHistory = new SearchHistory();
    User user = userDao.findById(uid).orElse(null);
    if (user == null) {
      return false;
    }
    searchHistory.setUser(user);
    searchHistory.setContent(content);
    historyDao.save(searchHistory);
    return true;
  }
  public List<EduEntity> getVisitEntityHistory(Integer uid) {
    List<EduEntity> historyList = eduDao.findByVisitUser_Uid(uid).orElse(null);
    if (historyList == null) {
      System.out.println("null history.");
      return null;
    }
    return historyList;
  }

  public boolean addVisitEntityHistory(Integer uid, String uri) {
    try {
      eduDao.setVisitHistory(uid, uri);
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
    return true;
  }
}
