package com.example.demo.middleware;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    response.setHeader("Content-Type", "application/json; charset=utf-8");
    if (request.getRequestURI().equals("/account") || request.getRequestURI().equals("/edu")) {
      return true;
    }
    try {
      String _id = request.getParameter("id");
      Integer id = Integer.parseInt(_id);
      String idStr = "avaJ" + id + "cltczfdsf" + id + "tukey";
      String cookie = Hashing.sha256().newHasher().putString(idStr, Charsets.UTF_8).hash().toString();
      Cookie[] cookies = request.getCookies();
      for (Cookie _cookie : cookies) {
        if (_cookie.getName().equals("user")) {
          if (_cookie.getValue().equals(cookie)) {
            return true;
          }
        }
      }
      throw new Exception("auth failed");
    } catch (Exception e) {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode json = mapper.createObjectNode();
      response.setStatus(403);
      json.put("success", false);
      json.put("message", "cookie auth failed");
      PrintWriter out = response.getWriter();
      out.println(json);
      out.flush();
      return false;
    }
  }
}
