package com.solutions.datamart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.solutions.datamart.dto.MediaRegistrationDto;
import com.solutions.datamart.model.Media;
import com.solutions.datamart.repository.MediaRepository;

@Controller
public class MediaController {

    @Autowired
    private MediaRepository mediaRepository;

    @ModelAttribute("media")
    public MediaRegistrationDto mediaRegistrationDto() {
	return new MediaRegistrationDto();
    }

    @GetMapping("/mediaregistration")
    public String showRegistrationForm(Model model) {
	return "addmedia";
    }

    @PostMapping("/mediaregistration")
    public String registerUserAccount(@ModelAttribute("media") @Valid MediaRegistrationDto mediaDto, BindingResult result) {

	Media media = mediaRepository.findByUrl(mediaDto.getRssFeed());
	if (media != null) {
	    result.rejectValue("rssFeed", null, "This media was already registerd in the system");
	}

	if (result.hasErrors()) {
	    return "registration";
	}

	media = new Media();
	media.setName(mediaDto.getName());
	media.setUrl(mediaDto.getRssFeed());
	media.setCountry(mediaDto.getCountry());

	mediaRepository.save(media);

	return "redirect:/addmedia?success";
    }

}