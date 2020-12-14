package com.solutions.datamart.service;

import com.solutions.datamart.model.Media;

public interface RecordService {

    void createRecord(Media media);

    void getAllMedias();
}
