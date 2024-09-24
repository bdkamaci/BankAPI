package tech.bankapi.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bankapi.core.config.ModelMapper.ModelMapperService;
import tech.bankapi.dto.request.UserRequest;
import tech.bankapi.dto.response.UserResponse;
import tech.bankapi.entity.User;
import tech.bankapi.exception.GlobalExceptionHandler;
import tech.bankapi.repository.UserRepository;
import tech.bankapi.security.JwtUtil;
import tech.bankapi.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        if(userRepository.findById(modelMapperService.forRequest().map(userRequest, User.class).getId()).isEmpty()) {
            User user = modelMapperService.forRequest().map(userRequest, User.class);
            userRepository.save(user);
            return modelMapperService.forResponse().map(user, UserResponse.class);
        } else {
            throw new RuntimeException("User already exists");
        }
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        User user = userRepository.findById(modelMapperService.forRequest().map(userRequest, User.class).getId())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        modelMapperService.forRequest().map(userRequest, user);
        userRepository.saveAndFlush(user);
        return modelMapperService.forResponse().map(user, UserResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return modelMapperService.forResponse().map(user, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapperService.forResponse().map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse loginUser(UserRequest userRequest) {
        // Check if user exists with the given email
        User user = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new GlobalExceptionHandler.InvalidCredentialsException("Invalid email or password"));

        // Validate password
        if (!isPasswordValid(userRequest.getPassword(), user.getPassword())) {
            throw new GlobalExceptionHandler.InvalidCredentialsException("Invalid email or password");
        }

        // Generate JWT Token
        String token = jwtUtil.generateToken(modelMapperService.forResponse().map(user, org.springframework.security.core.userdetails.User.class));

        // Map user entity to UserResponse
        UserResponse userResponse = modelMapperService.forResponse().map(user, UserResponse.class);
        userResponse.setToken(token);

        return userResponse;
    }

    private boolean isPasswordValid(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
