package org.jinwenxin.lifewishlist.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jinwenxin.lifewishlist.dto.request.LoginRequest;
import org.jinwenxin.lifewishlist.dto.request.RegisterRequest;
import org.jinwenxin.lifewishlist.dto.request.UserProfileUpdateRequest;
import org.jinwenxin.lifewishlist.dto.response.AuthResponse;
import org.jinwenxin.lifewishlist.dto.response.UserProfileResponse;
import org.jinwenxin.lifewishlist.model.User;
import org.jinwenxin.lifewishlist.service.UserService;
import org.jinwenxin.lifewishlist.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证接口", description = "用户注册和登录相关接口")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // 检查用户名和邮箱是否已存在
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(registerRequest.getPassword());

        User createdUser = userService.createUser(user);

        // 生成JWT令牌
        String token = jwtUtil.generateToken(createdUser.getUsername());

        return ResponseEntity.ok(new AuthResponse(token, createdUser.getId(), createdUser.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Optional<User> userOptional = userService.getUserByUsername(loginRequest.getUsername());
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }

            User user = userOptional.get();
            String token = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        try {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);

            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }

            User user = userOptional.get();
            UserProfileResponse userProfile = new UserProfileResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getAvatarUrl()
            );

            return ResponseEntity.ok(userProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get user information");
        }
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UserProfileUpdateRequest profileUpdateRequest, 
                                               Authentication authentication) {
        try {
            // 获取当前认证用户
            String currentUsername = authentication.getName();
            
            // 查找当前用户
            Optional<User> currentUserOptional = userService.getUserByUsername(currentUsername);
            if (currentUserOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            
            User currentUser = currentUserOptional.get();
            
            // 检查是否有权限更新该用户资料（用户只能更新自己的资料）
            if (!currentUser.getId().equals(profileUpdateRequest.getId())) {
                return ResponseEntity.badRequest().body("You can only update your own profile");
            }
            
            // 检查用户名是否被其他用户使用
            Optional<User> userWithSameUsername = userService.getUserByUsername(profileUpdateRequest.getUsername());
            if (userWithSameUsername.isPresent() && !userWithSameUsername.get().getId().equals(currentUser.getId())) {
                return ResponseEntity.badRequest().body("Username is already taken!");
            }
            
            // 检查邮箱是否被其他用户使用
            Optional<User> userWithSameEmail = userService.getUserByEmail(profileUpdateRequest.getEmail());
            if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(currentUser.getId())) {
                return ResponseEntity.badRequest().body("Email is already in use!");
            }
            
            // 更新用户信息
            currentUser.setUsername(profileUpdateRequest.getUsername());
            currentUser.setEmail(profileUpdateRequest.getEmail());
            currentUser.setAvatarUrl(profileUpdateRequest.getAvatarUrl());
            
            User updatedUser = userService.updateUser(currentUser);
            
            UserProfileResponse userProfile = new UserProfileResponse(
                    updatedUser.getId(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getAvatarUrl()
            );
            
            return ResponseEntity.ok(userProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update user profile: " + e.getMessage());
        }
    }
}