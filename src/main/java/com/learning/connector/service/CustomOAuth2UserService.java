package com.learning.connector.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 5/16/25
 * @file OAuth2UserService
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    // Fetch user info from Google
    OAuth2User oauth2User = super.loadUser(userRequest);
    Map<String, Object> attributes = oauth2User.getAttributes();

    // Default role for Gmail users
    List<GrantedAuthority> authorities = Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_USER"));

    // Example: grant admin role based on specific email
    String email = (String) attributes.get("email");
    if ("admin@example.com".equalsIgnoreCase(email)) {
      authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    // Google uses "sub" as the unique user identifier
    return new DefaultOAuth2User(authorities, attributes, "sub");
  }
}