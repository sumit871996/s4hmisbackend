package com.app.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.EmployeeRespository;
import com.app.dao.PatientRepository;
import com.app.dto.AuthRequest;
import com.app.dto.AuthResp;
import com.app.entities.Employee;
import com.app.entities.Patient;
import com.app.jwt_utils.JwtUtils;
import com.app.service.IPatientService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
@Validated
public class SignInSignUpController {
//dep : JWT utils : for generating JWT
	@Autowired
	private JwtUtils utils;
	// dep : Auth mgr
	@Autowired
	private AuthenticationManager manager;
	// dep : user service for handling users

	@Autowired
	private PatientRepository patientRepo;

	@Autowired
	private EmployeeRespository empRepo;

	@Autowired
	private IPatientService patientservice;
//	@Autowired
//	private UserService userService;

	// add a method to authenticate user . In case of success --send back token ,
	// o.w
	// send back err mesg
	@PostMapping("/signin")
	public ResponseEntity<?> validateUserCreateToken(@RequestBody @Valid AuthRequest request) {
		// store incoming user details(not yet validated) into Authentication object
		// Authentication i/f ---> implemented by UserNamePasswordAuthToken
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getPhone(),
				request.getPassword());
//		log.info("auth token before {}", authToken);
		try {
			// authenticate the credentials
			Authentication authenticatedDetails = manager.authenticate(authToken);
//			log.info("auth token again {} ", authenticatedDetails);
			// => auth succcess
			AuthResp authResp = new AuthResp();
			Optional<Patient> user = patientRepo.findByPhone(request.getPhone());
			// System.out.println(user);
			if (user.isPresent()) {
				Patient patient = user.get();
				authResp.setId(patient.getPatientId());
				authResp.setRole(patient.getUserRole());
			} else {
				Employee user1 = empRepo.findByPhone(request.getPhone())
						.orElseThrow(() -> new UsernameNotFoundException("Invalid Email ID "));
				authResp.setId(user1.getEmpId());
				authResp.setRole(user1.getUserRole());
			}
			authResp.setMessage("Auth Successful!!!");
			authResp.setToken(utils.generateJwtToken(authenticatedDetails));

			return ResponseEntity.ok(authResp);
		} catch (BadCredentialsException e) { // lab work : replace this by a method in global exc handler
			// send back err resp code
			System.out.println("err " + e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}
	// add request handling method for user registration

	@PostMapping("/signup")
	public ResponseEntity<Patient> signUP(@RequestBody @Valid Patient patient) {

		Patient p = patientservice.signUP(patient);

		return new ResponseEntity<>(p, HttpStatus.CREATED);

	}
}
