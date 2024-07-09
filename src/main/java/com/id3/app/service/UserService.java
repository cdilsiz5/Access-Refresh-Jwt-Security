package com.id3.app.service;

import com.id3.app.dto.UserDto;
import com.id3.app.request.CreateUserRequest;
import com.id3.app.request.UpdateUserRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<UserDto> createUser(CreateUserRequest createUserRequest);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UpdateUserRequest updateUserRequest);
}
