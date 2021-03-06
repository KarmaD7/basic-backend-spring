package com.example.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "User")
public class User {
  @Id
  @Column(name = "uid", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer uid;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "phone", unique = true, nullable = false)
  private String phone;

  @Column(name = "password", nullable = false)
  private String password;

  public User() {

  }

  public User(String name, String phone, String pwd) {
    this.name = name;
    this.phone = phone;
    this.password = pwd;
  }

  public Integer getId() {
    return uid;
  }

  public void setId(Integer id) {
    this.uid = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @ManyToMany
  @JoinTable(name = "user_visit_entity", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "eid"))
  private Set<EduEntity> visitEntity;

  @ManyToMany
  @JoinTable(name = "user_collect_entity", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "eid"))
  private Set<EduEntity> collectEntity;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private Set<SearchHistory> searchEntity;
}