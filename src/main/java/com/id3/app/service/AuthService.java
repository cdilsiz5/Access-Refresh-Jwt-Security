package com.id3.app.service;

import com.id3.app.request.LoginRequest;
import com.id3.app.request.TokenRefreshRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> Login(LoginRequest request);
    ResponseEntity<?> logoutUser(Long userID) ;
    ResponseEntity<?> refreshToken(TokenRefreshRequest request) ;


    }
