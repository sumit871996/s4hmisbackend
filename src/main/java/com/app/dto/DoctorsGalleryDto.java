package com.app.dto;

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
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class DoctorsGalleryDto {
	
	private Long empId;
	
	private String firstName;
	
	private String lastName;
	
	
	private String phone;
	
	private String email;
	
	private Gender gender;
	
	private String profilePhoto;
	
	private String education;
	
	private SpecialityEnum speciality;
	
	private DepartmentEnum department;
	
	private Role role;

}
