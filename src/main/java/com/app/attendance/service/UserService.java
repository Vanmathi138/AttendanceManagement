package com.app.attendance.service;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.attendance.dto.EmployeeRegister;
import com.app.attendance.entity.User;
import com.app.attendance.enumeration.Status;
import com.app.attendance.enumeration.UserRole;
import com.app.attendance.repository.UserRepository;
import com.app.attendance.response.MessageService;
import com.app.attendance.security.JwtService;
import com.app.attendance.util.PasswordUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private @NonNull MessageService messagePropertySource;
	private final PasswordEncoder passwordEncoder;
	
	public User createUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		if (user.getUserRole() == null) {
			throw new IllegalArgumentException("Role cannot be null");
		}
		var userobj = User.builder().email(user.getEmail()).mobileNo(user.getMobileNo())
				.password(PasswordUtil.getEncryptedPassword(user.getPassword())).userRole(user.getUserRole())
				.status(Status.ACTIVE).userName(user.getUsername()).build();
		return userRepository.saveAndFlush(userobj);
	}
	

	public Optional<User> findByDuplicateEmail(String email) {

		return userRepository.findByDuplicateEamil(email);
	}
	
	public Optional<User> findByDuplicateUserName(String userName) {
		return userRepository.findByDuplicateUserName(userName);
	}
	
	public Optional<User> getMobileNos(String phoneNumber) {
		return userRepository.findByDuplicateNumber(phoneNumber);
	}
	
	public Optional<User> findByDuplicatePassword(String password) {

		String enpassword = PasswordUtil.getEncryptedPassword(password);

		return userRepository.findByDuplicatePassword(enpassword);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUserName(username);
		if (!userOptional.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		User user = userOptional.get();
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getAuthorities());
	}
	public Optional<User> findById(UUID userId) {
		return userRepository.findById(userId);
	}

	public Optional<User> findByUserRoleType(String admin) {
		return userRepository.findByUserRoleType(admin);
	}


	public User createEmployee(User user) {
		var employee = User.builder()
                .empCode(user.getEmpCode())
                .email(user.getEmail())
                .mobileNo(user.getMobileNo())
                .password(PasswordUtil.getEncryptedPassword(user.getPassword()))
                .userName(user.getUsername())
                .dateOfBirth(user.getDateOfBirth())
                .dateOfJoining(user.getDateOfJoining())
                .designation(user.getDesignation())
                .userRole(UserRole.EMPLOYEE) 
                .build();
        return userRepository.saveAndFlush(employee);
    }
}
