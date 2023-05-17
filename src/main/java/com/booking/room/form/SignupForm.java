package com.booking.room.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignupForm {
	
	@NotEmpty(message="username is required")
	private String username;
	@NotEmpty(message="email is required")
	private String email;
	@NotEmpty(message="password is required")
	private String password;
//	@NotEmpty(message="confirmPassword is required")
	private String confirmPassword;

}
