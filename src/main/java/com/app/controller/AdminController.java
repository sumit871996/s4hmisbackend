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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.app.dto.newEmpPasswordDto;
import com.app.entities.Bed;
import com.app.entities.Employee;
import com.app.service.IAdminService;
import com.app.service.IEmployeeService;
import com.app.service.IReceptionService;
import com.app.service.ImageHandlingService;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
@Validated
public class AdminController {

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IEmployeeService empService;

	@Autowired
	private ImageHandlingService imageService;

	@Autowired
	private IReceptionService receptionServ;

	@PostMapping("/addEmployee")
	public ResponseEntity<Employee> AddEmployee(@RequestBody @Valid Employee emp) {
		Employee e = adminService.AddEmployee(emp);

		return new ResponseEntity<Employee>(e, HttpStatus.CREATED);
	}
	
	@PutMapping("/changeEmpPass/{empId}")
	public ResponseEntity<?> UpdateEMployeePassword(@RequestBody @Valid newEmpPasswordDto empPass, @PathVariable @Valid @Range(min=1, max = 1000) Long empId) {
		
		String s = adminService.changeEmpPassword(empId, empPass.getChangePassword());
		if(s !=null)
			return new ResponseEntity<>(HttpStatus.OK);
			else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<?> DeleteEmployee(
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long id) {
		boolean result = adminService.DeleteEmployeeById(id);
		if (result) {
			return ResponseEntity.ok(new ApiResponse("Doctor deleted SuccessFully !!!!!!!!!"));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/updateEmployee")
	public ResponseEntity<?> UpdateEmployee(@RequestBody @Valid Employee emp) {
		boolean result = adminService.UpdateEmployee(emp);
		if (result) {
			return ResponseEntity.ok(new ApiResponse("Employee updated SuccessFully !!!!!!!!!"));
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/myDetails/{empId}")
	public ResponseEntity<EmployeeResponseDto> getDoctorDetailsById(
			@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId) {
		System.out.println("inside getDoct controller");
		EmployeeResponseDto emp = empService.getMyDetails(empId);
		return new ResponseEntity<EmployeeResponseDto>(emp, HttpStatus.OK);
	}

	@PutMapping("/updateDetails/{empId}")
	public ResponseEntity<?> updateDoctorDetails(@RequestBody @Valid EmployeeModifyDetailsDto doctor,
			@PathVariable Long empId) {
		System.out.println(doctor.toString());
		return ResponseEntity.ok(new ApiResponse(empService.updateMyDetails(doctor, empId)));
	}

	@PostMapping("/addbeds")
	public ResponseEntity<Long> AddBeds(@RequestBody @Valid Bed beds) {
		Long bedNo = adminService.AddBeds(beds);

		return new ResponseEntity<Long>(bedNo, HttpStatus.OK);
	}

	@PermitAll
	@PostMapping("/{empId}/images")
	public ResponseEntity<?> uploadImage(
			@Range(min = 1, max = 1000) @PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId,
			@RequestParam MultipartFile imageFile) throws IOException {
		System.out.println("in upload image " + empId + " orig file name " + imageFile.getOriginalFilename() + " size "
				+ imageFile.getSize());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse(imageService.uploadContents(empId, imageFile)));
//		return ResponseEntity.internalServerError().body(new ApiResponse(imageService.uploadContents(empId, imageFile)));
	}

	@PermitAll
	@GetMapping(value = "/{empId}/images", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> downloadImage(@Range(min = 1, max = 1000) @PathVariable long empId) throws IOException {
		System.out.println("in downlaod img " + empId);
		return ResponseEntity.ok(imageService.restoreContents(empId));
	}

	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
		List<EmployeeResponseDto> employeesList = adminService.getListEmployees();
		if (employeesList != null) {
			return new ResponseEntity<List<EmployeeResponseDto>>(employeesList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<EmployeeResponseDto>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/getAllBeds")
	public ResponseEntity<List<Bed>> getAllBeds() {
		List<Bed> bedList = receptionServ.getAllBedsDetails();
		if (bedList != null) {
			return new ResponseEntity<List<Bed>>(bedList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Bed>>(HttpStatus.NO_CONTENT);
		}
	}

}
