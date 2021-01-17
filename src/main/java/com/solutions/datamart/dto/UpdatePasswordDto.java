package com.solutions.datamart.dto;

import javax.validation.constraints.NotEmpty;

import com.solutions.datamart.validator.FieldMatch;

import lombok.Data;

@FieldMatch.List({ @FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match") })
@Data
public class UpdatePasswordDto {

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String confirmNewPassword;

}