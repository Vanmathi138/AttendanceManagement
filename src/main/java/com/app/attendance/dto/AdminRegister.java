package com.app.attendance.dto;

import java.time.LocalDate;

import com.app.attendance.enumeration.Designation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRegister {

	private String empCode;
	
	private String userName;

	private String email;

	private String mobileNo;

	private String password;
	
	private LocalDate dateOfBirth;
	
	private LocalDate dateOfJoining;
	
	private Designation designation;
	
	
	
}
