package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bed")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bed {

	@Column(name = "ward_number", length = 1)
	private String wardNumber;

	@Id
	@Column(name = "bed_number")
	private Long bedNumber;

	@Column(nullable = false)
	private double wardCharges;

	@Column(nullable = false)
	private boolean bedAvailability;

}
