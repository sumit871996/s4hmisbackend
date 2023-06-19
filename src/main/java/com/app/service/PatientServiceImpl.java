package com.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.customExceptions.ResourceNotFoundException;
import com.app.customExceptions.UserAlreadyExistException;
import com.app.dao.BedRepository;
import com.app.dao.BillRepository;
import com.app.dao.EmployeeRespository;
import com.app.dao.PathologyRepository;
import com.app.dao.PatientRepository;
import com.app.dao.PrescriptionRepository;
import com.app.dao.TreatmentRepository;
import com.app.dto.BillBasicResponseDto;
import com.app.dto.BillDoctorDetailsDto;
import com.app.dto.BillDto;
import com.app.dto.BillMedicinesDetailsDto;
import com.app.dto.BillPathologyDetailsDto;
import com.app.dto.BookAppointmentRequestDTO;
import com.app.dto.BookPathologyAppointRequestDTO;
import com.app.dto.DetailedReportResponseDTO;
import com.app.dto.EditPatientRequestDTO;
import com.app.dto.GetSecurityQRequestDTO;
import com.app.dto.MedicineResponseDTO;
import com.app.dto.PathologyDetailsResponseDTO;
import com.app.dto.PatientForgetPasswordRequestDTO;
import com.app.dto.PatientLoginRequestDTO;
import com.app.dto.PatientNewPasswordDTO;
import com.app.dto.PrescriptionResponseDTO;
import com.app.dto.ReportHistoryResponseDTO;
import com.app.dto.UpcomingAppointmnetResponseDTO;
import com.app.dto.UrgentAppointmentRequestDto;
import com.app.dto.UrgentAppointmentResponseDto;
import com.app.entities.Bed;
import com.app.entities.Bill;
import com.app.entities.Employee;
import com.app.entities.Pathology;
//import com.app.dto.EditPatientDTO;
import com.app.entities.Patient;
import com.app.entities.Prescription;
import com.app.entities.Role;
import com.app.entities.Slot;
import com.app.entities.SpecialityEnum;
import com.app.entities.Treatment;

@Service
@Transactional
public class PatientServiceImpl implements IPatientService {

	@Autowired
	private PasswordEncoder pwencoder;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private TreatmentRepository treatmentRepository;

	@Autowired
	PrescriptionRepository prescriptionRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private EmployeeRespository employeeRespository;

	@Autowired
	private PathologyRepository pathologyRepository;

	@Autowired
	private BedRepository bedRepo;

	public Patient signUP(Patient patient) {

		Optional<Employee> emp = employeeRespository.findByPhone(patient.getPhone());
		Optional<Patient> pat = patientRepository.findByPhone(patient.getPhone());

		// System.out.println(user);
		if (emp.isPresent())
			throw new UserAlreadyExistException("Employee with given phone no. already exist!!");
		else if (pat.isPresent()) {
			throw new UserAlreadyExistException("user with given phone no. already exist!!");
		}

		String password = pwencoder.encode(patient.getPassword());
		patient.setPassword(password);
		return patientRepository.save(patient);
	}

	@Override
	public Patient EditProfile(EditPatientRequestDTO patient, Long id) {

		Patient p = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient does not exist with Id: " + id));

		if (p != null) {
//		   if(patient.getPassword() != null )
//		   {
//			   String password = pwencoder.encode(patient.getPassword());
//				patient.setPassword(password);
//		   }

			mapper.map(patient, p);
			return patientRepository.save(p);
		}
		return null;
	}

	@Override
	public Long BookAppointmentApi(BookAppointmentRequestDTO appointmentdetails) {

		Long patientId = appointmentdetails.getPatientId();// getting id of patient from dto

		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Patient does not exist with Id: " + patientId));// getting
																													// patient
																													// from
																													// id
																													// to
																													// set
																													// in
																													// bill
																													// (patient
																													// id)

		Bill newBill = new Bill(); // to set patuent id,creating object
		newBill.setPatient(patient);// setting patient

		newBill.setDateOfAdmission(appointmentdetails.getDateOfAppointment());

		Bill generatedBill = billRepository.save(newBill);// generating new bill for patient id
		Long doctorId = appointmentdetails.getDoctorId();
		String doctorFname = appointmentdetails.getDoctorFname();// getting doctorid
		String doctorLname = appointmentdetails.getDoctorLname();// getting doctorid

		Employee doctor = employeeRespository.findById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with Id: " + doctorId));// getting
																													// doctor

		Treatment newTreatment = new Treatment();
		newTreatment.setEmployee(doctor); // setting doctor id
		newTreatment.setReportNumber(generatedBill);// setting bill number as reportn number

		mapper.map(appointmentdetails, newTreatment); // mapping remaining fields from dto

		treatmentRepository.save(newTreatment); // saving treatment
		return newTreatment.getReportNumber().getBillNumber(); // return id of generated appointmet

	}

