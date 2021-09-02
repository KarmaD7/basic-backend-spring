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

@RequestMapping("collection")
@Controller
public class CollectionController {
  @GetMapping
  @ResponseBody
  public String getCollectionForUser(@RequestParam("id") String id) {
    return null;
  }

  @PostMapping("entity")
  @ResponseBody
  public String collectEntity(HttpServletResponse response, @RequestBody ObjectNode request, @RequestParam("id") String id) {
    return null;
  }

  @PostMapping("exercise")
  @ResponseBody
  public String collectExercise(HttpServletResponse response, @RequestBody ObjectNode request, @RequestParam("id") String id) {
    return null;
  }
}
