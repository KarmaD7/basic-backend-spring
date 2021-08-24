package com.example.demo.middleware;

import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.security.auth.message.AuthException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

  @Autowired
  private UserService userService;

  @Autowired
  static private ObjectMapper mapper;
  
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    try {
      String _id = request.getParameter("id");
      Integer id = Integer.parseInt(_id);
      String idStr = "avaJ" + id + "cltczfdsf" + id + "tukey";
      String cookie = Hashing.sha256().newHasher().putString(idStr, Charsets.UTF_8).hash().toString();
      Cookie[] cookies = request.getCookies();
      for (Cookie _cookie: cookies) {
        if (_cookie.getName() == "user") {
          if (_cookie.getValue() == cookie) {
            return true;
          }
        }
      }
      throw new Exception("auth failed");
    } catch (Exception e) {
      ObjectNode json = mapper.createObjectNode();
      json.put("success", false);
      json.put("message", "cookie auth failed");
      return false;
    }
  }
}
