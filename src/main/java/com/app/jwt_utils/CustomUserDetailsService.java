package com.app.jwt_utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.EmployeeRespository;
import com.app.dao.PatientRepository;
import com.app.entities.Employee;
import com.app.entities.Patient;

@Service // or @Component also works!
@Transactional

public class CustomUserDetailsService implements UserDetailsService {
	// dep : user repository : based upon spring data JPA
	@Autowired
	private EmployeeRespository empRepo;
	@Autowired
	private PatientRepository patRepo; 
	@Override
	public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
		System.out.println("in load by user nm " + phone);
		// invoke dao's method to load user details from db by username(ie. actaully an
		// email)
		Optional<Patient> user= patRepo.findByPhone(phone);
		//System.out.println(user);
		if(user.isPresent())
		{
			Patient patient = user.get();
			System.out.println(patient.getUserRole());
			return new CustomUserDetails(patient);
		}
		else {
			Employee user1= empRepo.findByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("Invalid Email ID "));
			System.out.println(user1);
			return new CustomUserDetails(user1);
			}

	}

}
