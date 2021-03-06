package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "EduEntity"
/*, uniqueConstraints = @UniqueConstraint(
  columnNames = {"course, entity_name"}
)*/)
public class EduEntity {
  @Id
  @Column(name = "eid")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "course", nullable = false)
  private String course;

  @Column(name = "entity_name", nullable = false)
  private String entityName;

  @Column(name = "uri", unique = true, nullable = true)
  private String uri; // uri for entity, id for exercise
  
  @Column(name = "entity", nullable = false)
  private Boolean isEntity;

  public EduEntity() {

  }

  public EduEntity(String course, String entityName, String uri, Boolean isEntity) {
    this.course = course;
    this.entityName = entityName;
    this.uri = uri;
    this.isEntity = isEntity;
  }

  public Integer getId() {
    return this.id;
  }

  public String getCourse() {
    return this.course;
  }

  public String getName() {
    return this.entityName;
  }

  public String getUri() {
    return this.uri;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setCourse(String course) {
    this.course = course;
  }

  public void setName(String name) {
    this.entityName = name;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public Boolean getIsEntity() {
    return this.isEntity;
  }

  public void setIsEntity(boolean isEntity) {
    this.isEntity = isEntity;
  }

  @ManyToMany(mappedBy = "visitEntity")
  private Set<User> visitUser;

  @ManyToMany(mappedBy = "collectEntity")
  private Set<User> collectUser;

  public Map<String, Object> toMap() {
    Map<String, Object> eduMap = new HashMap<String, Object>();
    eduMap.put("course", course);
    eduMap.put("name", entityName);
    eduMap.put("uri", uri);
    return eduMap;
  }
}


