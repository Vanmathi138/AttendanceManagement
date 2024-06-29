package com.app.attendance.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.app.attendance.entity.User;
import com.app.attendance.repository.UserRepository;
import com.app.attendance.response.MessageService;
import com.app.attendance.util.PasswordUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	private final UserRepository userRepository;
	
	private @NonNull MessageService messagePropertySource;;
	

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
	
	


}
