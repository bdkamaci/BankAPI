package tech.bankapi.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.bankapi.core.config.ModelMapper.ModelMapperService;
import tech.bankapi.dto.request.ChangePasswordRequest;
import tech.bankapi.dto.request.TwoFactorAuthRequest;
import tech.bankapi.dto.request.UserRequest;
import tech.bankapi.dto.response.UserResponse;
import tech.bankapi.exception.custom.InvalidCredentialsException;
import tech.bankapi.exception.custom.ResourceNotFoundException;
import tech.bankapi.model.TwoFactorAuthSettings;
import tech.bankapi.model.User;
import tech.bankapi.repository.TwoFactorAuthSettingsRepository;
import tech.bankapi.repository.UserRepository;
import tech.bankapi.security.JwtUtil;
import tech.bankapi.service.AuthService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TwoFactorAuthSettingsRepository twoFactorAuthSettingsRepository;
    private final ModelMapperService modelMapperService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponse loginUser(UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!isPasswordValid(userRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Generate JWT token using user email and ID
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());

        // Map user to UserResponse and add token
        UserResponse userResponse = modelMapperService.forResponse().map(user, UserResponse.class);
        userResponse.setToken(token);

        return userResponse;
    }



    private boolean isPasswordValid(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void logoutUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // Invalidate the user's session by updating lastLogoutTime
        user.setLastLogoutTime(LocalDateTime.now());
        userRepository.save(user);

        // TO DO: Add additional actions for logging or session termination in need.
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        // Fetch the user by ID
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        // Validate the current password using PasswordEncoder's matches() method
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Current password is invalid");
        }

        // Encode the new password
        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);

        // Save the updated user
        userRepository.save(user);
    }

    @Override
    public void setupTwoFactorAuth(TwoFactorAuthRequest request) {
        // Fetch user by ID
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        // Create or update the 2FA settings
        TwoFactorAuthSettings settings = twoFactorAuthSettingsRepository.findByUserId(request.getUserId());
        if (settings == null) {
            settings = new TwoFactorAuthSettings();
            settings.setUserId(request.getUserId());
        }
        settings.setMethod(request.getMethod());
        settings.setEnabled(true);

        // Save the settings
        twoFactorAuthSettingsRepository.save(settings);
    }
}
