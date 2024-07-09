package com.id3.app.service.impl;

import com.id3.app.exception.TokenRefreshException;
import com.id3.app.exception.UserNotFoundException;
import com.id3.app.model.RefreshToken;
import com.id3.app.model.User;
import com.id3.app.repository.UserRepository;
import com.id3.app.request.LoginRequest;
import com.id3.app.request.TokenRefreshRequest;
import com.id3.app.response.JwtResponse;
import com.id3.app.response.TokenRefreshResponse;
import com.id3.app.security.UserDetailsImpl;
import com.id3.app.security.service.JwtService;
import com.id3.app.security.service.RefreshTokenService;
import com.id3.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.id3.app.constants.Constant.USER_NOT_FOUND;
import static com.id3.app.mapper.UserMapper.USER_MAPPER;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;



    public ResponseEntity<?> Login(LoginRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getUserPassword()));
            log.info("done authenticate user :{}", request.getUserEmail());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String jwt = jwtService.generateJwtToken(userDetails);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            User user = userRepository.findByEmail(request.getUserEmail()).orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND));
            return ResponseEntity.ok(new JwtResponse(jwt,refreshToken.getToken(), USER_MAPPER.toUserDto(user)));

        } catch (DisabledException e) {
            log.error("done authentication failed user :{}", request.getUserEmail());
            throw new DisabledException("USER_ACCOUNT_DISABLED");
        } catch (BadCredentialsException e) {
            log.error("done authentication failed user :{}", request.getUserEmail());
            throw new BadCredentialsException("WRONG_USERNAME_OR_PASSWORD");
        } catch (UsernameNotFoundException e) {
            log.error("done authentication failed user :{}", request.getUserEmail());
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }

    }
    public ResponseEntity<?> logoutUser(Long userId) {
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok("Log out successful!");
    }
    public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateTokenFromUsername(user.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}

