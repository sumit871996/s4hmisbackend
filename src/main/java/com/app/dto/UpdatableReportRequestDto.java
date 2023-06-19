package com.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
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
public class UpdatableReportRequestDto {

//	private String typeOfTreatment;

//	private Long reportNumberToSet;

	@NotBlank(message = "treatment name cannot be blank")
	private String treatmentName;

	@NotNull(message = "treatment charges cannot be null")
	private Long treatmentCharges;

	private String doctorRemark;

	private String doctorsNote;

	private String treatmentResult;

	private int bedAllotedForDays;

	private boolean treatmentStatus;

	private List<TreatmentMedicinesUpdatableListDto> medicines_list;

	private String testName;

}
