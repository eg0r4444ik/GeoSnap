package com.korotkov.hackathon.service;

import com.korotkov.hackathon.entity.SatelliteEntity;
import com.korotkov.hackathon.entity.UserEntity;
import com.korotkov.hackathon.entity.UserRole;
import com.korotkov.hackathon.repository.UserRepository;
import com.korotkov.hackathon.security.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserEntity> registerUser(UserEntity user) {
        SatelliteEntity build = SatelliteEntity.builder()
                .distanceToEarth(23)
                .orbitPeriod(23)
                .earthToOrbitAngle(23)
                .build();


        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .enabled(true)
                        .username(user.getUsername())
                        .role(UserRole.USER)
                        .build()
                //                .createdAt(LocalDateTime.now())
                //                .updatedAt(LocalDateTime.now())
        ).doOnSuccess(u -> {
            log.info("IN registerUser - user: {} created", u);
        });
    }

    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<UserEntity> getCurrentUser() {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getName());
    }

    public Mono<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Flux<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


}
