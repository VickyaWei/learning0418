package com.learning.connector.config;

import com.learning.connector.service.CustomOAuth2UserService;
import com.learning.connector.service.CustomerProfileDetailService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author vickyaa
 * @date 5/13/25
 * @file SecurityConfig
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private CustomerProfileDetailService customerProfileDetailService;

  @Autowired
  private CustomOAuth2UserService customOAuth2UserService;


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            // No authentication required to access these paths.
            .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
            .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
            .requestMatchers("/api/customers").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")                      // custom login page
            .loginProcessingUrl("/login")
            .successHandler(customAuthenticationSuccessHandler())    // after successful username/password login
            .failureUrl("/login?error=true")
            .permitAll()
        )
        .oauth2Login(oauth2 -> oauth2
            .loginPage("/login")  // custom login page
            .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
            .successHandler(customAuthenticationSuccessHandler())
            .failureUrl("/login?error=true") // always go to dashboard
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        )
        .csrf(csrf -> csrf.disable())
        .authenticationProvider(daoAuthenticationProvider());



    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(customerProfileDetailService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }


  @Bean
  public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
    return (request, response, authentication) -> {
      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

      if (hasAuthority(authorities, "ROLE_ADMIN")) {
        response.sendRedirect("/admin/dashboard");
      } else {
        response.sendRedirect("/user/dashboard");
      }
    };
  }

  private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
    return authorities.stream()
        .anyMatch(a -> a.getAuthority().equals(authority));
  }


}
