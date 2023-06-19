package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeModifyDetailsDto {
	
	
	private String firstName;
	private String lastName;
	@NotBlank(message = "Phone number cannot be blank")
	@Length(min = 10, max = 10, message = "Phone number length must be 10 digits")
	private String phone;
	
	private String email;
	private LocalDate dateOfBirth;
	private String buildingName;
	private String streetName;
	private String city;
	private String state;
	private String pinCode;
}
