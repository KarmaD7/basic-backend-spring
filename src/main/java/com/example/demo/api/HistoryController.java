package com.example.demo.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;

import com.example.demo.service.HistoryService;
import com.example.demo.utils.BadRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("history")
@Controller
public class HistoryController {
  @Autowired
  HistoryService historyService;

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

  @GetMapping("search/save")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public ObjectNode getSearchHistoryForUser(@RequestParam("id") String id) {
    ObjectNode json = new ObjectMapper().createObjectNode();
    // int uid;
    // try {
    //   uid = Integer.parseInt(id);
    // } catch (NumberFormatException e) {
      
    // } 
    // cookie auth guarantees the id is valid.
    List<String> history = historyService.getSearchHistory(Integer.parseInt(id));  
    json.put("success", true);
    ArrayNode arrayNode = json.putArray("history");
    for (String item: history) {
      arrayNode.add(item);
    }
    
    return json;
  }

  @GetMapping("search/get")
  @ResponseBody
  public String putSearchHistory(HttpServletResponse response, @RequestBody ObjectNode request, @RequestParam("id") String id) {
    ObjectNode json = new ObjectMapper().createObjectNode();
    JsonNode _key = request.get("key");
    if (_key == null) {
      response.setStatus(400);
      return BadRequest.badRequest;
    }
    String key = _key.asText();
    boolean success = historyService.addSearchHistory(Integer.parseInt(id), key);
    json.put("success", success);
    return json.asText();
  }
}
