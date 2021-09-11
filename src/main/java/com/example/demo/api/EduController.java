package com.example.demo.api;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.model.EduEntity;
import com.example.demo.service.EduService;
import com.example.demo.service.HistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.demo.utils.BadRequest;
import com.example.demo.utils.EdukgConnection;
import com.example.demo.utils.ExerciseFormatter;

@RequestMapping("/edu")
@Controller
public class EduController {
  
  @Autowired
  private EduService eduService;

  @Autowired
  private HistoryService historyService;
  
  @GetMapping("search")
  @ResponseBody

  public String search(@RequestParam("course") String course, @RequestParam("key") String key) throws JsonProcessingException, JsonMappingException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    ArrayNode courseData = json.putArray("result");
    String id = EdukgConnection.getId();
    String[] courses = course.split(",");
    for (String selectedCourse: courses) {
      System.out.println(selectedCourse);
      String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList?course=" + selectedCourse + "&searchKey=" + key + "&id=" + id;
      String strRes = EdukgConnection.sendGetRequest(url);
      JsonNode edukgRes = mapper.readTree(strRes);
      ObjectNode res = mapper.createObjectNode();
      if (Integer.parseInt(edukgRes.get("code").asText()) != 0) {
        json.put("success", false);
        json.put("message", "edukg has crashed");
        return json.toString();
      }
      JsonNode data = edukgRes.get("data");
      if (data.isArray()) {
        for (JsonNode node: data) {
          ObjectNode onode = (ObjectNode)node;
          onode.put("course", selectedCourse);
          courseData.add(onode);
        }
      }
    }
    json.put("success", true);;
    return json.toString();
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
      return BadRequest.badRequest;
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

  @ResponseBody
  @PostMapping("related")
  public String relatedSubject(HttpServletResponse res, @RequestBody ObjectNode req) {
    String id = EdukgConnection.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/relatedsubject";
    JsonNode _name = req.get("name"); JsonNode _course = req.get("course");
    if (_name == null || _course == null) {
      res.setStatus(400);
      return BadRequest.badRequest;
    }
    String name = _name.asText();
    String course = _course.asText();
    // ObjectNode json = new ObjectMapper().createObjectNode();
    MultiValueMap<String, Object> json = new LinkedMultiValueMap<String, Object>();
    json.add("subjectName", name);
    json.add("course", course);
    json.add("id", id);
    return EdukgConnection.sendPostRequest(url, json);
  }

  @ResponseBody
  @GetMapping("recommend")
  public String recommendExercises(@RequestParam("id") Integer id) {
    ObjectMapper mapper = new ObjectMapper();
    String url = "http://127.0.0.1:8888/recommend";
    List<EduEntity> visitHistory = historyService.getVisitEntityHistory(id);
    List<String> visitedName = visitHistory.stream().map(EduEntity::getName).collect(Collectors.toList());
    List<String> toPassName = visitedName;
    // MultiValueMap<String, Object> json = new LinkedMultiValueMap<String, Object>();
    // json.add("entities", visitedName);
    // System.out.println(json);
    if (visitedName.size() > 5) {
      toPassName = visitedName.subList(visitedName.size() - 5, visitedName.size());
    }
    try {
      ObjectNode json = new ObjectMapper().createObjectNode();
      ArrayNode entities = json.putArray("entities");
      for (String name: toPassName) {
        entities.add(name);
      }
      JsonNode returnedJson =  mapper.readTree(EdukgConnection.sendJsonPostRequest(url, json));
      JsonNode recommendedEntities = returnedJson.get("result");
      // List<String> entityAsStr = new ArrayList<>();
      ArrayNode arrRes = (ArrayNode) recommendedEntities;
      int sz = arrRes.size();
      boolean[] used = new boolean[sz];
      Random r = new Random();
      while (true) {
        int num = r.nextInt(sz);
        if (used[num] == true) continue;
        used[num] = true;
        System.out.println(num);
        String entity = arrRes.get(num).asText();
        String strExercises = getExercise(entity);
        JsonNode relatedExercises = mapper.readTree(strExercises);
        ArrayNode exerciseArr = (ArrayNode) relatedExercises.get("exercise");
        System.out.println(exerciseArr);
        if (exerciseArr.size() == 0) {
          continue;
        } 
        return strExercises;
      }
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
    // if (recommendedEntities.isArray()) {
    //   for (JsonNode node: recommendedEntities) {
    //     entityAsStr.add(node.asText());
    //   }
    // }
    // System.out.println(entityAsStr);
    // for 
    // List
    // this.getExercise(name);
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
