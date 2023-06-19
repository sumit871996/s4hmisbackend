package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.customExceptions.ResourceNotFoundException;
import com.app.dao.BedRepository;
import com.app.dao.EmployeeRespository;
import com.app.dto.EmployeeResponseDto;
import com.app.entities.Bed;
import com.app.entities.Employee;


@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private EmployeeRespository employeeRespository;
	
	@Autowired
	private PasswordEncoder pwencoder;
	
	@Autowired
	private BedRepository bedRepository;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Employee AddEmployee(Employee emp) {
		/*
		if(emp.getRole() != Role.ROLE_DOCTOR)
		{
			emp.setRole(Role.ROLE_DOCTOR);
		}
		if(emp.getRole() != Role.ROLE_PATHOLOGIST)
		{
			emp.setRole(Role.ROLE_PATHOLOGIST);
		}
		if(emp.getRole() != Role.ROLE_RECEPTIONIST)
		{
			emp.setRole(Role.ROLE_RECEPTIONIST);
		}*/
		
	    String password = emp.getPassword();
	    emp.setPassword(passEncoder.encode(password));
		
		Employee newEmployee = employeeRespository.save(emp);
		return newEmployee;		
	}

	@Override
	public boolean DeleteEmployeeById(Long id) {
		if(id <= 0)
		{
			return false;
		}
		
		Employee extemp = employeeRespository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Employee does not exist with Id: "+ id)
				);
		if(extemp != null  )
		{
			employeeRespository.deleteById(id);
			return true;
		}
	
		return false;
	}

	@Override
	public boolean UpdateEmployee(Employee emp) {
		
		Long id = emp.getEmpId();
		
		Employee extemp = employeeRespository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Employee does not exist with Id: "+ id)
				);
		String password = emp.getPassword();
		emp.setPassword(passEncoder.encode(password));
		if(extemp != null)
		{
			employeeRespository.save(emp);
			return true;
		}
		return false;
			
	}

	

	@Override
	public Long AddBeds(Bed bed) {
		
		Bed bedAdded = bedRepository.save(bed);
		
		return bedAdded.getBedNumber();
	}

	@Override
	public List<EmployeeResponseDto> getListEmployees() {
		
		List<Employee> employeesList = employeeRespository.getAllEmployees();
		
		List<EmployeeResponseDto> dtoList = new ArrayList<EmployeeResponseDto>();
		
		for(int i=0;i<employeesList.size();i++)
		{
			
			dtoList.add(mapper.map(employeesList.get(i), EmployeeResponseDto.class));
		}
		
		return dtoList;
	}

	@Override
	public String changeEmpPassword(Long empid, String password) {
		Employee empToSetPassword = employeeRespository.findById(empid).orElseThrow(()-> new ResourceNotFoundException("Employee not found!!"));
		empToSetPassword.setPassword(pwencoder.encode(password));
		employeeRespository.save(empToSetPassword);
		return "Password changed successfully!!";
	}
	
	

}
