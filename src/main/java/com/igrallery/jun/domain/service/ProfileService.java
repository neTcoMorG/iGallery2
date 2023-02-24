package com.igrallery.jun.domain.service;

import com.igrallery.jun.domain.entity.User;
import com.igrallery.jun.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    public Optional<User> getProfile (String email) {
        return userRepository.findByEmail(email);
    }
}
