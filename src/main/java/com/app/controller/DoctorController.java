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
import com.app.dto.TreatmentBasicDetailsResponseDto;
import com.app.dto.TreatmentDetailedResponseDto;
import com.app.dto.UpdatableReportRequestDto;
import com.app.service.IDoctorService;
import com.app.service.IEmployeeService;
import com.app.service.ImageHandlingService;

@RestController
@CrossOrigin("*")
@RequestMapping("/doctor")
@Validated
public class DoctorController {

	@Autowired
	private IEmployeeService empService;

	@Autowired
	private ImageHandlingService imageService;

	@Autowired
	private IDoctorService doctorService;

	@GetMapping("/myDetails/{empId}")
	public ResponseEntity<EmployeeResponseDto> getDoctorDetailsById(
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId) {
		System.out.println("inside getDoct controller");
		EmployeeResponseDto emp = empService.getMyDetails(empId);
		return new ResponseEntity<EmployeeResponseDto>(emp, HttpStatus.OK);
	}

	@PutMapping("/updateDetails/{empId}")
	public ResponseEntity<?> updateDoctorDetails(@RequestBody @Valid EmployeeModifyDetailsDto doctor,
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId) {
		System.out.println(doctor.toString());
		return ResponseEntity.ok(new ApiResponse(empService.updateMyDetails(doctor, empId)));
	}

	@GetMapping("/treatments/{empId}")
	public ResponseEntity<List<TreatmentBasicDetailsResponseDto>> findAllUpcomingAppointments(
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId) {
		System.out.println("inside get");
		return new ResponseEntity<List<TreatmentBasicDetailsResponseDto>>(
				doctorService.findAllUpcomingAppointments(empId), HttpStatus.OK);
	}

	@GetMapping("/viewReport/{reportNumber}")
	public ResponseEntity<TreatmentDetailedResponseDto> getAppointmentByReportNumber(
			@PathVariable @Valid @NotNull Long reportNumber) {
		System.out.println("inside get report details");
		TreatmentDetailedResponseDto dto = doctorService.getAppointmentByReportNumber(reportNumber);
		return new ResponseEntity<TreatmentDetailedResponseDto>(dto, HttpStatus.OK);
	}

	@PostMapping("/updateReport/{reportNumber}")
	public ResponseEntity<?> updatePatientReport(@PathVariable @Valid @NotNull Long reportNumber,
			@RequestBody @Valid UpdatableReportRequestDto dtoToUpdate) {
		return ResponseEntity
				.ok(new ApiResponse(doctorService.updateAppointmentDetailsByReportNumber(reportNumber, dtoToUpdate)));
	}

	
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
