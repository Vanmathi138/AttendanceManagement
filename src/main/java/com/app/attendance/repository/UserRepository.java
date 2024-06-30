package com.app.attendance.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.attendance.entity.User;
import com.app.attendance.enumeration.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);

	Optional<User> findByMobileNo(String mobileNo);

	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.userRole = 'ADMIN' AND u.userName = :userName")
	boolean adminDuplicate(@Param("userName") String userName);

	@Query(value = "SELECT * FROM attendance_user_details WHERE user_name = :userName", nativeQuery = true)
	Optional<User> findByUserName(String userName);

	boolean existsByUserName(String email);

	Long countByUserRole(UserRole userRole);

}
