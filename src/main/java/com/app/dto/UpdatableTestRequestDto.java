package com.app.dto;

import javax.validation.constraints.NotNull;

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
public class UpdatableTestRequestDto {

	@NotNull(message = "test charges cant be null")
	private double testCharges;

	private String pathologistRemark;

	private boolean testStatus;

}
