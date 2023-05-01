package com.mark.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import com.mark.entity.UserInfo;
import com.mark.repository.UserInfoRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired 
	private UserInfoRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserInfo userInfo = this.repository.findByEmail(username);
		System.out.println(userInfo);
		return org.springframework.security.core.userdetails.User.withUsername(userInfo.getEmail())
				.password(userInfo.getPassword()).authorities(userInfo.getOrganisation()).build();

	}

}
