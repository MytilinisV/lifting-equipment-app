package com.example.liftingequipmentmain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RegisterUserDTO {

    @NotNull
    @Size(min = 2, max = 20)
    private String username;

    @Pattern(regexp = "^.{4,}$", message = "Password must contain at least 4 characters.")
    private String password;

}
