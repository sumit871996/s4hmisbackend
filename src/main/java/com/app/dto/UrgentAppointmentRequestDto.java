package com.app.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

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
public class UrgentAppointmentRequestDto {
	
	public Long getPatientId() {
		return patientId;
	}

	// to receive from patient
	@NotNull(message = "patient id cant be null")
	@Range(min = 1001, message = "patient id must be above 1000")
	private Long patientId;

	private String patientProblem;

	private String patientNote;

	// to be set automatically
	// private Employee employee;
	// private String typeOfTreatment;
	// private Bill reportNumber;
	// private String treatmentName;
	// private LocalDate dateOfAppointment;
	// private Slot treatmentSlot;
	// private Long bedNumber;
	// private String wardNumber;

}
