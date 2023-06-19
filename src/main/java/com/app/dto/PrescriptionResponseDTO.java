package com.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrescriptionResponseDTO {
	private Long doctorId;
	private String docFirstName;
	private String docLastName;
	private List<MedicineResponseDTO> medicines;

}
