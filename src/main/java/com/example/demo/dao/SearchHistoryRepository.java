package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.SearchHistory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SearchHistoryRepository extends CrudRepository<SearchHistory, Integer> {
  Optional<List<SearchHistory>> findByUser_Uid(Integer uid);

  @Query(value = "SELECT * FROM search_history WHERE uid = ?1 ORDER BY create_time LIMIT ?2", nativeQuery = true)
  Optional<List<SearchHistory>> findTopN(Integer uid, Integer number);
}
