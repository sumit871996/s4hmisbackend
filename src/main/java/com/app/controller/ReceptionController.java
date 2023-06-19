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
import com.app.dto.BedResponseDto;
import com.app.dto.BillBasicResponseDto;
import com.app.dto.BillDto;
import com.app.dto.EmployeeModifyDetailsDto;
import com.app.dto.EmployeeResponseDto;
import com.app.dto.ReceptionBedUpdateDto;
import com.app.entities.Bed;
import com.app.service.IEmployeeService;
import com.app.service.IReceptionService;
import com.app.service.ImageHandlingService;

@RestController
@CrossOrigin("*")
@RequestMapping("/reception")
@Validated
public class ReceptionController {
	
	@Autowired
	private IReceptionService receptionService;
	
	@Autowired
	private IEmployeeService empService;
	
	@Autowired
	private ImageHandlingService imageService;
	
	//get my details
	@GetMapping("/myDetails/{empId}")
	public ResponseEntity<EmployeeResponseDto> getPathologistDetailsById(@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId)
	{
		System.out.println("inside gegRecep controller");
		EmployeeResponseDto receptionist =   empService.getMyDetails(empId);
		  return new ResponseEntity<EmployeeResponseDto>(receptionist, HttpStatus.OK);
	}
	//update my details
	@PutMapping("/updateDetails/{empId}")
	public ResponseEntity<String> updateReceptionistDetails(@RequestBody EmployeeModifyDetailsDto receptionist, @PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId)
	{
		System.out.println(receptionist.toString());
		return new ResponseEntity<String>(empService.updateMyDetails(receptionist, empId), HttpStatus.OK);
	}
	
	
	//get bill by report number
	@GetMapping("/getBill/{reportNumber}")
	public ResponseEntity<BillDto> getBillDetails(@PathVariable @Valid @NotNull Long reportNumber)
	{
		BillDto bdto = receptionService.getBill(reportNumber);
		if(bdto!=null)
		return new ResponseEntity<BillDto>(bdto, HttpStatus.OK);
		else
		return new ResponseEntity<BillDto>(HttpStatus.NO_CONTENT);
	}


	//update paid status + date of discharge
	@GetMapping("/updatePaidStatus/{billNumber}")
	public ResponseEntity<Boolean> updatePaidStatus(@PathVariable @Valid @NotNull Long billNumber)
	{
	System.out.println("inside update paid status");
		return new ResponseEntity<Boolean>(receptionService.updatePaidStatus(billNumber), HttpStatus.OK);
	}

	//add bed number and ward number
	@PutMapping("/bedAllocation/{reportNumber}")
	public ResponseEntity<Boolean> updateReceptionistBedDetails(@RequestBody @Valid ReceptionBedUpdateDto bedUpdate, @PathVariable @Valid @NotNull Long reportNumber)
	{
		boolean isBedAllocated=receptionService.BedAllocation(bedUpdate, reportNumber);
		if(isBedAllocated==true)
		return new ResponseEntity<Boolean>(isBedAllocated, HttpStatus.OK);
		else 
			return new ResponseEntity<Boolean>( HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/getBeds")
	public ResponseEntity<List<Bed>> getAllBedDetails()
	{
		return new ResponseEntity<List<Bed>>(receptionService.getAllBedsDetails(), HttpStatus.OK);
	}
	
	@GetMapping("/getBedsDetails")
	public ResponseEntity<List<BedResponseDto>> getAllBedDetailswithPatients()
	{
		System.out.println(receptionService.getAllBedsWithAllotmentDetails());
		return new ResponseEntity<List<BedResponseDto>>(receptionService.getAllBedsWithAllotmentDetails(), HttpStatus.OK);
	}
	
	//get all paid bills
	@GetMapping("/getPaidBills")
	public ResponseEntity<List<BillBasicResponseDto>> getAllPaidBills()
	{
		List<BillBasicResponseDto> lbill = receptionService.getAllPaidBills();
		if(lbill!=null)
			return new ResponseEntity<List<BillBasicResponseDto>>(lbill,HttpStatus.OK);
		else
			return new ResponseEntity<List<BillBasicResponseDto>>(HttpStatus.NO_CONTENT);
	}
	
	///get all pending bills
	@GetMapping("/getPendingBills")
	public ResponseEntity<List<BillBasicResponseDto>> getAllPendingBills()
	{
		List<BillBasicResponseDto> lbill = receptionService.getAllPendingBills();
		if(lbill!=null)
			return new ResponseEntity<List<BillBasicResponseDto>>(lbill,HttpStatus.OK);
		else
			return new ResponseEntity<List<BillBasicResponseDto>>(HttpStatus.NO_CONTENT);
	}
	
	//upload profile image
	@PostMapping("/{empId}/images")
	@PermitAll
	public ResponseEntity<?> uploadImage(@PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") Long empId, @RequestParam MultipartFile imageFile)
			throws IOException {
		System.out.println("in upload image " + empId + " orig file name " + imageFile.getOriginalFilename() + " size "
				+ imageFile.getSize());
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(imageService.uploadContents(empId, imageFile)));
	}
	//download profile image
	@PermitAll
	@GetMapping(value = "/{empId}/images",produces = 
		{MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
	public ResponseEntity<?> downloadImage( @PathVariable @Valid @NotNull @Range(min = 1, max = 100, message = "employee id must be between range 1-1000") long empId) throws IOException
	{
		System.out.println("in downlaod img "+empId);
		return ResponseEntity.ok(imageService.restoreContents(empId));
	}

}
