package com.id3.app.controller;

import com.id3.app.dto.AccountDto;
import com.id3.app.request.CreateAccountRequest;
import com.id3.app.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.id3.app.constants.Constant.*;

@RestController
@RequestMapping(API_PREFIX+API_VERSION_V1+API_ID3+API_ACCOUNT)
@RequiredArgsConstructor
@Tag(name = "Account Resource", description = "ID3 Rest APIs for Account")
public class AccountController {

    private final AccountService accountService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create a new account for a user"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                            schema = @Schema(implementation = AccountDto.class),
                            mediaType = "application/json")))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestBody CreateAccountRequest account) {
        return accountService.createAccount(account);
    }

    @Operation(
            summary = "Fetch Accounts by User ID REST API",
            description = "REST API to fetch all accounts of a user by user ID"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = AccountDto.class),
                            mediaType = "application/json")))
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> getAccountsByUserId(@PathVariable Long userId) {
        return accountService.getAccountsByUserId(userId);
    }

    @Operation(
            summary = "Delete Account REST API",
            description = "REST API to delete an account by ID"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NO CONTENT"))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @Operation(
            summary = "Transfer Balance REST API",
            description = "REST API to transfer balance between two accounts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status BAD REQUEST",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public void transferBalance(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam Double amount) {
        accountService.transferBalance(fromAccountId, toAccountId, amount);
    }
}
