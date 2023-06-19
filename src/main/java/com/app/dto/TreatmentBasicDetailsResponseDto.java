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
public class TreatmentBasicDetailsResponseDto {

	private TreatmentPatientDetailDto patient_details;

	private Long reportNumberToSend;

	private String patientNote;

	private String patientProblem;

	private LocalDate dateOfAppointment;

	private Slot treatmentSlot;

}
