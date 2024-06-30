package com.app.attendance.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.attendance.dto.AdminRegister;
import com.app.attendance.response.AdminRegisterResponse;
import com.app.attendance.response.ResponseGenerator;
import com.app.attendance.response.TransactionContext;
import com.app.attendance.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
@Tag(name = "Registration Controller", description = "Endpoints for user registration")
public class RegistrationController {
	
private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ResponseGenerator responseGenerator;
	
	private final AdminService adminService;
	
	@Operation(summary = "Admin Register", description = "Registers a new admin user")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token")
    @PostMapping(value = "/admin", produces = "application/json")
    public ResponseEntity<?> adminRegister(@RequestBody AdminRegister request, @RequestHeader HttpHeaders httpHeader) {
        logger.info("New admin created {}", LocalDateTime.now());
        TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
        try {
            AdminRegisterResponse response = adminService.adminRegister(request);
            return responseGenerator.successResponse(context, response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while registering admin details", e);
            return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
