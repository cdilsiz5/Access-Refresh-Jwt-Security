package com.id3.app.controller;



import com.id3.app.request.LoginRequest;
import com.id3.app.request.TokenRefreshRequest;
import com.id3.app.response.JwtResponse;
import com.id3.app.security.service.JwtService;
import com.id3.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.id3.app.constants.Constant.*;


@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1+API_ID3+API_AUTHENTICATION)
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST APIs for Auth ",
        description = "CRUD REST APIs For Auth"
)
@Slf4j
@CrossOrigin(origins = "${spring.api-url}")//
public class AuthController {
    private final AuthService service;
    private final JwtService jwtService;

    @Operation(
            summary = "Login User ",
            description = "REST API to Login "
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = JwtResponse.class),
                            mediaType = "application/json")))
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return service.Login(loginRequest);
    }
    @Operation(
            summary = "Validate token",
            description = "Rest Api To Validate User Token")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Validate token",
            content = @Content(
                    mediaType = "application/json")))
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/validate")
    public boolean validateToken(@RequestParam("token") String token) {
        return   jwtService.validateToken(token);

    }
    @Operation(
            summary = "Refresh Token Request",
            description = "REST API to get Refresh Token "
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = TokenRefreshRequest.class),
                            mediaType = "application/json")))
    @PostMapping("/refreshToken")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return service.refreshToken(request);
    }
    @Operation(
            summary = "Logout User Request",
            description = "REST API to get logout user Token "
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                             mediaType = "application/json")))
    @PostMapping("/logout/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> logoutUser(@PathVariable Long userId) {
        return service.logoutUser(userId);
    }

}
