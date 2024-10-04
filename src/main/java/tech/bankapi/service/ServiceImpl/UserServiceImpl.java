package tech.bankapi.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bankapi.core.config.ModelMapper.ModelMapperService;
import tech.bankapi.dto.request.UserRequest;
import tech.bankapi.dto.response.UserResponse;
import tech.bankapi.model.User;
import tech.bankapi.repository.UserRepository;
import tech.bankapi.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = modelMapperService.forRequest().map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return modelMapperService.forResponse().map(user, UserResponse.class);
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
}
