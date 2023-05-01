package com.mark.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
	
	private String email;
	
	private String password;
	
	private String organisation;

}
