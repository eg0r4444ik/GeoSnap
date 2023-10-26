package com.korotkov.hackathon.rest;

import com.korotkov.hackathon.dto.AuthRequestDto;
import com.korotkov.hackathon.dto.AuthResponseDto;
import com.korotkov.hackathon.dto.UserDto;
import com.korotkov.hackathon.entity.UserEntity;
import com.korotkov.hackathon.security.CustomPrincipal;
import com.korotkov.hackathon.security.SecurityService;
import com.korotkov.hackathon.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    SecurityService securityService;
    UserService userService;
    ModelMapper modelMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        UserEntity entity = modelMapper.map(dto, UserEntity.class);
        return userService.registerUser(entity)
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class));
    }


    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return securityService.authenticate(dto.getUsername(), dto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }

    @GetMapping("/getAllUsers")

    public Flux<UserDto> getAllUsers() {
        return userService.getAllUsers().map(userEntity -> modelMapper.map(userEntity, UserDto.class));
    }

    @GetMapping("/hello")
    public Mono<String> getHello() {
        return Mono.just("123");
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(customPrincipal.getId())
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class));
    }
}
