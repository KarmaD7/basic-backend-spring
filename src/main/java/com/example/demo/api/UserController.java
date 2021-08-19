package com.example.demo.api;

import com.example.demo.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/accounts")
@Controller
public class UserController {
  private static UserService userService;

  @ResponseBody
  @GetMapping
  public String login() {
    return "Hello, World";
  }

  @ResponseBody
  @PostMapping
  public void register() {
    ;
  }

  @ResponseBody
  @PostMapping("/changepassword")
  public void changePassword() {
    ;
  }
}
