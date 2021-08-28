package com.example.demo.api;

import com.example.demo.service.EduService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/edu")
@Controller
public class EduController {
  
  @Autowired
  private EduService eduService;
  
  @GetMapping("search")
  @ResponseBody
  public ObjectNode search() {
    String id = eduService.getId();
    return null;
  }

  @GetMapping("entity")
  @ResponseBody
  public ObjectNode getEntityInfo() {
    String id = eduService.getId();
    return null;
  }

  @GetMapping("exercise")
  @ResponseBody
  public ObjectNode getExercise() {
    String id = eduService.getId();
    return null;
  }

  @GetMapping("find")
  @ResponseBody
  public ObjectNode findKnowledge() {
    String id = eduService.getId();
    return null;
  }



  @ResponseBody
  @PostMapping("qa")
  public ObjectNode questionAnswer() {
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
