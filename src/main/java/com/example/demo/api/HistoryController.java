package com.example.demo.api;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("history")
@Controller
public class HistoryController {
  @GetMapping("visit")
  @ResponseBody
  public String getVisitHistoryForUser(@RequestParam("id") String id) {
    return null;
  }

  @PostMapping("visit/entity")
  @ResponseBody
  public String visitEntity(HttpServletResponse response, @RequestBody ObjectNode request, @RequestParam("id") String id) {
    return null;
  }

  @PostMapping("visit/exercise")
  @ResponseBody
  public String visitExercise(HttpServletResponse response, @RequestBody ObjectNode request, @RequestParam("id") String id) {
    return null;
  }

  @GetMapping("search")
  @ResponseBody
  public String getSearchHistoryForUser(HttpServletResponse response, @RequestBody ObjectNode request, @RequestParam("id") String id) {
    return null;
  }

  @PostMapping("search")
  @ResponseBody
  public String putSearchHistory(HttpServletResponse response, @RequestBody ObjectNode request, @RequestParam("id") String id) {
    return null;
  }
}
