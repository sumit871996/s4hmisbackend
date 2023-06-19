package com.app.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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
public class PatientLoginRequestDTO {

	@NotBlank
	private String password;

	@NotBlank(message = "Phone number cannot be blank")
	@Length(min = 10, max = 10, message = "Phone number length must be 10 digits")
	private String phone;
}
