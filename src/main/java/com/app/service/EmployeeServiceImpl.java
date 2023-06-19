package com.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.customExceptions.ResourceNotFoundException;
import com.app.dao.EmployeeRespository;
import com.app.dto.EmployeeModifyDetailsDto;
import com.app.dto.EmployeeResponseDto;
import com.app.entities.Employee;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EmployeeRespository empRepo;
	@Override
	public Employee findByphoneAndpassword(String phone, String password) {
		System.out.println("inside findByEmployeeId");
		return  empRepo.findByPhoneAndPassword(phone, password).orElseThrow(
				() -> new ResourceNotFoundException("Wrong credentials!!!")
				);
		
	}
	@Override
	public Employee findByEmpId(Long empId) {
		
		return empRepo.findById(empId).orElseThrow(
				() -> new ResourceNotFoundException("Treatments does not exist with Id: "+ empId)
				);
	}
	
	@Override
	public String updateMyDetails(EmployeeModifyDetailsDto myDetails, Long empId) {  //Update Doctor Details
		
		Employee verifiedEmployee =  empRepo.findById(empId).orElseThrow(
				() -> new ResourceNotFoundException("Employee does not exist with Id: "+ empId)
				);
		if(verifiedEmployee != null)
		{
				mapper.map(myDetails, verifiedEmployee);
				empRepo.save(verifiedEmployee);
				return "Employee Details modified successfully!!!!";
		}
		else return "No Such Employee!!!!";
	}
	
	@Override
	public EmployeeResponseDto getMyDetails(Long empId) {
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new ResourceNotFoundException("Invalid Emp Id"));
		return mapper.map(emp, EmployeeResponseDto.class);
	}
	

}
