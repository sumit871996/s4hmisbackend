package com.app.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class BookEmergencyAppointmentRequestDto {

	@NotNull
	@Range(min=1001)
	private Long patientId;
	private String patientNote;
	private String patientProblem;
	
}