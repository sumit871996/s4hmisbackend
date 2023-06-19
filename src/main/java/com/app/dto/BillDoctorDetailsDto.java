package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BillDoctorDetailsDto {

	@Length(max = 255)
	@NotBlank
	private String treatmentName;
	
	@Length(max = 255)
	@NotBlank
	private String typeOfTreatment;
	
	@Range(min=1)
	@NotNull
	private Long treatmentCharges;
	
}
