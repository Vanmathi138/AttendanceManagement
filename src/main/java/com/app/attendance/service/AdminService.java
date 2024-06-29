package com.app.attendance.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.app.attendance.advice.AdminRegistrationException;
import com.app.attendance.entity.User;
import com.app.attendance.enumeration.UserRole;
import com.app.attendance.repository.UserRepository;
import com.app.attendance.response.UserResponse;
import com.app.attendance.security.JwtService;
import com.app.attendance.util.PasswordUtil;

import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final UserService userService;

	public User createAdmin(User user) {
        // Check if there are already 3 admins registered
        long adminCount = userRepository.countByUserRole(UserRole.ADMIN);
        if (adminCount >= 3) {
            throw new AdminRegistrationException("Maximum number of admins (3) already registered.");
        }

        var adminObj = User.builder()
                .empCode(user.getEmpCode())
                .email(user.getEmail())
                .mobileNo(user.getMobileNo())
                .password(PasswordUtil.getEncryptedPassword(user.getPassword()))
                .userName(user.getUsername())
                .dateOfBirth(user.getDateOfBirth())
                .dateOfJoining(user.getDateOfJoining())
                .designation(user.getDesignation())
                .userRole(UserRole.ADMIN) 
                .build();
        return userRepository.saveAndFlush(adminObj);
    }
	public ResponseEntity<?> getadmindetials(String username, String auth) {

		try {
			String token = jwtService.extractToken(auth);
			// Construct response
			Map<String, Object> response = new HashMap<>();
			// Validate token
			if (!jwtService.validateToken(token)) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
			}
			Optional<User> userdetails = userRepository.findByUserName(username);
			if (userdetails.isPresent()) {
				User user = userdetails.get();
				UserResponse userresponse = UserResponse.builder().email(user.getEmail()).userName(user.getUsername())
						.mobileNo(user.getMobileNo()).status(user.getStatus()).build();

				// Retrieve user details
				UserDetails userDetails = userService.loadUserByUsername(userdetails.get().getUsername());
				response.put("Authorities", userDetails.getAuthorities());
				response.put("Details", userresponse);
				return ResponseEntity.ok(response);
			} else {
				response.put("Status", HttpStatus.NO_CONTENT.toString());
				response.put("message", "User id is invalid pleas check the ID");
				response.put("Error", HttpStatus.NO_CONTENT);
				return ResponseEntity.internalServerError().body(response);
			}
		} catch (SignatureException e) {
			e.printStackTrace();
			throw new SignatureException("Token is invalid");
		}
	}

}
