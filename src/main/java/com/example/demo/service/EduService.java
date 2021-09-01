package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.print.attribute.standard.Media;

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
      System.out.println(resJson.get("id").asText());
      return resJson.get("id").asText();
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }
  public String sendGetRequest(final String url) {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForEntity(url, String.class).getBody();
  }
  public String sendPostRequest(final String url, MultiValueMap<String, Object> json) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(json, headers);
    return restTemplate.postForEntity(url, request, String.class).getBody();
  }
}
