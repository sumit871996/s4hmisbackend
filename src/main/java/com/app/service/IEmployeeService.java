package com.app.service;

import com.app.dto.EmployeeModifyDetailsDto;
import com.app.dto.EmployeeResponseDto;
import com.app.entities.Employee;

public interface IEmployeeService {
	
	Employee findByphoneAndpassword(String phone, String password);
	
	//get All details of employee by Id
		Employee findByEmpId(Long empId);
		
		String updateMyDetails(EmployeeModifyDetailsDto myDetails, Long empId);
		
		EmployeeResponseDto getMyDetails(Long empId);

}
