package com.app.dto;

import java.time.LocalDate;

import com.app.entities.BloodGroupEnum;
import com.app.entities.DepartmentEnum;
import com.app.entities.Gender;
import com.app.entities.Role;
import com.app.entities.SpecialityEnum;

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
public class EmployeeResponseDto {
	
	private Long empId;
	        
	private String firstName;
	
	private String lastName;

	private String phone;

	private String email;

	private BloodGroupEnum bloodGroup;
	
	private String password;

	private Gender gender;
	
	private String profilePhoto;
	
	private LocalDate dateOfBirth;
	
	private String buildingName;
	
	private String streetName;
	
	private String city;

	private String state;

	private String pinCode;
	
	private String education;
	
	private SpecialityEnum speciality;
	
	private DepartmentEnum department;
	
	private Role role;

}
