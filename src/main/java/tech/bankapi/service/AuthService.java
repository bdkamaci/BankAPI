package tech.bankapi.service;

import tech.bankapi.dto.request.ChangePasswordRequest;
import tech.bankapi.dto.request.TwoFactorAuthRequest;
import tech.bankapi.dto.request.UserRequest;
import tech.bankapi.dto.response.UserResponse;

public interface AuthService {
    UserResponse loginUser(UserRequest userRequest);
    void logoutUser(Long id);
    void changePassword(ChangePasswordRequest request);
    void setupTwoFactorAuth(TwoFactorAuthRequest request);
}