	@Override
	public String ForgetPassword(PatientForgetPasswordRequestDTO patientForgetPassword) {
		String password = null;

//		System.out.println("in patient service name : "+patientForgetPassword.getFirstName());
//		System.out.println("in patient service phone : "+patientForgetPassword.getPhoneNo());
//		System.out.println("in patient service DOB : "+patientForgetPassword.getDOB());
//		System.out.println("in patient service test : "+patientForgetPassword.getDateOfapp());
		Patient patient = patientRepository
				.findByFirstNameAndPhoneNoAndDOB(
						patientForgetPassword.getPhoneNo(),
						patientForgetPassword.getSecurityAnswer())
				.orElseThrow(() -> new ResourceNotFoundException("Patient does not exist!!"));

		if (patient != null) {
			password = patient.getPassword();
		}
		return password;
	}
	
	@Override
	public String SetPassword(PatientNewPasswordDTO newPasswordDto) {
		Patient patient = patientRepository
				.findByPhone(
						newPasswordDto.getPhone())
				.orElseThrow(() -> new ResourceNotFoundException("Patient does not exist!!"));
		
		patient.setPassword(pwencoder.encode(newPasswordDto.getPassword()));
		patientRepository.save(patient);
		return "Password modified successfully";
	}

	@Override
	public List<UpcomingAppointmnetResponseDTO> UpcomingAppointments(Long patientId) {

		List<UpcomingAppointmnetResponseDTO> appointmnets = new ArrayList<UpcomingAppointmnetResponseDTO>();
		List<Bill> listOfbill = new ArrayList<>();

		listOfbill = patientRepository.findBillbyPatientId(patientId);

		for (int i = 0; i < listOfbill.size(); i++) {
			Treatment t = treatmentRepository.findListByReportNumber(listOfbill.get(i).getBillNumber(), false)
					.orElseThrow(() -> new ResourceNotFoundException(
							"Treatment not available for patientId : " + patientId));

			if (t != null) {
				UpcomingAppointmnetResponseDTO treatment = new UpcomingAppointmnetResponseDTO();
				treatment.setReportId(t.getReportNumber().getBillNumber());
				treatment.setFirstName(listOfbill.get(i).getPatient().getFirstName());
				treatment.setLastName(listOfbill.get(i).getPatient().getLastName());
				treatment.setPatientphone(listOfbill.get(i).getPatient().getPhone());

				treatment.setDateOfAppointment(t.getDateOfAppointment());
				treatment.setPatientNote(t.getPatientNote());
				treatment.setTreatmentSlot(t.getTreatmentSlot());
				treatment.setDoctorName(t.getEmployee().getFirstName());
				treatment.setDoctorphone(t.getEmployee().getPhone());
				appointmnets.add(treatment);
			}
		}

		return appointmnets;
	}

