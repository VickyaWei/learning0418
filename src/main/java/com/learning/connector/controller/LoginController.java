package com.learning.connector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author vickyaa
 * @date 5/14/25
 * @file LoginController
 */
@Controller
public class LoginController {

  @GetMapping("/login")
  public String login() {
    return "login";
  }

}