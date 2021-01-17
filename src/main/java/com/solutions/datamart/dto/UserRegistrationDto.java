package com.solutions.datamart.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.solutions.datamart.validator.FieldMatch;

import lombok.Data;

@FieldMatch.List({ @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
	@FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match") })
@Data
public class UserRegistrationDto {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @Email
    @NotEmpty
    private String email;

    @Email
    @NotEmpty
    private String confirmEmail;

    private String role;

    @AssertTrue
    private Boolean terms;

}