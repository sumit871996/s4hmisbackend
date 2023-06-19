package com.app.dto;

import java.time.LocalDate;

import com.app.entities.Slot;

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
public class UpcomingAppointmnetResponseDTO 
{
	private Long reportId;
	private String firstName;
	private String lastName;
	private String patientNote;
	private String patientphone;
	
	private LocalDate dateOfAppointment;
	private Slot treatmentSlot;
	private String doctorName;
	private String doctorphone;
	
	
	
	
	
}
