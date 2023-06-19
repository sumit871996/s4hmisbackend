package com.app.service;

import java.util.List;

import com.app.dto.TestBasicResponseDto;
import com.app.dto.TestDetailedResponseDto;
import com.app.dto.UpdatableTestRequestDto;

public interface IPathologyService {
	
	List<TestBasicResponseDto> findAllUpcomingTests(Long empId);
	
	String updateTestDetailsByReportNumber(Long reportNumber, UpdatableTestRequestDto dto);

	TestDetailedResponseDto getTestByReportNumber(Long reportNumber);
}
