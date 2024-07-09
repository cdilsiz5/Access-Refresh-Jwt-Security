package com.id3.app.controller;

import com.id3.app.dto.UserDto;
import com.id3.app.request.CreateUserRequest;
import com.id3.app.request.UpdateUserRequest;
import com.id3.app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.id3.app.constants.Constant.*;

@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_ID3 + API_USER)
@RequiredArgsConstructor
@Tag(name = "User Resource", description = "ID3 Rest APIs for User")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Create User REST API",
            description = "REST API to create new User inside ID3"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                            schema = @Schema(implementation = UserDto.class),
                            mediaType = "application/json")))
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest user) {
        return userService.createUser(user);
    }

    @Operation(
            summary = "Fetch User Details REST API",
            description = "REST API to fetch User details"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = UserDto.class),
                            mediaType = "application/json")))
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Update User Details REST API",
            description = "REST API to update User details"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = UserDto.class),
                            mediaType = "application/json")))
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest user) {
        return userService.updateUser(id, user);
    }
}
