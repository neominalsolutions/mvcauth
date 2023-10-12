package com.akbank.mvcauth.services;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // UserReposity bağlanıp veri çektik sonrasında ise userDetails bilgiler
    // ApplicationUser sınıfı üzerinden User Security nesnesine maplenerek süreç
    // otomatize edilmiş olur.
    var passwordHash = passwordEncoder().encode("password");

    // autories yada roles methodlarından birini kullanalım. ikiside grantAuthority
    // class tanımı olduğundan birbilerini eziyor.
    return User.builder().username("user").password(passwordHash)
        .roles("admin", "manager").build();
  }

}
