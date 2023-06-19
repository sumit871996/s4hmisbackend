package com.app.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.BillBasicResponseDto;
import com.app.dto.BillDto;
import com.app.dto.BookAppointmentRequestDTO;
import com.app.dto.BookPathologyAppointRequestDTO;
import com.app.dto.DetailedReportResponseDTO;
import com.app.dto.EditPatientRequestDTO;
import com.app.dto.PathologyDetailsResponseDTO;
import com.app.dto.PrescriptionResponseDTO;
import com.app.dto.ReportHistoryResponseDTO;
import com.app.dto.UpcomingAppointmnetResponseDTO;
import com.app.dto.UrgentAppointmentRequestDto;
import com.app.dto.UrgentAppointmentResponseDto;
import com.app.entities.Employee;
//import com.app.dto.EditPatientDTO;
import com.app.entities.Patient;
import com.app.service.IPatientService;

@RestController
@CrossOrigin
@RequestMapping("/patient")
@Validated
public class PatientController {

	@Autowired
	private IPatientService patientservice;

	@PostMapping("/bookEmergencyAppointment")
	public ResponseEntity<UrgentAppointmentResponseDto> bookUrgentApt(
			@RequestBody @Valid UrgentAppointmentRequestDto urgentRequest) {
		if (urgentRequest.getPatientId() < 1000)
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		UrgentAppointmentResponseDto responseUrgent = patientservice.bookUrgentAppointment(urgentRequest.getPatientId(),
				urgentRequest);

		return ResponseEntity.ok(responseUrgent);
	}

	@PutMapping("/editprofile/{id}")
	public ResponseEntity<Patient> EditProfile(@RequestBody @Valid EditPatientRequestDTO patient,
			@PathVariable @Valid @NotNull @Range(min = 1001, message = "patient id must be greater than 1000") Long id) {
		Patient newPatient = patientservice.EditProfile(patient, id);
		if (newPatient != null) {
			return new ResponseEntity<Patient>(newPatient, HttpStatus.OK);
		} else
			return new ResponseEntity<Patient>(newPatient, HttpStatus.NO_CONTENT);
	}

	@PostMapping("/bookappointment")
	public ResponseEntity<Long> BookAppointment(@RequestBody @Valid BookAppointmentRequestDTO appointmentdetails) {
		Long AppointmentId = patientservice.BookAppointmentApi(appointmentdetails);

		return new ResponseEntity<Long>(AppointmentId, HttpStatus.OK);

	}

