package com.app.dto;

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
public class TreatmentMedicinesUpdatableListDto {
	
	private String medicineName;
	
	private int quantity;
	
	private double unitCost;
	
	private String dosagePerDay;
	
	private int duration;

}
