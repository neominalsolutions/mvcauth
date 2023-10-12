package com.akbank.mvcauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/login", "/").permitAll()
            .anyRequest().authenticated())
        .formLogin((form) -> form
            .loginPage("/login") // custom sayfa yönlendirmesi
            .permitAll())
        .logout((logout) -> {
          logout.permitAll();
          logout.invalidateHttpSession(true);
          logout.logoutSuccessUrl("/");
          logout.logoutUrl("/logout");
        });

    return http.build();
  }
}

// redis komutlar
// redis-cli
// keys *
// HGETALL "spring:session:sessions:172d353b-559f-44f1-b9ec-cb7e86b69703"
