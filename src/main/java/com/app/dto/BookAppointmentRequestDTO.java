package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.app.entities.Slot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookAppointmentRequestDTO {
	
	@NotNull
	@Range(min = 1001)
	private Long patientId;
	
	@NotNull
	@Range(min=2, max = 1000)
	private Long doctorId;
	

	private String doctorFname;

	private String doctorLname;
	
	private Slot treatmentSlot;
	
	private String typeOfTreatment;
	
	private String patientNote;
	
	private String patientProblem;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfAppointment;
	
}
