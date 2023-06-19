package com.app.service;

import java.util.List;

import com.app.dto.EmployeeResponseDto;
import com.app.entities.Bed;
import com.app.entities.Employee;


public interface IAdminService {
	
	Employee AddEmployee(Employee emp);
	
	boolean DeleteEmployeeById(Long id);
	
	boolean UpdateEmployee(Employee emp);
    
    Long AddBeds(Bed bed);
    
    List<EmployeeResponseDto> getListEmployees();
    
    String changeEmpPassword(Long empid, String password);
    
    
    
    
    
    
}