	@GetMapping("/upcomingAppointments/{id}")
	public ResponseEntity<List<UpcomingAppointmnetResponseDTO>> UpcomingAppointments(
			@PathVariable @Valid @NotNull @Range(min = 1001, message = "patient id must be greater than 1000") Long id) {
		List<UpcomingAppointmnetResponseDTO> comingAppointments = patientservice.UpcomingAppointments(id);

		if (comingAppointments != null) {
			return new ResponseEntity<List<UpcomingAppointmnetResponseDTO>>(comingAppointments, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<UpcomingAppointmnetResponseDTO>>(comingAppointments, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/showprescription/{reportNo}")
	public ResponseEntity<PrescriptionResponseDTO> ViewPrescription(@PathVariable @Valid @NotNull Long reportNo) {
		PrescriptionResponseDTO prescription = patientservice.viewPrescription(reportNo);
		if (prescription != null) {
			return new ResponseEntity<PrescriptionResponseDTO>(prescription, HttpStatus.OK);
		} else {
			return new ResponseEntity<PrescriptionResponseDTO>(prescription, HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/bookpathology")
	public ResponseEntity<PathologyDetailsResponseDTO> BookPathologyAppointment(
			@RequestBody @Valid BookPathologyAppointRequestDTO BookPathologyAppoint) {
		PathologyDetailsResponseDTO pathologyAppointment = patientservice
				.BookPathologyAppointment(BookPathologyAppoint);

		return new ResponseEntity<PathologyDetailsResponseDTO>(pathologyAppointment, HttpStatus.OK);
	}

	@GetMapping("/getPathology/{reportNo}")
	public ResponseEntity<PathologyDetailsResponseDTO> getExistingPathologyDetails(
			@PathVariable @Valid @NotNull Long reportNo) {
		PathologyDetailsResponseDTO existingPathData = patientservice.getpathologyDetails(reportNo);
		if (existingPathData != null) {
			return new ResponseEntity<PathologyDetailsResponseDTO>(existingPathData, HttpStatus.OK);
		} else
			return new ResponseEntity<PathologyDetailsResponseDTO>(existingPathData, HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getReportHistory/{id}")
	public ResponseEntity<List<ReportHistoryResponseDTO>> GetAllHistoryResport(
			@PathVariable @Valid @NotNull @Range(min = 1001, message = "patient id must be greater than 1000") Long id) {
		List<ReportHistoryResponseDTO> reports = patientservice.ReportHistory(id);

		if (reports != null) {
			return new ResponseEntity<List<ReportHistoryResponseDTO>>(reports, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ReportHistoryResponseDTO>>(reports, HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("/getBill/{reportNumber}")
	public ResponseEntity<BillDto> getBillDetails(@PathVariable @Valid @NotNull Long reportNumber) {
		BillDto bdto = patientservice.getBill(reportNumber);
		if (bdto != null)
			return new ResponseEntity<BillDto>(bdto, HttpStatus.OK);
		else
			return new ResponseEntity<BillDto>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getPendingBills/{id}")
	public ResponseEntity<List<BillBasicResponseDto>> getAllPendingBills(
			@PathVariable @Valid @NotNull @Range(min = 1001, message = "patient id must be greater than 1000") Long id) {
		List<BillBasicResponseDto> lbill = patientservice.getAllBillsByStatus(id, false);
		if (lbill != null)
			return new ResponseEntity<List<BillBasicResponseDto>>(lbill, HttpStatus.OK);
		else
			return new ResponseEntity<List<BillBasicResponseDto>>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getPaidBills/{id}")
	public ResponseEntity<List<BillBasicResponseDto>> getAllPaidBills(
			@PathVariable @Valid @NotNull @Range(min = 1001, message = "patient id must be greater than 1000") Long id) {
		List<BillBasicResponseDto> lbill = patientservice.getAllBillsByStatus(id, true);
		if (lbill != null)
			return new ResponseEntity<List<BillBasicResponseDto>>(lbill, HttpStatus.OK);
		else
			return new ResponseEntity<List<BillBasicResponseDto>>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getBillsByStatus/{id}/{status}")
	public ResponseEntity<List<BillBasicResponseDto>> getAllPendingBills(
			@PathVariable @Valid @NotNull @Range(min = 1001, message = "patient id must be greater than 1000") Long id,
			@PathVariable @Valid @NotNull boolean status) {
		List<BillBasicResponseDto> lbill = patientservice.getAllBillsByStatus(id, status);
		if (lbill != null)
			return new ResponseEntity<List<BillBasicResponseDto>>(lbill, HttpStatus.OK);
		else
			return new ResponseEntity<List<BillBasicResponseDto>>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getAlldoctors")
	public ResponseEntity<List<Employee>> getAllDoctors() {
		List<Employee> doctorList = patientservice.getDoctorList();
		if (doctorList != null) {
			return new ResponseEntity<List<Employee>>(doctorList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Employee>>(doctorList, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/getAllPathology/{patientId}")
	public ResponseEntity<List<PathologyDetailsResponseDTO>> getAllPathologyDetails(
			@PathVariable @Valid @NotNull @Range(min = 1001, message = "patient id must be greater than 1000") Long patientId) {
		List<PathologyDetailsResponseDTO> existingPathData = patientservice.getAllPathologyDetails(patientId);
		if (existingPathData != null) {
			return new ResponseEntity<List<PathologyDetailsResponseDTO>>(existingPathData, HttpStatus.OK);
		} else
			return new ResponseEntity<List<PathologyDetailsResponseDTO>>(existingPathData, HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getAllpathologist")
	public ResponseEntity<List<Employee>> getAllPathologist() {
		List<Employee> pathologistList = patientservice.getPathologistList();
		if (pathologistList != null) {
			return new ResponseEntity<List<Employee>>(pathologistList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Employee>>(pathologistList, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/getmyDetails/{patientId}")
	public ResponseEntity<Patient> getPatientFullDetails(
			@PathVariable @Valid @NotNull @Range(min = 1001, message = "patient id must be greater than 1000") Long patientId) {
		Patient p = patientservice.getMydetails(patientId);

		if (p != null) {
			return new ResponseEntity<Patient>(p, HttpStatus.OK);
		} else {
			return new ResponseEntity<Patient>(p, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/getDetailedReport/{reportNo}")
	public ResponseEntity<DetailedReportResponseDTO> getDetailedReport(@PathVariable @Valid @NotNull Long reportNo) {
		DetailedReportResponseDTO reportdetails = patientservice.detailedReportDetails(reportNo);
		if (reportdetails != null) {
			return new ResponseEntity<DetailedReportResponseDTO>(reportdetails, HttpStatus.OK);
		} else {
			return new ResponseEntity<DetailedReportResponseDTO>(reportdetails, HttpStatus.NO_CONTENT);
		}

	}

}
