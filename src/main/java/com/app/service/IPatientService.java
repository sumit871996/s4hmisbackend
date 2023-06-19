package com.app.service;

import java.util.List;

import com.app.dto.BillBasicResponseDto;
import com.app.dto.BillDto;
import com.app.dto.BookAppointmentRequestDTO;
import com.app.dto.BookPathologyAppointRequestDTO;
import com.app.dto.DetailedReportResponseDTO;
import com.app.dto.EditPatientRequestDTO;
import com.app.dto.GetSecurityQRequestDTO;
import com.app.dto.PathologyDetailsResponseDTO;
import com.app.dto.PatientForgetPasswordRequestDTO;
import com.app.dto.PatientLoginRequestDTO;
import com.app.dto.PatientNewPasswordDTO;
import com.app.dto.PrescriptionResponseDTO;
import com.app.dto.ReportHistoryResponseDTO;
import com.app.dto.UpcomingAppointmnetResponseDTO;
import com.app.dto.UrgentAppointmentRequestDto;
import com.app.dto.UrgentAppointmentResponseDto;
import com.app.entities.Employee;
import com.app.entities.Patient;

public interface IPatientService 
{

	 Patient signUP(Patient patient);
	 
	 Patient EditProfile(EditPatientRequestDTO patient,Long id);
	 
	 String SetPassword(PatientNewPasswordDTO newPasswordDto);
	 
	 Long BookAppointmentApi(BookAppointmentRequestDTO appointmentdetails);
	 
	 String ForgetPassword(PatientForgetPasswordRequestDTO patientForgetPassword);
	
	 List<UpcomingAppointmnetResponseDTO> UpcomingAppointments(Long patientId);
	 
	 PrescriptionResponseDTO viewPrescription(Long reportnumber);
	 
	 PathologyDetailsResponseDTO BookPathologyAppointment(BookPathologyAppointRequestDTO BookPathologyAppoint);

	 PathologyDetailsResponseDTO getpathologyDetails(Long reportNo);
	 
	 List<ReportHistoryResponseDTO> ReportHistory(Long patientId);
	 
	 Patient PatientLogin(PatientLoginRequestDTO patientLoginRequestDTO);
	 
	 String getSecurityQuestion(GetSecurityQRequestDTO getSecurityQRequestDTO);
	 
	 BillDto getBill(Long billNumber); 
	 
	 List<BillBasicResponseDto> getAllBillsByStatus(Long patientId,boolean status);
	 
	 List<Employee> getDoctorList();
	 
	 List<PathologyDetailsResponseDTO> getAllPathologyDetails(Long patientID);
	 
	 List<Employee> getPathologistList();
	 
	 Patient getMydetails(Long patientId);
	 
	 DetailedReportResponseDTO detailedReportDetails(Long reportNo);
	 
	 UrgentAppointmentResponseDto bookUrgentAppointment(Long patientId, UrgentAppointmentRequestDto urgentRequest);
	 
	 
	 
	 
	 

}

