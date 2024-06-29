package com.app.attendance.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.attendance.entity.User;
import com.app.attendance.enumeration.RequestType;
import com.app.attendance.request.AdminRegister;
import com.app.attendance.response.ResponseGenerator;
import com.app.attendance.response.TransactionContext;
import com.app.attendance.security.JwtService;
import com.app.attendance.service.AdminService;
import com.app.attendance.validation.AdminValidation;
import com.app.attendance.validation.ValidationResult;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ResponseGenerator responseGenerator;
	
	private AdminValidation adminValidator;
	
	private AdminService adminService;
	
	private JwtService jwtService;
	
	@PostMapping("/admin/register")
	public ResponseEntity<?> adminRegister(@RequestBody AdminRegister register,
			 @RequestHeader HttpHeaders httpHeaders) {
		ValidationResult validationResult = adminValidator.validate(RequestType.POST, register);
		User user = (User) validationResult.getObject();

		User adminservice = adminService.createAdmin(user);
		Map<String, Object> response = new HashMap<>();
		final String token = jwtService.generateToken(adminservice);
		response.put("Status", 1);
		response.put("message", "You have register successfully.");
		response.put("token", token);

		TransactionContext context = responseGenerator.generateTransationContext(httpHeaders);
		try {
			return responseGenerator.successResponse(context, response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
