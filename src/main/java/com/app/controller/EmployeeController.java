package com.app.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponse;
import com.app.dto.DoctorsGalleryDto;
import com.app.service.IHomeService;
import com.app.service.ImageHandlingService;

@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
@Validated
public class EmployeeController {      //patient and employee login page will be different

	@Autowired
	private ImageHandlingService imageService;
	
	@Autowired
	private IHomeService homeService;
	
	@GetMapping("/test")
	public String test()
	{
		return "Welcome!! BED successful!!!";
	}
	
	@PostMapping("/{empId}/images")
	public ResponseEntity<?> uploadImage(@PathVariable @Valid @NotNull Long empId, @RequestParam MultipartFile imageFile)
			throws IOException {
		System.out.println("in upload image " + empId + " orig file name " + imageFile.getOriginalFilename() + " size "
				+ imageFile.getSize());
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(imageService.uploadContents(empId, imageFile)));
//		return ResponseEntity.internalServerError().body(new ApiResponse(imageService.uploadContents(empId, imageFile)));
	}
	
	@GetMapping(value = "/{empId}/images",produces = 
		{MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
	public ResponseEntity<?> downloadImage( @PathVariable @Valid @NotNull long empId) throws IOException
	{
		System.out.println("in downlaod img "+empId);
		return ResponseEntity.ok(imageService.restoreContents(empId));
	}
	
	@GetMapping("/getAllDoctors")
	public ResponseEntity<List<DoctorsGalleryDto>> getAllDoctors()
	{
		List<DoctorsGalleryDto> listDto = homeService.findAllDoctors();
		
		if(listDto != null)
		return new ResponseEntity<List<DoctorsGalleryDto>>(listDto, HttpStatus.OK);
		else
			return new ResponseEntity<List<DoctorsGalleryDto>>(HttpStatus.NO_CONTENT);
			
	}

}
