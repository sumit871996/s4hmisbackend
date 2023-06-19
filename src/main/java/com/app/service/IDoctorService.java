package com.app.service;

import java.util.List;

import com.app.dto.TreatmentBasicDetailsResponseDto;
import com.app.dto.TreatmentDetailedResponseDto;
import com.app.dto.UpdatableReportRequestDto;

public interface IDoctorService {
	

	
	// get all upcoming appointments details by doctorId(sort by date and then by time)
		List<TreatmentBasicDetailsResponseDto> findAllUpcomingAppointments(Long empId);
		
	//update patient report details by report number (send DTO body)
		String updateAppointmentDetailsByReportNumber(Long reportNumber, UpdatableReportRequestDto dto);
		
	// get appointment details DTO of specific report of specific doctor	
		TreatmentDetailedResponseDto getAppointmentByReportNumber(Long reportNumber);

		
	
		
		
		
	

}
