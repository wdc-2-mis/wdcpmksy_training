package com.springboot.training.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.training.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query(value = "select user_reg_id from lms_user where email = :email", nativeQuery = true)
	Integer findregid(String email);

    @Query(value = "select user_id from lms_user where user_reg_id = :regid", nativeQuery = true)
	String findusername(Integer regid);
    
    Optional<User> findById(Integer userRegId);
    
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM lms_user u WHERE u.email = :email AND u.security_id = :securityQuestion AND u.security_answer = :securityAnswer", nativeQuery = true)
    boolean checkUserDtl(@Param("email") String email, @Param("securityQuestion") Integer securityQuestion, @Param("securityAnswer") String securityAnswer);

    @Modifying
    @Query(value ="UPDATE lms_user SET password = :password WHERE email = :email", nativeQuery = true)
    int updateUserPassword(@Param("email") String email, @Param("password") String password);
}


