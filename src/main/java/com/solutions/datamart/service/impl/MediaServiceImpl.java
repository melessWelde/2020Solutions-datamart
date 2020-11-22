package com.solutions.datamart.service.impl;

@Service("mediaService")
@Transactional
public class MediaServiceImpl implements MediaService {
	@Autowired
	private MediaDao mediaDao;

	@Override
	public long createMedia(Media media) {
		return mediaDao.createMedia(media);
	}

	@Override
	public Media updateMedia(Media media) {
		return mediaDao.updateMedia(media);
	}

	@Override
	public void deleteMedia(long id) {
		mediaDao.deleteMedia(id);

	}

	@Override
	public List<Media> getAllMedias() {
		return mediaDao.getAllMedias();
	}

	@Override
	public Media getMedia(long id) {
		return mediaDao.getMedia(id);
	}

	@Override
	public List<Media> getAllMedias(String search) {
		return mediaDao.getAllMedias(search);
	}

	public List<Media> getAllMediasToSubscribe() {
		return mediaDao.getAllMediasToSubscribe();
	}

}