package com.app.dto;

import javax.validation.constraints.NotBlank;

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
public class newEmpPasswordDto {
	
	public String getChangePassword() {
		return changePassword;
	}
	
	@NotBlank
	private String changePassword;

}
