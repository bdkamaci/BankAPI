package tech.bankapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.bankapi.dto.request.UserRequest;
import tech.bankapi.dto.response.UserResponse;
import tech.bankapi.security.JwtUtil;
import tech.bankapi.service.AuthService;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = authService.loginUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }

        // Extract userId from JWT
        Long userId = jwtUtil.extractUserId(token);
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        authService.logoutUser(userId);
        return ResponseEntity.ok().build();
    }

    // Token Extraction Helper Method
    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract the token part
        }
        return null;
    }
}
