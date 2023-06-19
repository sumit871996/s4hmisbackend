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
import com.app.dao.PrescriptionRepository;
import com.app.dao.TreatmentRepository;
import com.app.dto.TreatmentBasicDetailsResponseDto;
import com.app.dto.TreatmentDetailedResponseDto;
import com.app.dto.TreatmentPatientDetailDto;
import com.app.dto.UpdatableReportRequestDto;
import com.app.entities.Bill;
import com.app.entities.Pathology;
import com.app.entities.Patient;
import com.app.entities.Prescription;
import com.app.entities.Treatment;

@Service
@Transactional
public class DoctorServiceImpl implements IDoctorService {
	
	
	@Autowired
	private PrescriptionRepository presRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PathologyRepository pathoRepo;
	
	@Autowired
	private TreatmentRepository treatmentRepo;
	
	@Autowired
	private BillRepository billRepo;
	


	
	@Override
	public List<TreatmentBasicDetailsResponseDto> findAllUpcomingAppointments(Long empId) {
		
		List<TreatmentBasicDetailsResponseDto> tdto = new ArrayList<TreatmentBasicDetailsResponseDto>();  //for storing list of all upcmoning details of particular mployee

		List<Treatment> treatmentList = treatmentRepo.findByEmployeeAndPendingTreatments(empId).orElseThrow(
				() -> new ResourceNotFoundException("Treatments does not exist with Id: "+ empId)
				);

		for( int i=0; i< treatmentList.size();i++)
		{
			Patient ptn = treatmentList.get(i).getReportNumber().getPatient();
			TreatmentPatientDetailDto ptnDtls = mapper.map(ptn, TreatmentPatientDetailDto.class);
			
			TreatmentBasicDetailsResponseDto trtmntDtls = mapper.map(treatmentList.get(i), TreatmentBasicDetailsResponseDto.class);
			trtmntDtls.setPatient_details(ptnDtls);
			trtmntDtls.setReportNumberToSend(treatmentList.get(i).getReportNumber().getBillNumber());
					
			tdto.add(trtmntDtls);
		}
		return tdto;
	}

	@Override
	public TreatmentDetailedResponseDto getAppointmentByReportNumber(Long reportNumber) {
		

		Treatment treatmentFound = treatmentRepo.findByPatientReportNumber(reportNumber).orElseThrow(
				() -> new ResourceNotFoundException("Treatment does not exist with report number: "+ reportNumber)
				);
		
		System.out.println("Treatment found" + treatmentFound);
		

		Patient patient = billRepo.getPatientByreportNumber(reportNumber).orElseThrow(
				() -> new ResourceNotFoundException("Bill does not exist with report number: "+ reportNumber)
				) ;
		System.out.println("Patient found" + patient);

		TreatmentPatientDetailDto pdto =  new TreatmentPatientDetailDto();
		TreatmentDetailedResponseDto dto = new TreatmentDetailedResponseDto();
		

		if(treatmentFound.getBed() != null)
		{
		dto.setBedNumber(treatmentFound.getBed().getBedNumber());
		dto.setWardNumber(treatmentFound.getBed().getWardNumber());
		}
		mapper.map(patient, pdto);
		dto.setPatient_details(pdto);
		
		dto.setReportNumber(treatmentFound.getReportNumber().getBillNumber());
		dto.setPatientNote(treatmentFound.getPatientNote());
		dto.setPatientProblem(treatmentFound.getPatientProblem());
		dto.setTreatmentName(treatmentFound.getTreatmentName());
		dto.setTreatmentCharges(treatmentFound.getTreatmentCharges());
		dto.setDoctorRemark(treatmentFound.getDoctorRemark());
		dto.setDoctorsNote(treatmentFound.getDoctorsNote());
		dto.setTreatmentResult(treatmentFound.getTreatmentResult());
		dto.setTreatmentStatus(treatmentFound.isTreatmentStatus());
		dto.setBedAllotedForDays(treatmentFound.getBedAllotedForDays());
		return dto;
	}

	@Override
	public String updateAppointmentDetailsByReportNumber(Long reportNumber, UpdatableReportRequestDto dto) {
		
		Treatment treatmentToUpdate = treatmentRepo.findByPatientReportNumber(reportNumber).orElseThrow(
				() -> new ResourceNotFoundException("Treatment does not exist with report number: "+ reportNumber)
				);
		if(treatmentToUpdate != null)
		{
		mapper.map(dto, treatmentToUpdate);
		
		Treatment t = treatmentRepo.save(treatmentToUpdate);
		if(t!=null) {
		
		Boolean verifyPath = pathoRepo.findByPatientReportNumber(reportNumber).isPresent();

		if(!verifyPath){
			Pathology pathToEnter = new Pathology();

		pathToEnter.setTestName(dto.getTestName());
		Bill b = billRepo.findByBillNumber(reportNumber).orElseThrow(
				() -> new ResourceNotFoundException("Bill does not exist with report number: "+ reportNumber)
				);
		pathToEnter.setReportNumber(b.getTreatment().getReportNumber());
		pathoRepo.save(pathToEnter);
		}
		
		List<Prescription> verifyPres = presRepo.getPrescriptionDetailsByReportNumber(reportNumber).orElseThrow(
				() -> new ResourceNotFoundException("Prescriptions does not exist with report Number: "+ reportNumber)
				);

		if(verifyPres.size() == 0){

		for(int i=0;i<dto.getMedicines_list().size();i++)
		{
			Prescription presToEnter = new Prescription();
			mapper.map(dto.getMedicines_list().get(i), presToEnter);
			
			Bill b = billRepo.findByBillNumber(reportNumber).orElseThrow(
					() -> new ResourceNotFoundException("Bill does not exist with report number: "+ reportNumber)
					);
			presToEnter.setReportNumber(b.getTreatment().getReportNumber());
		presRepo.save(presToEnter);
		}
		}
		if(t !=null)
			return "Report Updated Successfully!!";

		}
		return "failed to update report Details";
		}
		else
			return "Treatment is not present";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
