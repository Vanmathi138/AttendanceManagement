package com.app.attendance.controller;

import java.time.LocalDateTime;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.attendance.dto.AuthenticationRequest;
import com.app.attendance.response.ResponseGenerator;
import com.app.attendance.response.TransactionContext;
import com.app.attendance.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication_Controller", description = "Defines endpoints that can be hit only when the User is not logged in.")
public class AuthenticationController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ResponseGenerator responseGenerator;
	
	private final AuthenticationService AuthenticationService;
	
	
	
	 @Operation(summary = "Authenticate Admin", description = "Authenticate admin user and generate token")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Successfully authenticated and generated token"),
	            @ApiResponse(responseCode = "400", description = "Bad request, invalid credentials")
	            
	    })
	    @PostMapping(value="/authenticate",produces="application/json")
	    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request,
	                                   @RequestHeader HttpHeaders httpHeader) {
	        logger.info("generate token  {}", LocalDateTime.now());
	        TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
	        try {
	            return responseGenerator.successResponse(context, AuthenticationService.authenticate(request), HttpStatus.OK);
	        } catch (AuthenticationException e) {
	            logger.error("error occurred while generate token ", e);
	            return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
	    }

}

