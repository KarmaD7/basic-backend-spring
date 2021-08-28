package com.example.demo.api;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.service.EduService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/edu")
@Controller
public class EduController {
  
  @Autowired
  private EduService eduService;
  
  @GetMapping("search")
  @ResponseBody

  public String search(@RequestParam("course") String course, @RequestParam("key") String key) {
    String id = eduService.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList?course=" + course + "&searchKey=" + key + "&id=" + id;
    return eduService.sendGetRequest(url);
    // return null;
  }

  @GetMapping("entity")
  @ResponseBody
  public String getEntityInfo(@RequestParam("course") String course, @RequestParam("name") String name) {
    String id = eduService.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName?course=" + course + "&name=" + name + "&id=" + id;
    return eduService.sendGetRequest(url);
  }

  @GetMapping("exercise")
  @ResponseBody
  public String getExercise(@RequestParam("name") String name) {
    String id = eduService.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName?uriName=" + name + "&id=" + id;
    return eduService.sendGetRequest(url);
  }

  @PostMapping("find")
  @ResponseBody
  public String findKnowledge(HttpServletResponse res, @RequestBody ObjectNode req) {
    String id = eduService.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/linkInstance";
    JsonNode _text = req.get("text"); JsonNode _course = req.get("course");
    if (_text == null || _course == null) {
      res.setStatus(400);
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode json = mapper.createObjectNode();
      json.put("message", "Bad Request");
      return json.toString();
    }
    ObjectNode json = new ObjectMapper().createObjectNode();
    json.put("context", _text.toString());
    json.put("course", _course.toString());
    json.put("id", id);
    return eduService.sendPostRequest(url, json)ll;
  }

  @ResponseBody
  @PostMapping("qa")
  public String questionAnswer(HttpServletResponse res, @RequestBody ObjectNode req) {
    String id = eduService.getId();
    String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/inputQuestion";
    JsonNode _question = req.get("question"); JsonNode _course = req.get("course");
    if (_question == null || _course == null) {
      res.setStatus(400);
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode json = mapper.createObjectNode();
      json.put("message", "Bad Request");
      return json.toString();
    }
    String question = _question.toString();
    String course = _course.toString();
    ObjectNode json = new ObjectMapper().createObjectNode();
    json.put("inputQuestion", question);
    json.put("course", course);
    json.put("id", id);
    return eduService.sendPostRequest(url, json);
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
