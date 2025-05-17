package com.learning.connector.service;

import com.learning.connector.model.CustomerProfile;
import com.learning.connector.repository.CustomerProfileRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * @author vickyaa
 * @date 5/14/25
 * @file CustomerProfileDetailService
 */

@Service
public class CustomerProfileDetailService implements UserDetailsService {

  @Autowired
  private CustomerProfileRepository customerProfileRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    CustomerProfile profile = customerProfileRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    List<GrantedAuthority> authorities = profile.getRoles().stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(
        profile.getEmail(),
        profile.getPassword(), // this must be encrypted (BCrypt)
        authorities
    );
  }
}
