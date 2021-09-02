package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SearchHistory")
public class SearchHistory {
  @Id
  @Column(name = "search_id", nullable = false)
  private Integer searchId;
}
