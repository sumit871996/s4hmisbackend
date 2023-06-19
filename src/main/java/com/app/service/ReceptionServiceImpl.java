package com.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.customExceptions.ResourceNotFoundException;
import com.app.dao.BedRepository;
import com.app.dao.BillRepository;
import com.app.dao.PathologyRepository;
import com.app.dao.PrescriptionRepository;
import com.app.dao.TreatmentRepository;
//import com.app.dto.BedAllocationDto;
import com.app.dto.BedResponseDto;
import com.app.dto.BillBasicResponseDto;
import com.app.dto.BillDoctorDetailsDto;
import com.app.dto.BillDto;
import com.app.dto.BillMedicinesDetailsDto;
import com.app.dto.BillPathologyDetailsDto;
import com.app.dto.ReceptionBedUpdateDto;
import com.app.entities.Bed;
import com.app.entities.Bill;
import com.app.entities.Employee;
import com.app.entities.Pathology;
import com.app.entities.Patient;
import com.app.entities.Prescription;
import com.app.entities.Treatment;

@Service
@Transactional
public class ReceptionServiceImpl implements IReceptionService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PrescriptionRepository prescRepo;

	@Autowired
	private TreatmentRepository treatmentRepo;

	@Autowired
	private PathologyRepository pathRepo;

	@Autowired
	private BedRepository bedRepo;

	@Autowired
	private BillRepository billRepo;

	@Override
	public boolean BedAllocation(ReceptionBedUpdateDto bedupdateDTO, Long reportNumber) {

		Treatment t = treatmentRepo.findByPatientReportNumber(reportNumber).orElseThrow(
				() -> new ResourceNotFoundException("Treatments not found with report number" + reportNumber));
		Long bedNumber = bedupdateDTO.getBedNumber();
		String wardNumber = bedupdateDTO.getWardNumber();
		Bed bed = bedRepo.findByWardNumberAndBedNumber(wardNumber, bedNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Bed not found with Ward Number: " + wardNumber + " and bedNumber: " + bedNumber));

		System.out.println(bed);
		if (bed.isBedAvailability() == true) {
			t.setBed(bed);
			t.getBed().setBedAvailability(false);
		} else
			throw new ResourceNotFoundException(
					"Bed already occupied for with Ward Number: " + wardNumber + " and bedNumber: " + bedNumber);

		Treatment t2 = treatmentRepo.save(t);
		if (t2 != null)
			return true;
		else
			return false;
	}

	@Override
	public List<Bed> getAllBedsDetails() {
		return bedRepo.findAll();
	}

	@Override
	public boolean updatePaidStatus(Long billNumber) {

		Bill bill = billRepo.findByBillNumber(billNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Bill does not exist with billNumber: " + billNumber));
		bill.setPaidStatus(true);
		System.out.println(LocalDate.now());
		bill.setDateOfDischarge(LocalDate.now());
		Treatment treatment = treatmentRepo.findByPatientReportNumber(billNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Treatment does not exist!!!"));
		if (treatment.getBed() != null)
			treatment.getBed().setBedAvailability(true);
		return true;

	}

	@Override
	public BillDto getBill(Long billNumber) {

		Bill billToSend = billRepo.findByBillNumber(billNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Bill does not exist with billNumber: " + billNumber));
		Pathology pathoToSend = null;
		if (!pathRepo.findByPatientReportNumber(billNumber).isEmpty()) {
			pathoToSend = pathRepo.findByPatientReportNumber(billNumber)
					.orElseThrow(() -> new ResourceNotFoundException(
							"Pathology test does not exist with Report Number: " + billNumber));
		}

		Treatment treatToSend = treatmentRepo.findByPatientReportNumber(billNumber).orElseThrow(
				() -> new ResourceNotFoundException("Treatment does not exist with report number: " + billNumber));

		List<Prescription> prescToSend = prescRepo.getPrescriptionDetailsByReportNumber(billNumber).orElseThrow(
				() -> new ResourceNotFoundException("Prescriptions does not exist with Report Number: " + billNumber));
		BillDto bdto = new BillDto();

		BillPathologyDetailsDto pdto = new BillPathologyDetailsDto();
		bdto.setPathology_details(pdto);
		if (pathoToSend != null)
			mapper.map(pathoToSend, pdto);

		BillDoctorDetailsDto ddto = new BillDoctorDetailsDto();
		bdto.setDoctor_details(ddto);
		if (treatToSend != null)
			mapper.map(treatToSend, ddto);

		List<BillMedicinesDetailsDto> mlist = new ArrayList<BillMedicinesDetailsDto>();
		for (int i = 0; i < prescToSend.size(); i++) {
			BillMedicinesDetailsDto bbdto = new BillMedicinesDetailsDto();
			if (prescToSend.get(i) != null)
				mapper.map(prescToSend.get(i), bbdto);
			mlist.add(bbdto);
		}
		bdto.setMedicines_details(mlist);

		if (treatToSend != null)
			if (treatToSend.getBed() != null) {
				Bed bed = bedRepo
						.findByWardNumberAndBedNumber(treatToSend.getBed().getWardNumber(),
								treatToSend.getBed().getBedNumber())
						.orElseThrow(() -> new ResourceNotFoundException(
								"Bed does not exist with Report Number: " + billNumber));
				Double bedCharges = bed.getWardCharges();
				bdto.setTotalBedCharges(treatToSend.getBedAllotedForDays() * bedCharges);
			}
		if (billToSend != null)
			bdto.setPaidStatus(billToSend.isPaidStatus());
		double totalMedicineCharges = 0;

		if (prescToSend != null) {
			for (Prescription p : prescToSend)
				totalMedicineCharges += p.getUnitCost() * p.getQuantity();
		}

		double pathCharge;
		if (bdto.getPathology_details() == null)
			pathCharge = 0.0;
		else
			pathCharge = bdto.getPathology_details().getTestCharges();

		Long treatmentCharges = 0L;
		if (bdto.getDoctor_details().getTreatmentCharges() != null)
			treatmentCharges = bdto.getDoctor_details().getTreatmentCharges();
		bdto.setTotalPaidAmount(bdto.getTotalBedCharges() + treatmentCharges + pathCharge + totalMedicineCharges);
		bdto.setDateOfDischarge(billToSend.getDateOfDischarge());
		billToSend.setTotalPaidAmount(bdto.getTotalPaidAmount());
		billRepo.save(billToSend);
		return bdto;
	}

	@Override
	public List<BillBasicResponseDto> getAllPendingBills() {
		List<BillBasicResponseDto> billToSend = new ArrayList<>();
		List<Bill> billList = billRepo.findByPaidStatusFalse()
				.orElseThrow(() -> new ResourceNotFoundException("There are no pending bills"));

		System.out.println(billList.toString());

		for (int i = 0; i < billList.size(); i++) {
			BillBasicResponseDto bbdto = new BillBasicResponseDto();
			bbdto.setBillNumber(billList.get(i).getBillNumber());

			Patient pat = billList.get(i).getPatient();
			mapper.map(pat, bbdto);
			String fullName = pat.getFirstName() + " " + pat.getLastName();
			bbdto.setPatientName(fullName);
			bbdto.setPatientPhone(pat.getPhone());
			BillDto billDto = getBill(bbdto.getBillNumber());
			mapper.map(billDto, bbdto);
			bbdto.setDateOfAdmission(billList.get(i).getDateOfAdmission());
//			bbdto.setTotalPaidAmount(billDto.getTotalPaidAmount());
			billToSend.add(bbdto);
		}

		return billToSend;
	}

	@Override
	public List<BillBasicResponseDto> getAllPaidBills() {
		List<BillBasicResponseDto> billToSend = new ArrayList<>();
		List<Bill> billList = billRepo.findByPaidStatusTrue()
				.orElseThrow(() -> new ResourceNotFoundException("No paid bills!!!"));
		for (int i = 0; i < billList.size(); i++) {
			BillBasicResponseDto bbdto = new BillBasicResponseDto();
			bbdto.setBillNumber(billList.get(i).getBillNumber());

			Patient pat = billList.get(i).getPatient();
			mapper.map(pat, bbdto);
			String fullName = pat.getFirstName() + " " + pat.getLastName();
			bbdto.setPatientName(fullName);
			bbdto.setPatientPhone(pat.getPhone());
			BillDto billDto = getBill(bbdto.getBillNumber());
			mapper.map(billDto, bbdto);
			bbdto.setDateOfAdmission(billList.get(i).getDateOfAdmission());
			billToSend.add(bbdto);
		}

		return billToSend;
	}

	@Override
	public List<BedResponseDto> getAllBedsWithAllotmentDetails() {
		List<BedResponseDto> bedAllocationList = new ArrayList<BedResponseDto>();

		List<Bed> allbeds = bedRepo.findAll();

		List<Treatment> allTreat = treatmentRepo.findAll();

		for (int i = 0; i < allbeds.size(); i++) {
			System.out.println(allbeds.size());
			bedAllocationList.add(mapper.map(allbeds.get(i), BedResponseDto.class));
			Long bedNumber = allbeds.get(i).getBedNumber();
			String wardNumber = allbeds.get(i).getWardNumber();

			for (int j = 0; j < allTreat.size(); j++) {
				System.out.println(allTreat.size() + "   " + allbeds.size() + " " + j + " " + i);
				if (allTreat.get(j).getBed() != null && allTreat.get(j).getBed().getBedNumber() == bedNumber
						&& allTreat.get(j).getBed().getWardNumber() == wardNumber
						&& !allTreat.get(j).getBed().isBedAvailability()) {
					bedAllocationList.get(i).setReportNumber(allTreat.get(j).getTreatmentId());
					System.out.println("Hoo");
					Employee doct = allTreat.get(j).getEmployee();
					bedAllocationList.get(i).setDoctorName(doct.getFirstName() + " " + doct.getLastName());

					Patient patient = allTreat.get(j).getReportNumber().getPatient();
					bedAllocationList.get(i).setPatientName(patient.getFirstName() + " " + patient.getLastName());
					bedAllocationList.get(i).setPatientPhone(patient.getPhone());
				}

			}
		}
		return bedAllocationList;
	}

}
