package tech.bankapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.bankapi.dto.request.ChangePasswordRequest;
import tech.bankapi.dto.request.TwoFactorAuthRequest;
import tech.bankapi.dto.response.SecurityLogResponse;
import tech.bankapi.service.AuthService;
import tech.bankapi.service.SecurityLogService;

import java.util.List;

@Controller
@RequestMapping("/api/security")
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityLogService securityLogService;
    private final AuthService authService;

    @PostMapping("/two-factor-auth")
    public ResponseEntity<Void> setupTwoFactorAuth(@RequestBody TwoFactorAuthRequest request) {
        authService.setupTwoFactorAuth(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logs")
    public ResponseEntity<List<SecurityLogResponse>> getUserLogs(@RequestParam Long userId) {
        List<SecurityLogResponse> logs = securityLogService.getUserLogs(userId);
        return ResponseEntity.ok(logs);
    }
}
