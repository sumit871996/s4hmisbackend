package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.app.entities.Slot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookPathologyAppointRequestDTO {

	@NotNull
	@Range(min=1, max = 1000)
	private Long pathologyId;
	
	@NotNull
	@Range(min=1)
	private Long reportNumber;
	
	@FutureOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfArrivalTest;
	
	@NotNull
	private Slot testSlot;
	
}
