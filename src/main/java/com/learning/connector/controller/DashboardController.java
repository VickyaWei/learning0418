package com.learning.connector.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vickyaa
 * @date 5/13/25
 * @file DashboardController
 */
@Controller
public class DashboardController {

  //  @GetMapping("/dashboard")
//  public String dashboard() {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//    if (hasRole(auth, "ROLE_ADMIN") || hasRole(auth, "SCOPE_admin")) {
//      return "redirect:/admin/dashboard";
//    } else {
//      return "redirect:/user/dashboard";
//    }
//  }
//
//  // Admin dashboard
//  @GetMapping("/admin/dashboard")
//  public String adminDashboard(Model model) {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    model.addAttribute("username", auth.getName());
//    model.addAttribute("roles", auth.getAuthorities().toString());
//
//    return "admin-dashboard.jsp";
//  }
//
//  // User dashboard
//  @GetMapping("/user/dashboard")
//  public String userDashboard(Model model) {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    model.addAttribute("username", auth.getName());
//    model.addAttribute("roles", auth.getAuthorities().toString());
//
//    return "user-dashboard";
//  }
//
//  private boolean hasRole(Authentication authentication, String role) {
//    return authentication.getAuthorities().stream()
//        .anyMatch(a -> a.getAuthority().equals(role));
//  }
  @GetMapping("/user/dashboard")
  public String dashboard() {
    return "dashboard";
  }
}