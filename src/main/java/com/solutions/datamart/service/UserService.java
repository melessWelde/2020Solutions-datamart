package com.solutions.datamart.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.solutions.datamart.dto.UserRegistrationDto;
import com.solutions.datamart.model.User;

public interface UserService extends UserDetailsService {

	User findByEmail(String email);

	User save(UserRegistrationDto registration);
}