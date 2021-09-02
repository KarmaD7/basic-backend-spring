package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.print.attribute.standard.Media;

import com.example.demo.dao.EduRepository;
import com.example.demo.model.EduEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.RequestToViewNameTranslator;

@Service
public class EduService {
  @Autowired
  private EduRepository eduDao;

  public EduEntity getEduEntityByUri(String uri) {
    return eduDao.findByUri(uri).orElse(null);
  }

  public EduEntity getEduEntityByCourseAndName(String course, String name) {
    return eduDao.findByCourseAndEntityname(course, name).orElse(null);
  }
  
  public void saveEduEntity(EduEntity entity) {
    eduDao.save(entity);
  }
}
