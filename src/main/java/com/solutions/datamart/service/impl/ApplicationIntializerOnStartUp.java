package com.solutions.datamart.service.impl;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.solutions.datamart.dto.UserRegistrationDto;
import com.solutions.datamart.model.User;
import com.solutions.datamart.service.UserService;

@Component
@Transactional
public class ApplicationIntializerOnStartUp {

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		String email = "tigray_admin@gmail.com";

		User existing = userService.findByEmail(email);
		if (existing != null) {
			System.err.println("Admin user has been already created");

		} else {
			UserRegistrationDto user = new UserRegistrationDto();
			user.setFirstName("Tigray");
			user.setLastName("Prevail");
			user.setEmail(email);
			user.setPassword("123456");
			user.setRole("ADMIN");

			userService.save(user);

			System.err.println("Admin user has been created now");
		}
	}

}
