package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;

import javax.print.attribute.standard.Media;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.RequestToViewNameTranslator;

@Service
public class EduService {
  public String getId() {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://open.edukg.cn/opedukg/api/typeAuth/user/login";
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode json = mapper.readTree(new File("src/main/resources/secrets.json"));
      ResponseEntity<String> res = restTemplate.postForEntity(url, json, String.class);
      String resBody = res.getBody();
      JsonNode resJson =  mapper.readTree(resBody);
      return resJson.get("id").toString();
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }
}
