package com.example.demo.api;

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
  public ObjectNode login(HttpServletResponse response, @RequestParam("nameOrPhone") String nameOrPhone,
      @RequestParam("hashedPassword") String hashedPassword) {
    ObjectNode json = mapper.createObjectNode();
    final String namePattern = "^([a-zA-Z0-9_-]{1,63})$";
    final String phonePattern = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    final String pwdPattern = "[A-Fa-f0-9]{64}";
    Integer mode; // 0 for name, 1 for phone
    if (Pattern.matches(phonePattern, nameOrPhone) == true) {
      mode = 1;
    } else if (Pattern.matches(namePattern, nameOrPhone) == true) {
      mode = 0;
    } else {
      json.put("success", false);
      json.put("message", "invalid name or phone");
      return json;
    }
    if (Pattern.matches(pwdPattern, hashedPassword) != true) {
      json.put("success", false);
      json.put("message", "invalid hashedPassword");
      return json;
    }
    User user = userService.getUser(nameOrPhone, hashedPassword, mode);
    System.out.println(user);
    System.out.println(user == null);
    if (user == null) {
      json.put("success", false);
      json.put("message", "wrong name or password");
      return json;
    }
    json.put("id", user.getId());
    json.put("name", user.getName());
    json.put("phone", user.getPhone());
    String cookieStr = "avaJ" + user.getId() + "cltczfdsf" + user.getId() + "tukey";
    String hashedStr = Hashing.sha256().newHasher().putString(cookieStr, Charsets.UTF_8).hash().toString();
    Cookie cookie = new Cookie("user", hashedStr);
    cookie.setMaxAge(1 * 24 * 3600); // 1 days
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
    return json;
  }

  @ResponseBody
  @PostMapping
  public ObjectNode register(HttpServletResponse response, @RequestBody ObjectNode req) {
    var _name = req.get("name");
    var _phone = req.get("phone");
    var _hashedPassword = req.get("hashedPassword");
    ObjectNode json = mapper.createObjectNode();
    if (_name == null || _phone == null || _hashedPassword == null) {
      response.setStatus(400);
      return json;
    }
    String name = _name.asText();
    String phone = _phone.asText();
    String hashedPassword = _hashedPassword.asText();
    final String namePattern = "^([a-zA-Z0-9_-]{1,63})$";
    final String phonePattern = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    final String pwdPattern = "[A-Fa-f0-9]{64}";
    System.out.println(name);
    if (Pattern.matches(namePattern, name) != true) {
      json.put("success", false);
      json.put("message", "invalid name");
      return json;
    }
    if (Pattern.matches(phonePattern, phone) != true) {
      json.put("success", false);
      json.put("message", "invalid phone number");
      return json;
    }
    if (Pattern.matches(pwdPattern, hashedPassword) != true) {
      json.put("success", false);
      json.put("message", "invalid hashedPassword");
      return json;
    }
    RegisterStatus result = userService.createUser(name, phone, hashedPassword);
    if (result == RegisterStatus.duplicated_name) {
      json.put("success", false);
      json.put("message", "name already exists");
      return json;
    } else if (result == RegisterStatus.duplicated_phone) {
      json.put("success", false);
      json.put("message", "phone already exists");
      return json;
    }
    json.put("success", true);
    return json;
  }

  @ResponseBody
  @PostMapping("/changepassword")
  public ObjectNode changePassword(HttpServletResponse response, @RequestBody ObjectNode req,
      @RequestParam("id") int id) {
    var _oldpwd = req.get("original");
    var _newpwd = req.get("updated");
    ObjectNode json = mapper.createObjectNode();
    if (_oldpwd == null || _newpwd == null) {
      response.setStatus(400);
      json.put("message", "Bad Request");
      return json;
    }
    String oldpwd = _oldpwd.asText();
    String newpwd = _newpwd.asText();
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
