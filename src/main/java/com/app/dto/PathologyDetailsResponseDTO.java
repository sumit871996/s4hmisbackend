package com.app.dto;

import java.time.LocalDate;

import com.app.entities.Slot;

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
public class PathologyDetailsResponseDTO {
	
	private Long testId;
	
	private Long reportNo;
	
	private Long pathologistId;

	private String testName;

	private double testCharges;

	private String pathologistRemark;

	private boolean testStatus;

	private LocalDate dateOfArrivalTest;

	private Slot testSlot;
}
