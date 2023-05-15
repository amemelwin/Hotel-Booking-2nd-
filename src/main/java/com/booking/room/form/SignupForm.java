package com.booking.room.form;

import lombok.Data;

@Data
public class SignupForm {
	
	private String username;
	private String email;
	private String password;
	private String confirmPassword;

}
