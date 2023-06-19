package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.customExceptions.ResourceNotFoundException;
import com.app.dao.BillRepository;
import com.app.dao.PathologyRepository;
import com.app.dto.TestBasicResponseDto;
import com.app.dto.TestDetailedResponseDto;
import com.app.dto.TreatmentPatientDetailDto;
import com.app.dto.UpdatableTestRequestDto;
import com.app.entities.Pathology;
import com.app.entities.Patient;

@Service
@Transactional
public class PathologyServiceImpl implements IPathologyService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PathologyRepository pathRepo;

	@Autowired
	private BillRepository billRepo;

	@Override
	public List<TestBasicResponseDto> findAllUpcomingTests(Long empId) {
		List<TestBasicResponseDto> tdto = new ArrayList<TestBasicResponseDto>();

		List<Pathology> pathoList = pathRepo.findByEmployeeAndpathologyResult(empId).orElseThrow(
				() -> new ResourceNotFoundException("Pathology tests does not exist with employee Id: " + empId));

		for (int i = 0; i < pathoList.size(); i++) {
			// create DTO object
			TestBasicResponseDto trtmntDtls = new TestBasicResponseDto();
			// get patient object
			Patient ptn = pathoList.get(i).getReportNumber().getPatient();
			// map patient with his details
			TreatmentPatientDetailDto ptnDtls = mapper.map(ptn, TreatmentPatientDetailDto.class);

			// set that patient in created DTO
			trtmntDtls.setPatient_details(ptnDtls);

			// set test details
			trtmntDtls.setTestName(pathoList.get(i).getTestName());
			trtmntDtls.setDateOfArrivalTest(pathoList.get(i).getDateOfArrivalTest());
			trtmntDtls.setTestSlot(pathoList.get(i).getTestSlot());
			trtmntDtls.setReportNumber(pathoList.get(i).getReportNumber().getBillNumber());

			// set empId
			trtmntDtls.setEmpId(pathoList.get(i).getPathologist().getEmpId());

			// add it in response list dto
			tdto.add(trtmntDtls);
		}
		return tdto;
	}

	@Override
	public String updateTestDetailsByReportNumber(Long reportNumber, UpdatableTestRequestDto dto) {

		Pathology pathologyToUpdate = pathRepo.findByPatientReportNumber(reportNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Pathology tests does not exist with reportNumber: " + reportNumber));
		if (pathologyToUpdate != null) {
			mapper.map(dto, pathologyToUpdate);

			Pathology t = pathRepo.save(pathologyToUpdate);

			if (t != null)
				return "Report Updated Successfully!!";
			return "failed to update report Details";
		}
		return "Test is not present";
	}

	@Override
	public TestDetailedResponseDto getTestByReportNumber(Long reportNumber) {

		Pathology testFound = pathRepo.findByPatientReportNumber(reportNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Pathology test details not available for report Number: " + reportNumber));

		Patient patient = billRepo.getPatientByreportNumber(reportNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Bill not found with report number" + reportNumber));
		// set treatment details
		TestDetailedResponseDto dto = mapper.map(testFound, TestDetailedResponseDto.class);

		// set patient details dto
		TreatmentPatientDetailDto pdto = mapper.map(patient, TreatmentPatientDetailDto.class);

		// set patient details in response dto
		dto.setPatient_details(pdto);

		// set employee ID
		dto.setEmpId(testFound.getPathologist().getEmpId());
		return dto;
	}

}
