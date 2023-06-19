package com.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageHandlingService {

	String uploadContents(Long Id, MultipartFile imageFile) throws IOException;

	byte[] restoreContents(Long Id) throws IOException;

}
