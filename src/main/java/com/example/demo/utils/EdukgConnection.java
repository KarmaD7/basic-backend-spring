package com.example.demo.utils;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.RequestToViewNameTranslator;

public class EdukgConnection {
  static public String getId() {
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
  static public String sendGetRequest(final String url) {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForEntity(url, String.class).getBody();
  }
  static public String sendPostRequest(final String url, MultiValueMap<String, Object> json) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(json, headers);
    return restTemplate.postForEntity(url, request, String.class).getBody();
  }
  static public String sendJsonPostRequest(final String url, ObjectNode json) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
    headers.setContentType(type);
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // headers.setCharset(MediaType.APPLICATION_JSON);
    HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
    return restTemplate.postForEntity(url, request, String.class).getBody();
  }
}
