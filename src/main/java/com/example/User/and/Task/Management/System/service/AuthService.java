package com.example.User.and.Task.Management.System.service;

import com.example.User.and.Task.Management.System.model.User;
import com.example.User.and.Task.Management.System.model.Role;
import com.example.User.and.Task.Management.System.repository.UserRepository;
import com.example.User.and.Task.Management.System.security.JwtUtils;
import com.example.User.and.Task.Management.System.security.UserDetailsImpl;
import dto.request.RegisterRequest;
import dto.request.LoginRequest;
import dto.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null) {
            for (String role : request.getRoles()) {
                roles.add(Role.valueOf(role));
            }
        } else {
            roles.add(Role.USER);
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        return new AuthResponse(jwt);
    }
}
