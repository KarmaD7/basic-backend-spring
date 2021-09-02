package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "EduEntity"
/*, uniqueConstraints = @UniqueConstraint(
  columnNames = {"course, entity_name"}
)*/)
public class EduEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "course", nullable = false)
  private String course;

  @Column(name = "entity_name", nullable = false)
  private String entityName;

  @Column(name = "uri", unique = true, nullable = true)
  private String uri; // uri for entity, id for exercise

  public EduEntity() {

  }

  public EduEntity(String course, String entityName, String uri) {
    this.course = course;
    this.entityName = entityName;
    this.uri = uri;
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
}
