package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDetailedResponseDto {
     
	private TreatmentPatientDetailDto patient_details;
	
	private Long reportNumber;

	private String patientNote;
	
	private String patientProblem;
	
	private String treatmentName;
	
	private Long treatmentCharges;
	
	private String doctorRemark;

	private String doctorsNote;
	
	private String wardNumber;
	
	private Long bedNumber;

	private String treatmentResult;

	private int bedAllotedForDays;

	private boolean treatmentStatus;
	
}
