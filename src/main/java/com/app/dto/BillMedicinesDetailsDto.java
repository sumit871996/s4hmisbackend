package com.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BillMedicinesDetailsDto {
	
	private String medicineName;
	private int quantity;
	private int unitCost;

}
