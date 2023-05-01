package com.mark.controller;



import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mark.config.UserInfoUserDetailsService;
import com.mark.dto.ResponeseDto;
import com.mark.dto.UserLoginRequest;
import com.mark.dto.UserLoginResponce;
import com.mark.entity.UserInfo;
import com.mark.service.JwtService;
import com.mark.service.UserService;



@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	@Autowired
	private UserService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserInfoUserDetailsService userInfoUserDetailsService;
	
	@Autowired
	private JwtService jwtService;

	
	@PostMapping("/registration")
	public ResponseEntity<?> addUser(@RequestBody UserInfo userInfo) {
		boolean checkUser= service.verifyUser(userInfo);
		if(checkUser==true)
		{
			boolean saving=service.addUser(userInfo);
			if(saving)
			return new ResponseEntity<>(new ResponeseDto("Registration Successfull"), HttpStatus.OK);
			else
				return  new ResponseEntity<>(new ResponeseDto("fail to register"), HttpStatus.BAD_REQUEST);
		}
		else {
			 return new ResponseEntity<>(new ResponeseDto("User present already"), HttpStatus.BAD_REQUEST);
		}
	}

	//login request
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody UserLoginRequest userLoginRequest) {
		
		
		String jwtToken = null;

		UserInfo user = null;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(),
					userLoginRequest.getPassword()));
		} catch (Exception ex) {
			return new ResponseEntity<>(new ResponeseDto("login not possible"), HttpStatus.BAD_REQUEST);
		}

		UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(userLoginRequest.getEmail());
		for(GrantedAuthority grantedAuthority : userDetails.getAuthorities())
		{
			if(grantedAuthority.getAuthority().equals(userLoginRequest.getOrganisation())) {
				jwtToken = jwtService.generateToken(userLoginRequest.getEmail());
			}
		}
		
		if(jwtToken !=null)
		{
			 user = service.getUserByEmail(userLoginRequest.getEmail());
			UserLoginResponce responce= new UserLoginResponce();
			responce.setEmail(user.getEmail());
			responce.setJwtToken(jwtToken);
			return new ResponseEntity<>(responce,HttpStatus.OK);
			
		}
		else {
			return new ResponseEntity<>(new ResponeseDto("login not possible"), HttpStatus.BAD_REQUEST);
		}

	}
}
