package com.app.dto;

import java.time.LocalDate;

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
public class UrgentAppointmentResponseDto {

	// to send back to patient
	private Long reportNumber;
	private String treatmentSlot;
	private Long bedNumber;
	private String wardNumber;
	private LocalDate dateOfAppointment;

}
