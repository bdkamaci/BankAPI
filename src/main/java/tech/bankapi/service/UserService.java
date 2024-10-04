package tech.bankapi.service;

import tech.bankapi.dto.request.UserRequest;
import tech.bankapi.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse registerUser(UserRequest userRequest);
    UserResponse updateUser(UserRequest userRequest);
    void deleteUser(Long id);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
}
