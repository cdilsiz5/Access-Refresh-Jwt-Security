package com.id3.app.service.impl;

import com.id3.app.config.PasswordConfiguration;
import com.id3.app.dto.UserDto;
import com.id3.app.exception.UserNotFoundException;
import com.id3.app.model.Role;
import com.id3.app.model.User;
import com.id3.app.repository.RoleRepository;
import com.id3.app.repository.UserRepository;
import com.id3.app.request.CreateUserRequest;
import com.id3.app.request.UpdateUserRequest;
import com.id3.app.security.UserDetailsImpl;
import com.id3.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.id3.app.constants.Constant.USER_NOT_FOUND;
import static com.id3.app.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<UserDto> createUser(CreateUserRequest createUserRequest) {
        User user = USER_MAPPER.createUser(createUserRequest);
        Role userRole = roleRepository.findByUserRoleEquals(createUserRequest.getUserRole())
                .orElseThrow(() -> new RuntimeException("Role Not Found"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        String encodedNewPassword = passwordEncoder.encode(createUserRequest.getPassword());
        user.setPassword(encodedNewPassword);
        return ResponseEntity.ok(USER_MAPPER.toUserDto(userRepository.save(user)));
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Fetching all users");
        return USER_MAPPER.toUserDtoList(userRepository.findAll().stream().collect(Collectors.toList()));
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequest updateUserRequest) {
        log.info("Updating user with ID: {}", id);
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        USER_MAPPER.updateUserRequest(updateUserRequest,existingUser);
        return USER_MAPPER.toUserDto(userRepository.save(existingUser));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND));
        log.info("Retrieved  user {}", email);
        if (user!=null) {
            return UserDetailsImpl.build(user);
        }
        log.error("Retrieved  user not found {}", email);
        throw new UsernameNotFoundException(email);
    }
}
