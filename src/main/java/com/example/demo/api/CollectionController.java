package com.example.demo.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.service.CollectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.example.demo.model.EduEntity;
import com.example.demo.service.HistoryService;
import com.example.demo.utils.BadRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("collection")
@Controller
public class CollectionController {
  @Autowired
  CollectionService collectionService;

  @GetMapping
  @ResponseBody
  public ObjectNode getCollectionForUser(@RequestParam("id") String id) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode json = mapper.createObjectNode();
    List<EduEntity> collection = collectionService.getCollectEntityHistory(Integer.parseInt(id));  
    json.put("success", true);
    ArrayNode arrayNode = json.putArray("history");
    for (EduEntity item: collection) {
      JsonNode itemJson = mapper.convertValue(item.toMap(), JsonNode.class);
      arrayNode.add(itemJson);
    }
    return json;
  }

  @PostMapping("entity")
  @ResponseBody
  public String collectEntity(HttpServletResponse response, @RequestBody ObjectNode request, @RequestParam("id") String id) {
    ObjectNode json = new ObjectMapper().createObjectNode();
    JsonNode _uri = request.get("uri");
    if (_uri == null) {
      response.setStatus(400);
      return BadRequest.badRequest;
    }
    String uri = _uri.asText();
    boolean success = collectionService.addCollectEntityHistory(Integer.parseInt(id), uri);
    json.put("success", success);
    return json.toString();
  }

  @DeleteMapping("entity")
  @ResponseBody
  public String collectExercise(HttpServletResponse response, @RequestBody ObjectNode request, @RequestParam("id") Integer id) {
    ObjectNode json = new ObjectMapper().createObjectNode();
    JsonNode _uri = request.get("uri");
    if (_uri == null) {
      response.setStatus(400);
      return BadRequest.badRequest;
    }
    String uri = _uri.asText();
    boolean success = collectionService.deleteCollection(id, uri);
    json.put("success", success);
    return json.toString();
  }
}
