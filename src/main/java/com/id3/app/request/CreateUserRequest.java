package com.id3.app.request;

import com.id3.app.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank(message = "First name cannot be empty")
    private String name;

    @NotBlank(message = "Last name cannot be empty")
    private String surname;

    private String identificationNumber;

    @Email(message = "Please provide a valid email")
    private String email;

    private UserRole userRole;

    @NotBlank
    @Size(max = 120)
    private String password;

}