	@Override
	public PrescriptionResponseDTO viewPrescription(Long reportNumber) {

		PrescriptionResponseDTO prescriptionDto = new PrescriptionResponseDTO();

		List<Prescription> detailMedicineList = new ArrayList<Prescription>();

		detailMedicineList = prescriptionRepository.getPrescriptionDetailsByReportNumber(reportNumber).orElseThrow(
				() -> new ResourceNotFoundException("Prescriptions does not exist for report number: " + reportNumber));

		Treatment t = treatmentRepository.findDoctorByReportNumber(reportNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Treatments does not exist!!"));
		if (t != null) {
			List<MedicineResponseDTO> medicineList = new ArrayList<MedicineResponseDTO>();
			prescriptionDto.setDoctorId(t.getEmployee().getEmpId());
			prescriptionDto.setDocFirstName(t.getEmployee().getFirstName());
			prescriptionDto.setDocLastName(t.getEmployee().getLastName());

			for (int i = 0; i < detailMedicineList.size(); i++) {
				MedicineResponseDTO medicine = new MedicineResponseDTO();
				mapper.map(detailMedicineList.get(i), medicine);

				medicineList.add(medicine);
			}

			prescriptionDto.setMedicines(medicineList);
		}
		return prescriptionDto;
	}

	@Override
	public PathologyDetailsResponseDTO BookPathologyAppointment(BookPathologyAppointRequestDTO BookPathologyAppoint) {

		Long pathologyId = BookPathologyAppoint.getPathologyId();

		Employee pathologist = employeeRespository.findById(pathologyId).orElseThrow(
				() -> new ResourceNotFoundException("Pathology tests does not exist for employee id: " + pathologyId));

		Pathology existingdata = pathologyRepository.findByPatientReportNumber(BookPathologyAppoint.getReportNumber())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Pathology tests does not exist for report number: " + BookPathologyAppoint.getReportNumber()));

		System.out.println("REp" + existingdata.getReportNumber().getBillNumber());
		existingdata.setPathologist(pathologist);
		existingdata.setDateOfArrivalTest(BookPathologyAppoint.getDateOfArrivalTest());
		existingdata.setTestSlot(BookPathologyAppoint.getTestSlot());

		pathologyRepository.save(existingdata);

		return getpathologyDetails(BookPathologyAppoint.getReportNumber());

	}

//to returne existing pathology
	@Override
	public PathologyDetailsResponseDTO getpathologyDetails(Long reportNo) {

		PathologyDetailsResponseDTO detailsDto = new PathologyDetailsResponseDTO();

		Pathology details = pathologyRepository.getPendingPathologyByReportNumber(reportNo).orElseThrow(
				() -> new ResourceNotFoundException("Pathology tests does not exist for report number: " + reportNo));
		if (details != null) {
			if (details.getPathologist() != null) {
				detailsDto.setPathologistId(details.getPathologist().getEmpId());
			}
			detailsDto.setReportNo(details.getReportNumber().getBillNumber());
			mapper.map(details, detailsDto);
			return detailsDto;
		}
		return null;
	}

	@Override
	public List<ReportHistoryResponseDTO> ReportHistory(Long patientId) {

		List<ReportHistoryResponseDTO> listOfReport = new ArrayList<ReportHistoryResponseDTO>();

		// get all bills for a patient from his id.
		List<Bill> listofBills = billRepository.getAllBillFromPatientId(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Bills does not exist for patient id: " + patientId));

		// for each bill in list, get treatment and set dto fields acc to treatment and
		// patient.
		for (int i = 0; i < listofBills.size(); i++) {

			Long rNo = listofBills.get(i).getBillNumber();
			Treatment t = treatmentRepository.findByPatientReportNumber(rNo).orElseThrow(
					() -> new ResourceNotFoundException("Treatments does not exist for patient id: " + patientId));
			ReportHistoryResponseDTO reportDetails = new ReportHistoryResponseDTO();

			Employee doctor = t.getEmployee();
			System.out.println("doctor22222" + doctor);
			reportDetails.setDocFirstName(t.getEmployee().getFirstName());

			reportDetails.setReportNo(t.getReportNumber().getBillNumber());
			reportDetails.setDocLastName(t.getEmployee().getLastName());
			reportDetails.setDoctorphone(t.getEmployee().getPhone());
			// reportDetails.setTotalAmount(listofBills.get(i).getTotalPaidAmount());
			reportDetails.setPatientfirstName(listofBills.get(i).getPatient().getFirstName());
			reportDetails.setPatientlastName(listofBills.get(i).getPatient().getLastName());
			reportDetails.setPatientphone(listofBills.get(i).getPatient().getPhone());

			mapper.map(t, reportDetails);
			listOfReport.add(reportDetails);
		}

		return listOfReport;
	}

	@Override
	public Patient PatientLogin(PatientLoginRequestDTO patientLoginRequestDTO) {

		System.out.println("phone>>>>>>>>>>>" + patientLoginRequestDTO.getPhone());
		System.out.println("phone>>>>>>>>>>>" + patientLoginRequestDTO.getPassword());
		Patient patient = patientRepository
				.findByPhoneAndPassword(patientLoginRequestDTO.getPhone(), patientLoginRequestDTO.getPassword())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Credentials "));
		System.out.println("111111111111" + patient);
		return patient;
	}

	@Override
	public String getSecurityQuestion(GetSecurityQRequestDTO getSecurityQRequestDTO) {

		Patient patient = patientRepository
				.findByFNameAndLNameAndPhoneNo(getSecurityQRequestDTO.getFirstName(),
						getSecurityQRequestDTO.getLastName(), getSecurityQRequestDTO.getPhone())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Credentials "));

		if (patient != null) {
			return patient.getSecurityQuestion();
		} else {
			return null;
		}
	}

	@Override
	public BillDto getBill(Long billNumber) {

		Bill billToSend = billRepository.findByBillNumber(billNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid bill number"));

		BillDto bdto = new BillDto();
		BillPathologyDetailsDto pdto = new BillPathologyDetailsDto();
		bdto.setPathology_details(pdto);
//		System.out.println("2///////////"+ billToSend.toString());
		Boolean pathExist = pathologyRepository.findByPatientReportNumber(billNumber).isPresent();
		Pathology pathoToSend = null;
		if (pathExist) {
			pathoToSend = pathologyRepository.findByPatientReportNumber(billNumber).orElseThrow(
					() -> new ResourceNotFoundException("Pathology test do not exist with bill number: " + billNumber));
			mapper.map(pathoToSend, pdto);
		}

//		System.out.println("hiii///////////"+ pathoToSend);

		Treatment treatToSend = treatmentRepository.findByPatientReportNumber(billNumber).orElseThrow(
				() -> new ResourceNotFoundException("Treatment do not exist with bill number: " + billNumber));

//		System.out.println("hiii///////////"+ treatToSend);

		List<Prescription> prescToSend = prescriptionRepository.getPrescriptionDetailsByReportNumber(billNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Prescriptions do not exist with bill number: " + billNumber));
//		System.out.println("hiii///////////"+ prescToSend);
//		if(pathoToSend != null)

//		System.out.println("5///////////"+ bdto.toString());
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

//		System.out.println(bedRepo.getBedChargesByWardAndBed(treatToSend.getBed().getBedNumber(), treatToSend.getBed().getWardNumber()));
//		System.out.println(treatToSend.getBedAllotedForDays());
		if (treatToSend != null)
			if (treatToSend.getBed() != null) {
				Bed bed = bedRepo
						.findByWardNumberAndBedNumber(treatToSend.getBed().getWardNumber(),
								treatToSend.getBed().getBedNumber())
						.orElseThrow(() -> new ResourceNotFoundException(
								"Bed does not exist with Report Number: " + billNumber));
				Double bedCharges = bed.getWardCharges();
				bdto.setTotalBedCharges(treatToSend.getBedAllotedForDays() * bedCharges);
//		System.out.println("4///////////"+ bdto.toString());
			}
		if (billToSend != null)
			bdto.setPaidStatus(billToSend.isPaidStatus());
		double totalMedicineCharges = 0;

		if (prescToSend != null) {
			for (Prescription p : prescToSend)
				totalMedicineCharges += p.getUnitCost() * p.getQuantity();
		}
//		System.out.println("Checking until now");
//		System.out.println(bdto);
		double pathCharge;
		if (bdto.getPathology_details() == null)
			pathCharge = 0.0;
		else
			pathCharge = bdto.getPathology_details().getTestCharges();

		Long treatmentCharges = 0L;
		if (bdto.getDoctor_details().getTreatmentCharges() != null)
			treatmentCharges = bdto.getDoctor_details().getTreatmentCharges();
//		System.out.println(bdto.getDoctor_details().getTreatmentCharges());
//		System.out.println(bdto.getTotalBedCharges());
//		System.out.println(pathCharge);
//		System.out.println(totalMedicineCharges);
		bdto.setTotalPaidAmount(bdto.getTotalBedCharges() + treatmentCharges + pathCharge + totalMedicineCharges);
		bdto.setDateOfDischarge(billToSend.getDateOfDischarge());
		return bdto;
	}

	@Override
	public List<BillBasicResponseDto> getAllBillsByStatus(Long patientId, boolean status) {

		List<BillBasicResponseDto> billToSend = new ArrayList<>();

		Boolean isExist = billRepository.findbyBillForPatientbyStatus(patientId, status).isPresent();
		if (isExist) {
			List<Bill> billList = billRepository.findbyBillForPatientbyStatus(patientId, status).orElseThrow(
					() -> new ResourceNotFoundException("Bills does not exist for patient id: " + patientId));

			for (int i = 0; i < billList.size(); i++) {
				BillBasicResponseDto bbdto = new BillBasicResponseDto();
				bbdto.setBillNumber(billList.get(i).getBillNumber());

				Patient pat = billList.get(i).getPatient();
				mapper.map(pat, bbdto);
				BillDto billDto = getBill(bbdto.getBillNumber());
//			System.out.println(bbdto.toString());

				bbdto.setTreatmentCharges(billDto.getDoctor_details().getTreatmentCharges());
				bbdto.setTreatmentName(billDto.getDoctor_details().getTreatmentName());
				bbdto.setTypeOfTreatment(billDto.getDoctor_details().getTypeOfTreatment());

				bbdto.setTotalPaidAmount(billDto.getTotalPaidAmount());
				billToSend.add(bbdto);
			}
		}

		return billToSend;
	}

	@Override
	public List<Employee> getDoctorList() {

		List<Employee> employeesList = employeeRespository.getAllEmployees();

		List<Employee> doctorList = new ArrayList<Employee>();

		for (int i = 0; i < employeesList.size(); i++) {
			if (employeesList.get(i).getRole() == Role.ROLE_DOCTOR) {
				doctorList.add(employeesList.get(i));
			}
		}

		return doctorList;
	}

	@Override
	public List<PathologyDetailsResponseDTO> getAllPathologyDetails(Long patientID) {

		List<PathologyDetailsResponseDTO> pathologyList = new ArrayList<PathologyDetailsResponseDTO>();

		// get all bills for a patient from his id.
		List<Bill> listofBills = billRepository.getAllBillFromPatientId(patientID)
				.orElseThrow(() -> new ResourceNotFoundException("Bills does not exist for patient id: " + patientID));

		for (int i = 0; i < listofBills.size(); i++) {
			PathologyDetailsResponseDTO pathology = null;
			try {
				pathology = getpathologyDetails(listofBills.get(i).getBillNumber());
				if (pathology != null) {
					pathologyList.add(pathology);
				}
			} catch (ResourceNotFoundException r) {
				continue;
			}
		}

		return pathologyList;
	}

	@Override
	public List<Employee> getPathologistList() {

		List<Employee> employeesList = employeeRespository.getAllEmployees();

		List<Employee> pathologistList = new ArrayList<Employee>();

		for (int i = 0; i < employeesList.size(); i++) {
			if (employeesList.get(i).getRole() == Role.ROLE_PATHOLOGIST) {
				pathologistList.add(employeesList.get(i));
			}
		}

		return pathologistList;

	}

	@Override
	public Patient getMydetails(Long patientId) {

		Patient mydetails = patientRepository.findById(patientId).orElseThrow(
				() -> new ResourceNotFoundException("Patient does not exist for patient id: " + patientId));

		return mydetails;
	}

	@Override
	public DetailedReportResponseDTO detailedReportDetails(Long reportNo) {
		DetailedReportResponseDTO reportDto = new DetailedReportResponseDTO();
		reportDto.setReportNo(reportNo);

		List<MedicineResponseDTO> medicinesdtoList = new ArrayList<MedicineResponseDTO>();

		try {
			Treatment t = treatmentRepository.findDoctorByReportNumber(reportNo)
					.orElseThrow(() -> new ResourceNotFoundException("such report does not exists: " + reportNo));

			if (t != null) {
				reportDto.setDocfirstName(t.getEmployee().getFirstName());
				reportDto.setDoclastName(t.getEmployee().getLastName());
				reportDto.setPatientNote(t.getPatientNote());
				reportDto.setDateOfAppointment(t.getDateOfAppointment());
				reportDto.setTreatmentName(t.getTreatmentName());
				reportDto.setTypeoftreatment(t.getTypeOfTreatment());
				reportDto.setTreatmentSlot(t.getTreatmentSlot());
			}

		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
		}

		try {

			Patient p = billRepository.getPatientByreportNumber(reportNo).orElseThrow(
					() -> new ResourceNotFoundException("Patient does not exists for report No: " + reportNo));

			if (p != null) {
				reportDto.setPatientfirstName(p.getFirstName());
				reportDto.setPatientlastName(p.getLastName());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			Pathology pathology = pathologyRepository.findByPatientReportNumber(reportNo).orElseThrow(
					() -> new ResourceNotFoundException("Patient does not exists for report No: " + reportNo));

			if (pathology != null) {
				System.out.println("dddfdfdf" + pathology);
				reportDto.setPathfirstName(pathology.getPathologist().getFirstName());
				reportDto.setPathlastName(pathology.getPathologist().getLastName());
				reportDto.setPathologyTest(pathology.getTestName());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			List<Prescription> medicineList = prescriptionRepository.getPrescriptionDetailsByReportNumber(reportNo)
					.orElseThrow(() -> new ResourceNotFoundException(
							"Prescription does not exists for report No: " + reportNo));
			if (medicineList != null) {
				for (int i = 0; i < medicineList.size(); i++) {
					MedicineResponseDTO medicinedto = new MedicineResponseDTO();
					medicinedto.setDosagePerDay(medicineList.get(i).getDosagePerDay());
					medicinedto.setDuration(medicineList.get(i).getDuration());
					medicinedto.setMedicineName(medicineList.get(i).getMedicineName());
					medicinedto.setQuantity(medicineList.get(i).getQuantity());
					medicinedto.setMedicineNumber(medicineList.get(i).getMedicineNumber());
					medicinesdtoList.add(medicinedto);
				}
			}

			if (medicineList != null) {
				reportDto.setMedicines(medicinesdtoList);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return reportDto;
	}

//	Long BookAppointmentApi(BookAppointmentRequestDTO appointmentdetails)
	@Override
	public UrgentAppointmentResponseDto bookUrgentAppointment(Long patientId,
			UrgentAppointmentRequestDto urgentRequest) {

		BookAppointmentRequestDTO treatmentToset = mapper.map(urgentRequest, BookAppointmentRequestDTO.class);

		List<Bed> bedList = bedRepo.findAll();
		UrgentAppointmentResponseDto responseDto = new UrgentAppointmentResponseDto();
		boolean bedFound = false;
		Bed bed = null;
		for (int i = 0; i < bedList.size(); i++) {

			if (bedList.get(i).getWardNumber().equals("C")) {
				if (bedList.get(i).isBedAvailability() == true) {
					responseDto.setBedNumber(bedList.get(i).getBedNumber());
					responseDto.setWardNumber(bedList.get(i).getWardNumber());
					bedList.get(i).setBedAvailability(false);
					bedFound = true;
					bed = bedList.get(i);
					break;
				}
			}
		}

		if (bedFound == true) {
			int hour = LocalTime.now().getHour();
			Slot slot = null;
			for (int i = 6; i < 25; i += 3) {
				if (hour >= 0 && hour < 9)
					slot = Slot.FIRST;
				else if (hour < 12)
					slot = Slot.SECOND;
				else if (hour < 15)
					slot = slot.THIRD;
				else if (hour < 18)
					slot = slot.FOURTH;
				else if (hour < 21)
					slot = slot.FIFTH;
				else if (hour < 24)
					slot = slot.SIXTH;

			}
			treatmentToset.setPatientId(patientId);
			treatmentToset.setDateOfAppointment(LocalDate.now());
			treatmentToset.setTreatmentSlot(slot);
			treatmentToset.setDateOfAppointment(LocalDate.now());
			treatmentToset.setTypeOfTreatment("Emergency");

			List<Employee> doctorlist = employeeRespository
					.findByMyRoleAndSpeciality(Role.ROLE_DOCTOR, SpecialityEnum.GENERAL_SURGEON)
					.orElseThrow(() -> new ResourceNotFoundException("No doctor available for treatment"));

			treatmentToset.setDoctorId(doctorlist.get(0).getEmpId());
			treatmentToset.setDoctorFname(doctorlist.get(0).getFirstName());
			treatmentToset.setDoctorLname(doctorlist.get(0).getLastName());

			responseDto.setTreatmentSlot(slot.toString());
			Long reportNumber = BookAppointmentApi(treatmentToset);

			Treatment t = treatmentRepository.findById(reportNumber)
					.orElseThrow(() -> new ResourceNotFoundException("Creating report error!"));

			t.setBed(bed);

			responseDto.setReportNumber(reportNumber);
			responseDto.setDateOfAppointment(LocalDate.now());
			return responseDto;
		} else {
			throw new ResourceNotFoundException("Bed not found!!");
//			return null;
		}
	}

	

}
