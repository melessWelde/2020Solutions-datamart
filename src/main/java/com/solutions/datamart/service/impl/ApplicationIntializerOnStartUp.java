package com.solutions.datamart.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.solutions.datamart.dto.UserRegistrationDto;
import com.solutions.datamart.model.Role;
import com.solutions.datamart.model.User;
import com.solutions.datamart.repository.RoleRepository;
import com.solutions.datamart.service.UserService;

@Component
@Transactional
public class ApplicationIntializerOnStartUp {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
	String email = "tigray_admin@gmail.com";

	Role adminRole = roleRepository.findByRole("ADMIN");
	if (adminRole == null) {
	    adminRole = new Role();
	    adminRole.setRole("ADMIN");
	    adminRole.setCreatedBy("SYSTEM");
	    adminRole.setCreatedDate(new Date());

	    roleRepository.save(adminRole);

	    System.err.println("ADMIN role was created now");
	}

	Role userRole = roleRepository.findByRole("USER");
	if (userRole == null) {
	    userRole = new Role();
	    userRole.setRole("USER");
	    userRole.setCreatedBy("SYSTEM");
	    userRole.setCreatedDate(new Date());

	    roleRepository.save(userRole);

	    System.err.println("USER role was created now");
	}

	User existing = userService.findByEmail(email);
	if (existing == null) {
	    UserRegistrationDto user = new UserRegistrationDto();
	    user.setFirstName("Tigray");
	    user.setLastName("Prevail");
	    user.setEmail(email);
	    user.setPassword("123456");
	    user.setRole("ADMIN");

	    userService.save(user);

	    System.err.println("user was created now");
	}
    }

}
