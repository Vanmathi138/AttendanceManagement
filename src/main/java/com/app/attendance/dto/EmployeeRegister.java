package com.app.attendance.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRegister {
		private String userName;
	    private String email;
	    private String mobileNo;
	    private String password;
	    private String dateOfBirth;
	    private LocalDate dateOfJoining;
}
