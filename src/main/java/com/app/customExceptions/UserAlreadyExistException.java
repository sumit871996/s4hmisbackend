package com.app.customExceptions;

public class UserAlreadyExistException extends RuntimeException {
	
		private static final long serialVersionUID = 1L;

		public UserAlreadyExistException(String mesg) {
			
			super(mesg);
			
		}
}
