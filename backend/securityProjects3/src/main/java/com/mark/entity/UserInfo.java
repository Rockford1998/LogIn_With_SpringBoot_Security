package com.mark.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	
		@Id
		@GeneratedValue(strategy= GenerationType.IDENTITY)
		private long id;
		private String name;
		private String gender;
		private String email;
		private long phonenumber;
		private String address;
		private String password;
		private String organisation;

}
