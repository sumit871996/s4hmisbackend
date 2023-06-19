package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.app.entities.BloodGroupEnum;
import com.app.entities.Gender;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString

public class EditPatientRequestDTO {

	@NotBlank
	@Length(min = 10, max = 10)
	private String phone;
	
	private String email;
	
	private BloodGroupEnum bloodGroup;
		
	private Gender gender;
	
//	private String profilePhoto;
	
	private String buildingName;
	
	private String streetName;

	private String city;
	
	private String state;
	
	private String pinCode;
	
	private int feeedback;
	
	private String securityAnswer;
	
	private String securityQuestion;
	private LocalDate dateOfBirth;
	
}
