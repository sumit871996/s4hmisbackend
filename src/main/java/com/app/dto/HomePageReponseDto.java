package com.app.dto;

import java.util.List;

import com.app.entities.SpecialityEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class HomePageReponseDto {
	
	

	private List<DoctorsGalleryDto> doctors_list;
	
	private List<SpecialityEnum> speciality_enum;

}
