package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.SearchHistory;

import org.springframework.data.repository.CrudRepository;

public interface SearchHistoryRepository extends CrudRepository<SearchHistory, Integer> {
  List<SearchHistory> findByUser_Uid(Integer uid);
}