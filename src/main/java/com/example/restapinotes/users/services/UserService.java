package com.example.restapinotes.users.services;

import com.example.restapinotes.users.entity.User;
import com.example.restapinotes.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findById(username);

        if(user.isEmpty()){
            return null;
        }
        return user.get();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
