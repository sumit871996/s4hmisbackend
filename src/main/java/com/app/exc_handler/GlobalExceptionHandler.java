package com.app.exc_handler;
 

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.dto.ApiResponse;

@ControllerAdvice // Mandatory cls level anno to tell SC that following is a centralized exc
					// handler class --which will provide COMMON ADVICE to all controllers/rest
					// controllers in the web app
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	// To handle P.L (presentation logic) validation errors detected due to @Valid
	// : override base class method
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("in global exc handler : method arg invalid " + ex);
		return new ResponseEntity<>(ex.getBindingResult().getFieldErrors(). // List<FieldError>
				stream() // Stream<FieldError>
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)) // Map<String,String>
				, HttpStatus.BAD_REQUEST);
	}

	// add common exc handler method : for all un chked as well as chked exception
	@ExceptionHandler(Exception.class) // MANDATORY method level annotation :
	// to tell SC that this method is going handle specified exc.
	public ResponseEntity<?> handleRuntimeException(Exception e) {
		System.out.println("in global exc handler :  exc");
		return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage()));
	}

}