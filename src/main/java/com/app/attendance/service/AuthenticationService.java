package com.app.attendance.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.attendance.dto.ErrorDto;
import com.app.attendance.entity.User;
import com.app.attendance.security.JwtService;
import com.app.attendance.util.PasswordUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final Logger logger= LoggerFactory.getLogger(getClass());

    private JwtService jwtService;
	

	private final com.app.attendance.repository.UserRepository userRepository;

	public ResponseEntity<?> userlogin(com.app.attendance.dto.AuthenticationRequest request) {

		com.app.attendance.dto.ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if (null == request) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid request payload.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}

		Optional<User> userOptional = userRepository.findByUserName(request.getUserName());
		if (!userOptional.isPresent()) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Usernot found.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		User user = userOptional.get();
		String enPassword = PasswordUtil.getEncryptedPassword(request.getPassword());

		if (!user.getUsername().equals(request.getUserName())) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid username.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}

		if (!user.getPassword().equals(enPassword)) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Password is wrong");
			response.put("Status", "0");
			response.put("error", errorDto);

			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
		}

		final String token = jwtService.generateToken(user);
		response.put("Status", 1);
		response.put("message", "Logged in successfully.!");
		response.put("jwt", token);
		response.put("userId", user.getId());
		response.put("userName", user.getUsername());
		response.put("role", user.getUserRole());

		return ResponseEntity.ok().body(response);
	}

}

