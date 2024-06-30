package com.app.attendance.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.attendance.entity.User;
import com.app.attendance.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	

	

    public Optional<User> findByDuplicateUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Optional<User> getMobileNos(String mobileNo) {
        return userRepository.findByMobileNo(mobileNo);
    }


    public Optional<User> findByDuplicateEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
