package com.app.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BillBasicResponseDto {
	
    private Long billNumber;
    private String patientName;
    private String phone;
    private double totalPaidAmount;
	private LocalDate dateOfAdmission;
	private LocalDate dateOfDischarge;
	private String patientPhone;
	
	private String typeOfTreatment;
	private String treatmentName;
	private Long treatmentCharges;

}
