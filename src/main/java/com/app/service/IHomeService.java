package com.app.service;

import java.util.List;

import com.app.dto.DoctorsGalleryDto;
import com.app.dto.HomePageReponseDto;

public interface IHomeService {

	
	HomePageReponseDto getStaticInfo();
	
	String getAboutUsInfo();
	
	String getPrivacyPolicyInfo();
	
	String getContactUsInfo();
	
	List<DoctorsGalleryDto> findAllDoctors();

	List<DoctorsGalleryDto> searchDoctors(String string);
	
	
}
