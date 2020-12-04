package com.solutions.datamart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solutions.datamart.model.Media;
import com.solutions.datamart.repository.MediaRepository;
import com.solutions.datamart.service.MediaService;

@Service("mediaService")
@Transactional
public class MediaServiceImpl implements MediaService {

	@Autowired
	private MediaRepository mediaRepository;

	@Override
	public List<Media> getAllMedias() {

		return mediaRepository.findAll();
	}

}