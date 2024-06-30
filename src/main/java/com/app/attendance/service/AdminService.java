package com.app.attendance.service;

import java.time.LocalDateTime;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.attendance.dto.AdminRegister;
import com.app.attendance.dto.AuthenticationRequest;
import com.app.attendance.entity.User;
import com.app.attendance.enumeration.Status;
import com.app.attendance.enumeration.UserRole;
import com.app.attendance.repository.UserRepository;
import com.app.attendance.response.AdminRegisterResponse;
import com.app.attendance.response.MessageService;
import com.app.attendance.security.JwtService;
import com.app.attendance.util.PasswordUtil;
import com.app.attendance.validation.AdminValidator;
import com.app.attendance.validation.ValidationResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final AdminValidator adminValidator;
    private final MessageService messageService;
    private final JwtService jwtService;
    
    private final AuthenticationManager authenticationManager;

    public AdminRegisterResponse adminRegister(AdminRegister request) {
        LocalDateTime now = LocalDateTime.now();
        ValidationResult validationResult = adminValidator.validate(request);
        User userDetails = (User) validationResult.getObject();

        userDetails.setUserName(userDetails.getUsername());
        userDetails.setPassword(PasswordUtil.getEncryptedPassword(userDetails.getPassword()));
        userDetails.setStatus(Status.ACTIVE);
        userDetails.setUserRole(UserRole.ADMIN);
        userDetails.setCreatedAt(now);
        userDetails.setCreatedBy("system");
        userDetails.setUpdatedAt(now);

        userRepository.save(userDetails);
        String token = jwtService.generateToken(userDetails);

        String message = messageService.messageResponse("user.details.save");
        return new AdminRegisterResponse(1, message, token);
    }
    
    
    public String authenticate(AuthenticationRequest request) {
        try {
        	authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            request.getPassword()));
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException("Invalid username or password", e);
        }

        User user = userRepository.findByUserName(request.getUserName())
                                   .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return jwtService.generateToken(user);
    }
}
