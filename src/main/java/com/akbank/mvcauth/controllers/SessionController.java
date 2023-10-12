package com.akbank.mvcauth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class SessionController {

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("")
  public String Home() {
    // Autenticated olan kullanıcının bilgilerine controllerdan erişmek için
    // kullanılan bir yöntem.
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return "index";
  }

  @GetMapping("login")
  public String Login() {
    return "login";
  }

  @PreAuthorize("hasRole('ROLE_admin')") // bu security anotasyonu ile login olmadan önce login olan kullanıcı ile
                                         // ilgili kontroller check ediliyor.
  // Net [Authorize] attribute benzer bir yaklaşım
  // admin role sayfayı açabilir
  // action bazlı kontrol sağlar.
  @GetMapping("admin")
  public String AdminPage() {
    return "admin";
  }

  @GetMapping("set")
  public String Set(HttpSession session) {

    session.setAttribute("username", "ali");
    session.setAttribute("email", "ali@test.com");
    // return "session/set";
    return "redirect:/display";
  }

  @GetMapping("display")
  public String Display(HttpSession session) {
    // viewdan session değerini okuyamadım
    String sessionValue = (String) session.getAttribute("username");

    System.out.println("sessionValue: " + sessionValue);

    return "sessions/display";
  }

  @GetMapping("clearSessionKey")
  public String ClearSessionKey(HttpSession session) {
    // viewdan session değerini okuyamadım
    session.removeAttribute("username");

    return "redirect:/display";
  }

  @GetMapping("clearSession")
  public String ClearSession(HttpSession session) {
    // tüm session bilgileri temizlenmiş.
    // şuan için server kendi in memory session üzerinden çalışıyoruz.
    session.invalidate();
    // logout işlemini tetiklediğimiz arkadaş.

    return "redirect:/display";
  }

}
