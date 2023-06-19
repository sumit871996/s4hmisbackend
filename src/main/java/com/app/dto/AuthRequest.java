package com.app.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthRequest {
	
	@NotBlank(message = "phone no. cant be blank")
	@Length(min = 10, max = 10, message = "Length of phone must be 10 digits")
	private String phone;
	
	public String getPhone() {
		return phone;
	}
	
	public String getPassword() {
		return password;
	}
	
	@NotBlank(message = "password can't be blank or null")
	private String password;
}
