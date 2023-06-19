package com.app.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BillDto {
	
	
	private BillDoctorDetailsDto doctor_details;
	
	private List<BillMedicinesDetailsDto> medicines_details;
	
	private BillPathologyDetailsDto pathology_details;
	
	private double totalPaidAmount;
	
	private double totalBedCharges;   // bed charges* no. of days
	
	
	private boolean paidStatus; //
	
	private LocalDate dateOfDischarge;
}
