package com.example.demo.api;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.service.EduService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public String findKnowledge() {
    String id = eduService.getId();
    return null;
  }

  @ResponseBody
  @PostMapping("qa")
  public String questionAnswer() {
    String id = eduService.getId();
    return null;
  }

}

// 登录 http://open.edukg.cn/opedukg/api/typeAuth/user/login
// 实体搜索 http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList
// 实体详情 http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName
// 问答 http://open.edukg.cn/opedukg/api/typeOpen/open/inputQuestion
// 知识链接 http://open.edukg.cn/opedukg/api/typeOpen/open/linkInstance
// 相关习题 http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName
// 知识关联 http://open.edukg.cn/opedukg/api/typeOpen/open/linkInstance
// 知识链接详情 http://open.edukg.cn/opedukg/api/typeOpen/open/getKnowledgeCard
