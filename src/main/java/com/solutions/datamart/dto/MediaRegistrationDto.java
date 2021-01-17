package com.solutions.datamart.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MediaRegistrationDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String rssFeed;

    @NotEmpty
    private String country;

}