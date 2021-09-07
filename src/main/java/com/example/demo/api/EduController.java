package com.example.demo.api;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.model.EduEntity;
import com.example.demo.service.EduService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.cache.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.utils.EdukgConnection;
import com.example.demo.utils.ExerciseFormatter;

@RequestMapping("/edu")
@Controller
public class EduController {
  
  @Autowired
  private EduService eduService;
  
  @GetMapping("search")
  @ResponseBody

  public String search(@RequestParam("course") String course, @RequestParam("key") String key) {
    String id = EdukgConnection.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList?course=" + course + "&searchKey=" + key + "&id=" + id;
    return EdukgConnection.sendGetRequest(url);
    // return null;
  }

  @GetMapping("entity")
  @ResponseBody
  public String getEntityInfo(@RequestParam("course") String course, @RequestParam("name") String name) {
    String id = EdukgConnection.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName?course=" + course + "&name=" + name + "&id=" + id;
    String strResult = EdukgConnection.sendGetRequest(url);
    try {
      JsonNode data = new ObjectMapper().readTree(strResult).get("data");
      String uri = data.get("uri").asText();
      if (!uri.equals("")) {
        eduService.saveEduEntity(new EduEntity(course, name, uri, true));
      }
    } catch (Exception e) {
      System.out.println("error when saving entity to db: " + e);
    }
    return strResult;
  }

  @GetMapping("exercise")
  @ResponseBody
  public String getExercise(@RequestParam("name") String name) {
    String id = EdukgConnection.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName?uriName=" + name + "&id=" + id;
    // to do: exercise text format; save in db
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode json = mapper.readTree(EdukgConnection.sendGetRequest(url));
      JsonNode exercises = json.get("data");
      ObjectNode jsonToReturn = mapper.createObjectNode();
      ArrayNode exerciseArray = jsonToReturn.putArray("exercise");
      System.out.println(exercises);
      if (exercises.isArray()) {
        System.out.println(true);
        for (JsonNode node: exercises) {
          System.out.println(node);
          String question = node.get("qBody").asText();
          ObjectNode questionJson = ExerciseFormatter.getFormattedChoiceExercise(question);
          if (questionJson == null) continue;
          questionJson.put("answer", node.get("qAnswer").asText());
          exerciseArray.add(questionJson);
        }
        jsonToReturn.put("success", true);
      } else {
        jsonToReturn.put("success", false);
      }
      return jsonToReturn.toString();
    } catch (JsonProcessingException e) {
      return null;
    }
    // ExerciseFormatter.getFormattedChoiceExercise(content);
  }

  @PostMapping("find")
  @ResponseBody
  public String findKnowledge(HttpServletResponse res, @RequestBody ObjectNode req) {
    String id = EdukgConnection.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/linkInstance";
    JsonNode _text = req.get("text"); JsonNode _course = req.get("course");
    if (_text == null || _course == null) {
      res.setStatus(400);
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode json = mapper.createObjectNode();
      json.put("message", "Bad Request");
      return json.asText();
    }
    // ObjectNode json = new ObjectMapper().createObjectNode();
    MultiValueMap<String, Object> json = new LinkedMultiValueMap<String, Object>();
    json.add("context", _text.asText());
    json.add("course", _course.asText());
    json.add("id", id);
    return EdukgConnection.sendPostRequest(url, json);
  }

  @ResponseBody
  @PostMapping("qa")
  public String questionAnswer(HttpServletResponse res, @RequestBody ObjectNode req) {
    String id = EdukgConnection.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/inputQuestion";
    JsonNode _question = req.get("question"); JsonNode _course = req.get("course");
    if (_question == null || _course == null) {
      res.setStatus(400);
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode json = mapper.createObjectNode();
      json.put("message", "Bad Request");
      return json.asText();
    }
    String question = _question.asText();
    String course = _course.asText();
    // ObjectNode json = new ObjectMapper().createObjectNode();
    MultiValueMap<String, Object> json = new LinkedMultiValueMap<String, Object>();
    json.add("inputQuestion", question);
    json.add("course", course);
    json.add("id", id);
    return EdukgConnection.sendPostRequest(url, json);
  }
}

// 登录 http://open.edukg.cn/opedukg/api/typeAuth/user/login
// 实体搜索 http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList
// 实体详情 http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName
// 问答 http://open.edukg.cn/opedukg/api/typeOpen/open/inputQuestion
// 知识链接 http://open.edukg.cn/opedukg/api/typeOpen/open/linkInstance
// 相关习题 http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName
// 知识关联 http://open.edukg.cn/opedukg/api/typeOpen/open/relatedsubject
// 知识链接详情 http://open.edukg.cn/opedukg/api/typeOpen/open/getKnowledgeCard
