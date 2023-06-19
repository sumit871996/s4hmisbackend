package com.app.service;

import java.util.List;

//import com.app.dto.BedAllocationDto;
import com.app.dto.BedResponseDto;
import com.app.dto.BillBasicResponseDto;
import com.app.dto.BillDto;
import com.app.dto.ReceptionBedUpdateDto;
import com.app.entities.Bed;

public interface IReceptionService {

	
	List<BillBasicResponseDto> getAllPendingBills();
	
	
	List<BillBasicResponseDto> getAllPaidBills();
	
	
	// bed allocation
	// make an entry into bed table and one more entry into treatment table
	public boolean BedAllocation(ReceptionBedUpdateDto bedupdateDTO ,Long reportNumber);
	
	List<BedResponseDto> getAllBedsWithAllotmentDetails();
	
	public List<Bed> getAllBedsDetails();
	
	
	//3. Paid Status
	boolean updatePaidStatus(Long billNumber);
	//	boolean getPaidStatus(Long reportNumber);
	
	
	// Show Bill <button> -->
	BillDto getBill(Long billNumber);
	
	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
