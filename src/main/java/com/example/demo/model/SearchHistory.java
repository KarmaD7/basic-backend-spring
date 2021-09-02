package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SearchHistory")
public class SearchHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "search_id", nullable = false)
  private Integer searchId;

  @Column(name = "search_content")
  private String searchContent;

  public SearchHistory() {

  }

  public SearchHistory(String searchContent) {
    this.searchContent = searchContent;
  }

  public Integer getId() {
    return this.searchId;
  }

  public void setId(Integer searchId) {
    this.searchId = searchId;
  }

  public String getContent() {
    return this.searchContent;
  }

  public void setContent(String searchContent) {
    this.searchContent = searchContent;
  }
}
