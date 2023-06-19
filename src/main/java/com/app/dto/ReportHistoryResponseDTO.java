package com.app.dto;

import java.time.LocalDate;

import com.app.entities.Slot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReportHistoryResponseDTO 
{
	private Long reportNo;
	private String patientfirstName;
	private String patientlastName;
	
	private String patientphone;
	private LocalDate dateOfAppointment;
	private Slot treatmentSlot;
	
	private String docFirstName;
	private String docLastName;
	private String doctorphone;
	
	private String doctorRemark;
	
	private boolean treatmentStatus;
}
