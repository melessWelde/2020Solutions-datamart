package com.solutions.datamart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.solutions.datamart.dto.UpdatePasswordDto;
import com.solutions.datamart.dto.UserRegistrationDto;
import com.solutions.datamart.model.User;
import com.solutions.datamart.repository.UserRepository;
import com.solutions.datamart.service.EmailService;
import com.solutions.datamart.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
	return new UserRegistrationDto();
    }

    @ModelAttribute("updatePassword")
    public UpdatePasswordDto updatePasswordDto() {
	return new UpdatePasswordDto();
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
	return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result) {

	User existing = userService.findByEmail(userDto.getEmail());
	if (existing != null) {
	    result.rejectValue("email", null, "There is already an account registered with that email");
	}

	if (result.hasErrors()) {
	    return "registration";
	}

	userService.save(userDto);
	// send an email to the use being created
	emailService.sendMessage(userDto.getEmail(), userDto.getPassword());

	return "redirect:/registration?success";
    }

    @GetMapping("/updatepassword")
    public String showUpdatePasswordForm(Model model) {
	return "updatepassword";
    }

    @PostMapping("/updatepassword")
    public String updatePassword(@ModelAttribute("user") @Valid UpdatePasswordDto passwordDto, BindingResult result) {

	String email = userService.getLoggedInUser();

	User existing = userService.findByEmail(email);
	if (existing == null) {
	    result.rejectValue("oldPassword", null, "There is no registered account and please login again");
	} else {
	    boolean isCorrect = userService.isOldPasswordCorrect(passwordDto.getOldPassword(), existing.getPassword());
	    if (!isCorrect) {
		result.rejectValue("oldPassword", null, "the old password is not correct");
	    }

	}

	if (result.hasErrors()) {
	    return "registration";
	}

	userRepository.save(existing);

	return "redirect:/updatepassword?success";
    }
}