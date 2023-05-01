package com.mark.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mark.entity.UserInfo;
import com.mark.repository.UserInfoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {
	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private PasswordEncoder encoder;
	
	public boolean verifyUser(UserInfo userInfo)
	{
		UserInfo user=repository.findByEmail(userInfo.getEmail());
			if(user==null)
				return true;
			else
				return false;
	}
	
	public boolean addUser(UserInfo userInfo) {
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		UserInfo user= repository.save(userInfo);
			if(user.equals(userInfo))
				return true;
			else
				return false;
	}

	public UserInfo getUserByEmail(String email) {
		return this.repository.findByEmail(email);
	}

}
