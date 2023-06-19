package com.app.dto;

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
public class BedResponseDto {
	
	private Long bedNumber;
	private String wardNumber;
	
	private String patientName;
	private String patientPhone;
	private String doctorName;
	
	private boolean bedAvailability;
	
	private Long reportNumber;

}
