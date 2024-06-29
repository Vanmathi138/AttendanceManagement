package com.app.attendance.response;


import com.app.attendance.enumeration.Status;
import com.app.attendance.enumeration.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	
private String userName;
	
	private String email;

	private String mobileNo;
	
	private Status	status;
	
	private UserRole userRole;

}
