package com.app.attendance.request;

import java.util.UUID;

import com.app.attendance.enumeration.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {
private UUID userId;
	
	private String userName;
	
	private String  email;
	
	private String mobileNo;
	
	private String password;
	
	private String confirmPassword; 
	
	private UserRole userRole;

}
