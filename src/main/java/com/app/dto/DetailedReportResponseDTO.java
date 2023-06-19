package com.app.dto;

import java.time.LocalDate;
import java.util.List;

import com.app.entities.Slot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DetailedReportResponseDTO {
	
	private String docfirstName;
	private String doclastName;
	private String patientNote;
	private String typeoftreatment;
	private String treatmentName;
	private LocalDate dateOfAppointment;
	
	private String pathfirstName;
	private String pathlastName;
	private String patientfirstName;
	private String patientlastName;
	
	List<MedicineResponseDTO> medicines;
	private Long reportNo;
	
	private Slot treatmentSlot;
	private String pathologyTest;
	
	
	
}
