package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class ReceptionBedUpdateDto {

	@NotBlank
	@Length(min = 1, max = 1, message = "Ward number must be single character")
	private String wardNumber;

	@NotNull
	private Long bedNumber;

}
