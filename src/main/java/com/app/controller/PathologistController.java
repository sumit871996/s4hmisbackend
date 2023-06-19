package com.app.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponse;
import com.app.dto.EmployeeModifyDetailsDto;
import com.app.dto.EmployeeResponseDto;
import com.app.dto.TestBasicResponseDto;
import com.app.dto.TestDetailedResponseDto;
import com.app.dto.UpdatableTestRequestDto;
import com.app.service.IEmployeeService;
import com.app.service.IPathologyService;
import com.app.service.ImageHandlingService;

@RestController
@CrossOrigin("*")
@RequestMapping("/pathologist")
@Validated
public class PathologistController {

	@Autowired
	private IPathologyService pathoService;

	@Autowired
	private IEmployeeService empService;

	@Autowired
	private ImageHandlingService imageService;

	// get my details
	@GetMapping("/myDetails/{empId}")
	public ResponseEntity<EmployeeResponseDto> getPathologistDetailsById(
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId) {
		System.out.println("inside getPatho controller");
		EmployeeResponseDto pathologist = empService.getMyDetails(empId);
		return new ResponseEntity<EmployeeResponseDto>(pathologist, HttpStatus.OK);
	}

	// update my details
	@PutMapping("/updateDetails/{empId}")
	public ResponseEntity<?> updateReceptionistDetails(@RequestBody @Valid EmployeeModifyDetailsDto pathologist,
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId) {
		System.out.println(pathologist.toString());
		return ResponseEntity.ok(new ApiResponse(empService.updateMyDetails(pathologist, empId)));
	}

	// find all upcoming tests
	@GetMapping("/tests/{empId}")
	public ResponseEntity<List<TestBasicResponseDto>> findAllUpcomingTests(
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId) {
		System.out.println("inside get");
		return new ResponseEntity<List<TestBasicResponseDto>>(pathoService.findAllUpcomingTests(empId), HttpStatus.OK);
	}

	// view report details by report number
	@GetMapping("/viewReport/{reportNumber}")
	public ResponseEntity<TestDetailedResponseDto> getTestByReportNumber(
			@PathVariable @Valid @NotNull Long reportNumber) {
		System.out.println("inside get report details");
		TestDetailedResponseDto dto = pathoService.getTestByReportNumber(reportNumber);
		return new ResponseEntity<TestDetailedResponseDto>(dto, HttpStatus.OK);
	}

	// update report by report number
	@PutMapping("/updateReport/{reportNumber}")
	public ResponseEntity<?> updatePatientReport(@PathVariable @Valid @NotNull Long reportNumber,
			@RequestBody @Valid UpdatableTestRequestDto dtoToUpdate) {
		return ResponseEntity.ok(pathoService.updateTestDetailsByReportNumber(reportNumber, dtoToUpdate));
	}

	// set profile photo
	@PostMapping("/{empId}/images")
	@PermitAll
	public ResponseEntity<?> uploadImage(
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId,
			@RequestParam MultipartFile imageFile) throws IOException {
		System.out.println("in upload image " + empId + " orig file name " + imageFile.getOriginalFilename() + " size "
				+ imageFile.getSize());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse(imageService.uploadContents(empId, imageFile)));
	}

	// download profile photo
	@PermitAll
	@GetMapping(value = "/{empId}/images", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> downloadImage(
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") long empId)
			throws IOException {
		System.out.println("in downlaod img " + empId);
		return ResponseEntity.ok(imageService.restoreContents(empId));
	}

}
