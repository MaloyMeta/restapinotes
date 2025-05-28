package com.example.restapinotes.security;

import com.example.restapinotes.users.entity.User;
import com.example.restapinotes.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findById(username).orElseThrow(
                ()-> new UsernameNotFoundException("User: " + username + " not found")
        );
      return new org.springframework.security.core.userdetails.User(
              user.getUserId(),
              user.getPasswordHash(),
              Collections.emptyList()
      );
    }
}
