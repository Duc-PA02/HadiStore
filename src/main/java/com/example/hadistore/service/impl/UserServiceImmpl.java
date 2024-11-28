package com.example.hadistore.service.impl;

import com.example.hadistore.components.JwtTokenProvider;
import com.example.hadistore.dtos.request.LoginRequest;
import com.example.hadistore.dtos.request.SignUpRequest;
import com.example.hadistore.dtos.response.LoginResponse;
import com.example.hadistore.entity.Cart;
import com.example.hadistore.entity.Role;
import com.example.hadistore.entity.User;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.CartRepository;
import com.example.hadistore.repository.RoleRepository;
import com.example.hadistore.repository.UserRepository;
import com.example.hadistore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImmpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public User createUser(SignUpRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())){
            throw new DataIntegrityViolationException("Email already exists");
        }
        Role role = roleRepository.findById(signupRequest.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        User user = new User(signupRequest.getName(), signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getPhone(),
                signupRequest.getAddress(), signupRequest.getGender(), signupRequest.getStatus(),
                signupRequest.getImage(), signupRequest.getRegisterDate());
        user.setRoles(Set.of(role));
        userRepository.save(user);
        Cart cart = new Cart(0.0, user.getAddress(), user.getPhone(), user);
        cartRepository.save(cart);
        return user;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getUsername())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(user);
        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setToken(token);
        return response;
    }
}
