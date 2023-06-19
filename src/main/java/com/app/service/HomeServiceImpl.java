package com.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.customExceptions.ResourceNotFoundException;
import com.app.dao.DoctorRepository;
import com.app.dao.EmployeeRespository;
import com.app.dao.HomeRepository;
import com.app.dto.DoctorsGalleryDto;
import com.app.dto.HomePageReponseDto;
import com.app.entities.Employee;
import com.app.entities.Role;
import com.app.entities.SpecialityEnum;
import com.app.entities.WebsiteStaticData;

@Service
@Transactional
public class HomeServiceImpl implements IHomeService {

	@Autowired
	private EmployeeRespository empServ;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private DoctorRepository doctRepo;

	@Autowired
	private HomeRepository homeRepo;

	@Override
	public HomePageReponseDto getStaticInfo() {

		HomePageReponseDto homeDto = new HomePageReponseDto();

		List<Employee> listDoct = doctRepo.findAllDoctors()
				.orElseThrow(() -> new ResourceNotFoundException("No doctors in the hospital!! Recruitment needed!!"));
		List<DoctorsGalleryDto> listResponseDoct = new ArrayList<>();
		for (int i = 0; i < listDoct.size(); i++) {
			DoctorsGalleryDto dGDto = new DoctorsGalleryDto();
			mapper.map(listDoct.get(i), dGDto);
			listResponseDoct.add(dGDto);
		}
		homeDto.setDoctors_list(listResponseDoct);

		// set Speciality Enum
		List<SpecialityEnum> specEnumDto = new ArrayList<SpecialityEnum>();
		homeDto.setSpeciality_enum(specEnumDto);

		LinkedList<SpecialityEnum> specEnum = new LinkedList<SpecialityEnum>(Arrays.asList(SpecialityEnum.values()));
		specEnum.remove(SpecialityEnum.ADMINISTRATION);
		specEnum.remove(SpecialityEnum.RECEPTIONIST);
		specEnum.remove(SpecialityEnum.GENERAL_SURGEON);
		specEnum.remove(SpecialityEnum.PATHOLOGIST);
		specEnum.remove(SpecialityEnum.PHYSICIAN);

		homeDto.setSpeciality_enum(specEnum);
		return homeDto;
	}

	@Override
	public String getAboutUsInfo() {
		WebsiteStaticData wsd = homeRepo.findByPolicyEntry(1L)
				.orElseThrow(() -> new ResourceNotFoundException("Static Data not available!!"));
		return wsd.getAboutUs();
	}

	@Override
	public String getPrivacyPolicyInfo() {
		WebsiteStaticData wsd = homeRepo.findByPolicyEntry(1L)
				.orElseThrow(() -> new ResourceNotFoundException("Static Data not available!!"));
		return wsd.getPrivacyPolicy();
	}

	@Override
	public String getContactUsInfo() {
		WebsiteStaticData wsd = homeRepo.findByPolicyEntry(1L)
				.orElseThrow(() -> new ResourceNotFoundException("Static Data not available!!"));
		return wsd.getContactUs();
	}

	@Override
	public List<DoctorsGalleryDto> findAllDoctors() {

		List<Employee> listDoct = empServ.findByMyRole(Role.ROLE_DOCTOR)
				.orElseThrow(() -> new ResourceNotFoundException("No doctors in the hospital!! Recruitment needed!!"));

		List<DoctorsGalleryDto> listDoctGal = new ArrayList<>();

		for (int i = 0; i < listDoct.size(); i++) {
			DoctorsGalleryDto dGDto = new DoctorsGalleryDto();
			mapper.map(listDoct.get(i), dGDto);
			listDoctGal.add(dGDto);
		}

		return listDoctGal;
	}

	@Override
	public List<DoctorsGalleryDto> searchDoctors(String string) {

		List<Employee> dgtdo = homeRepo.searchDoctors(string);
		List<DoctorsGalleryDto> toSend = new ArrayList<DoctorsGalleryDto>();
		for (int i = 0; i < dgtdo.size(); i++) {
			DoctorsGalleryDto dgdt = mapper.map(dgtdo.get(i), DoctorsGalleryDto.class);
			toSend.add(dgdt);
		}
		return toSend;
	}

}
