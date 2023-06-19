package com.app.dto;

import java.time.LocalDate;

import com.app.entities.BloodGroupEnum;
import com.app.entities.Gender;

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
public class TreatmentPatientDetailDto {
	
	private String firstName;
	
	private String lastName;
	
	private String phone;

	private BloodGroupEnum bloodGroup;

	private Gender gender;
	
	private LocalDate dateOfBirth;
	
	private String profilePhoto;

}
