package com.example.demo.api;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.RegisterStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/account")
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
  @ResponseStatus(HttpStatus.OK)
  public ObjectNode login(HttpServletResponse response, @RequestParam("nameOrEmail") String nameOrEmail,
      @RequestParam("hashedPassword") String hashedPassword) {
    ObjectNode json = mapper.createObjectNode();
    final String namePattern = "^([a-zA-Z0-9_-]{1,63})$";
    final String pwdPattern = "[A-Fa-f0-9]{64}";
    if (Pattern.matches(namePattern, nameOrEmail) != true) {
      json.put("success", false);
      json.put("message", "invalid name");
      return json;
    }
    if (Pattern.matches(pwdPattern, hashedPassword) != true) {
      json.put("success", false);
      json.put("message", "invalid hashedPassword");
      return json;
    }
    User user = userService.getUser(nameOrEmail, hashedPassword, 0);
    System.out.println(user);
    System.out.println(user == null);
    if (user == null) {
      json.put("success", false);
      json.put("message", "wrong name or password");
      return json;
    }
    json.put("id", user.getId());
    json.put("name", user.getName());
    json.put("email", user.getEmail());
    String cookieStr = "avaJ" + user.getId() + "cltczfdsf" + user.getId() + "tukey";
    String hashedStr = Hashing.sha256().newHasher().putString(cookieStr, Charsets.UTF_8).hash().toString();
    Cookie cookie = new Cookie("user", hashedStr);
    cookie.setMaxAge(7 * 24 * 3600); // 7 days 
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
    return json;
  }

  @ResponseBody
  @PostMapping
  public ObjectNode register(HttpServletResponse response, @RequestBody ObjectNode req) {
    var _name = req.get("name");
    var _email = req.get("email");
    var _hashedPassword = req.get("hashedPassword");
    ObjectNode json = mapper.createObjectNode();
    if (_name == null || _email == null || _hashedPassword == null) {
      response.setStatus(400);
      return json;
    }
    String name = _name.asText();
    String email = _email.asText();
    String hashedPassword = _hashedPassword.asText();
    final String namePattern = "^([a-zA-Z0-9_-]{1,63})$";
    final String pwdPattern = "[A-Fa-f0-9]{64}";
    System.out.println(name);
    if (Pattern.matches(namePattern, name) != true) {
      json.put("success", false);
      json.put("message", "invalid name");
      return json;
    }
    if (Pattern.matches(pwdPattern, hashedPassword) != true) {
      json.put("success", false);
      json.put("message", "invalid hashedPassword");
      return json;
    }
    RegisterStatus result = userService.createUser(name, email, hashedPassword);
    if (result == RegisterStatus.duplicated_name) {
      json.put("success", false);
      json.put("message", "name already exists");
      return json;
    } else if (result == RegisterStatus.duplicated_email) {
      json.put("success", false);
      json.put("message", "email already exists");
      return json;
    }
    json.put("success", true);
    return json;
  }

  @ResponseBody
  @PostMapping("/changepassword")
  public ObjectNode changePassword(@RequestParam("id") int id, String oldpwd, String newpwd) {
    ObjectNode json = mapper.createObjectNode();
    final String pwdPattern = "[A-Fa-f0-9]{64}";
    if (Pattern.matches(pwdPattern, newpwd) != true) {
      json.put("success", false);
      json.put("message", "invalid hashedPassword");
      return json;
    }
    boolean res = userService.changepassword(id, oldpwd, newpwd);
    if (res == false) {
      json.put("success", false);
      json.put("message", "wrong password");
      return json;
    }
    json.put("success", true);
    return json;
  }
}
