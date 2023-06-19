package com.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.customExceptions.ResourceNotFoundException;
import com.app.dao.EmployeeRespository;
import com.app.dao.PatientRepository;
import com.app.entities.Employee;
import com.app.entities.Patient;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ImageHandlingServiceImpl implements ImageHandlingService {

	@Autowired
	private EmployeeRespository empRepo;
	
	@Autowired
	private PatientRepository patientRepo;
	
	// SpEL
	@Value("${file.upload.folder}")
	private String folder;

	@PostConstruct
	public void anyInit() {
		log.info("in init {} ", folder);
		File dir = new File(folder);
		if (!dir.exists())
			log.info("dir created {} ", dir.mkdirs());
		else
			log.info("dir alrdy exists.... ");
	}

	@Override
	public String uploadContents(Long Id, MultipartFile imageFile) throws IOException {
		Employee emp = null;
		Patient pat=null;
		if(Id <=1000)
		emp = empRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Invalid Emp Id"));
		else
		pat = patientRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Invalid Patient Id"));

		System.out.println(imageFile);
		System.out.println("/".concat(folder).concat("/"));
		
		String imagePath = "/home/ubuntu/backend_s4/images/".concat(imageFile.getOriginalFilename());

		log.info("bytes copied {} ",
				Files.copy(imageFile.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING));
		
		if(Id <=1000 && emp!=null)
		emp.setProfilePhoto(imagePath);
		if(Id > 1000 && pat !=null)
		pat.setProfilePhoto(imagePath);
		return "Image updated successfully!!!";
	}

	@Override
	public byte[] restoreContents(Long Id) throws IOException{

		Employee emp = null;
		Patient pat=null;
		if(Id <=1000)
		emp = empRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Invalid Emp Id"));
		else
		pat = patientRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Invalid Patient Id"));
		

		if(emp != null && emp.getProfilePhoto() == null)
		{
			throw new ResourceNotFoundException("Image doesn't exist");
		}
		if(pat != null && pat.getProfilePhoto() == null)
			throw new ResourceNotFoundException("Image doesn't exist");

		if(emp!= null)
		return Files.readAllBytes(Paths.get(emp.getProfilePhoto()));
		else
		return Files.readAllBytes(Paths.get(pat.getProfilePhoto()));
	}


}
