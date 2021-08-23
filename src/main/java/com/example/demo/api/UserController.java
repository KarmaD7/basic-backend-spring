package com.example.demo.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/accounts")
@Controller
public class UserController {
  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  private ObjectMapper mapper;

  @ResponseBody
  @GetMapping
  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  public ObjectNode login(@RequestParam String nameOrEmail, @RequestParam("hashedPassword") String hashedPassword) {
    ObjectNode json = mapper.createObjectNode();
    final String namePattern = "^([a-zA-Z0-9_-]{1,63})$";
    final String pwdPattern = "[A-Fa-f0-9]{64}";
    if (Pattern.matches(namePattern, nameOrEmail) != true) {
      json.put("success", false);
      json.put("message", "invalid name");
    }
    if (Pattern.matches(pwdPattern, hashedPassword) != true) {
      json.put("success", false);
      json.put("message", "invalid hashedPassword");
    }
    User user = userService.getUser(nameOrEmail, hashedPassword, 1);
    if (user == null) {
      json.put("success", false);
      json.put("message", "wrong name or password");
    }
    json.put("id", user.getId());
    json.put("name", user.getName());
    json.put("email", user.getEmail());
    return json;
  }

  @ResponseBody
  @PostMapping
  public void register(String name, String email, String hashedPassword) {
    ObjectNode json = mapper.createObjectNode();
    final String namePattern = "^([a-zA-Z0-9_-]{1,63})$";
    final String pwdPattern = "[A-Fa-f0-9]{64}";
    if (Pattern.matches(namePattern, name) != true) {
      json.put("success", false);
      json.put("message", "invalid name");
    }
    if (Pattern.matches(pwdPattern, hashedPassword) != true) {
      json.put("success", false);
      json.put("message", "invalid hashedPassword");
    }
  }

  @ResponseBody
  @PostMapping("/changepassword")
  public void changePassword(String oldpwd, String newpwd) {
    ObjectNode json = mapper.createObjectNode(); 
    final String pwdPattern = "[A-Fa-f0-9]{64}";
    if (Pattern.matches(pwdPattern, newpwd) != true) {
      json.put("success", false);
      json.put("message", "invalid hashedPassword");
    }
  }
}
