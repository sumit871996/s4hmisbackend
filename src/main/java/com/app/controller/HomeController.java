package com.app.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.DoctorsGalleryDto;
import com.app.dto.GetSecurityQRequestDTO;
import com.app.dto.HomePageReponseDto;
import com.app.dto.PatientForgetPasswordRequestDTO;
import com.app.dto.PatientNewPasswordDTO;
import com.app.service.IHomeService;
import com.app.service.PatientServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/home")
@Validated
public class HomeController {

	@Autowired
	private IHomeService homeService;

	@Autowired
	private PatientServiceImpl patientservice;

	@PostMapping("/getSecurityQ")
	public ResponseEntity<?> getSecurity(@RequestBody @Valid GetSecurityQRequestDTO getSecurityQRequestDTO) {

		String securityQ = patientservice.getSecurityQuestion(getSecurityQRequestDTO);
		if (securityQ != null) {
			return ResponseEntity.ok(patientservice.getSecurityQuestion(getSecurityQRequestDTO));
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<?> ForgetPassword(@RequestBody @Valid PatientForgetPasswordRequestDTO patientForgetPassword) {
		String msg = "Please enter valid Paramaters";
		String Password = patientservice.ForgetPassword(patientForgetPassword);
		if (Password == null) {
			return new ResponseEntity<String>(msg, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/setpassword")
	public ResponseEntity<?> SetPassword(@RequestBody @Valid PatientNewPasswordDTO patientNewPassword) {

		String Password = patientservice.SetPassword(patientNewPassword);
		if(Password !=null)
		return new ResponseEntity<>(HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	

	@GetMapping("/searchdoctor")
	public ResponseEntity<List<DoctorsGalleryDto>> searchDoctor(@RequestParam @Valid @NotBlank String string) {
		List<DoctorsGalleryDto> listToSend = homeService.searchDoctors(string);

		return new ResponseEntity<List<DoctorsGalleryDto>>(listToSend, HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<HomePageReponseDto> getStaticInfo() {
		HomePageReponseDto homePageResponse = homeService.getStaticInfo();
		if (homePageResponse != null)
			return new ResponseEntity<HomePageReponseDto>(homePageResponse, HttpStatus.OK);
		else
			return new ResponseEntity<HomePageReponseDto>(HttpStatus.NO_CONTENT);

	}

	@GetMapping("/getAllDoctors")
	public ResponseEntity<List<DoctorsGalleryDto>> getAllDoctors() {
		List<DoctorsGalleryDto> listDto = homeService.findAllDoctors();
		if (listDto != null)
			return new ResponseEntity<List<DoctorsGalleryDto>>(listDto, HttpStatus.OK);
		else
			return new ResponseEntity<List<DoctorsGalleryDto>>(HttpStatus.NO_CONTENT);

	}

}
