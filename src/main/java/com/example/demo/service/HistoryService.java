package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.dao.EduRepository;
import com.example.demo.dao.SearchHistoryRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.EduEntity;
import com.example.demo.model.SearchHistory;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
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

  public boolean addSearchHistory(Integer uid, String content) {
    SearchHistory searchHistory = new SearchHistory();
    User user = userDao.findById(uid).orElse(null);
    if (user == null) {
      return false;
    }
    searchHistory.setUser(user);
    searchHistory.setContent(content);
    System.out.println(searchHistory.getContent());
    historyDao.save(searchHistory);
    return true;
  }
  public List<String> getVisitEntityHistory(Integer uid) {
    List<SearchHistory> historyList = historyDao.findByUser_Uid(uid).orElse(null);
    if (historyList == null) {
      return null;
    }
    List<String> historyAsStr = historyList.stream().map(SearchHistory::getContent).collect(Collectors.toList());
    return historyAsStr;
  }
  public List<String> getVisitExerciseHistory(Integer uid) {
    List<SearchHistory> historyList = historyDao.findByUser_Uid(uid).orElse(null);
    if (historyList == null) {
      return null;
    }
    List<String> historyAsStr = historyList.stream().map(SearchHistory::getContent).collect(Collectors.toList());
    return historyAsStr;
  }
}
