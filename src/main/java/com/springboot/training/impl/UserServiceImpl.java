package com.springboot.training.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.training.dto.UserDto;
import com.springboot.training.dto.UserUrlDto;
import com.springboot.training.entity.URLDetails;
import com.springboot.training.entity.User;
import com.springboot.training.repository.ReportRepository;
import com.springboot.training.repository.RoleRepository;
import com.springboot.training.repository.SaveURLRepository;
import com.springboot.training.repository.UserRepository;
import com.springboot.training.service.UserService;

import jakarta.servlet.http.*;
@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private ReportRepository reportRepository;
	private SaveURLRepository saveURlRepository;
	 private RoleRepository roleRepository; 
    private PasswordEncoder passwordEncoder;
     public UserServiceImpl(UserRepository userRepository,
				/* RoleRepository roleRepository, */
                           ReportRepository reportRepository,
                           SaveURLRepository saveURlRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
		/* this.roleRepository = roleRepository; */
        this.passwordEncoder = passwordEncoder;
        this.saveURlRepository = saveURlRepository;
        this.reportRepository = reportRepository;
    }

	
	  public void saveUser(UserDto userDto) { User user = new User();
	  user.setUser_id(userDto.getFirstName() + " " + userDto.getLastName());
	  user.setEmail(userDto.getEmail()); 
	  user.setPassword(passwordEncoder.encode(userDto.getPassword()));
	  
	   user.setStatus("admin"); userRepository.save(user); }
	 

    @Override
	public void saveUserUrl(UserUrlDto userUrlDto) {
		URLDetails dtl = new URLDetails();
		Date d= new Date();
		dtl.setUrl(userUrlDto.getSaveUrl());
		dtl.setStatus(userUrlDto.getUrlType());
		dtl.setCreated_on(d);
		saveURlRepository.save(dtl);
	}
    
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getUser_id().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

	/*
	 * private Role checkRoleExist(){ Role role = new Role();
	 * role.setName("ROLE_ADMIN"); return roleRepository.save(role); }
	 */

	public List<UserUrlDto> findAllDetails(int PageNo, int PageSize) {
		Pageable pageable  = PageRequest.of(PageNo - 1, PageSize);
		Page<URLDetails> urls = reportRepository.findAll(pageable);
		return urls.stream()
				.map((usersurl) -> mapToUserDto(usersurl))
				.collect(Collectors.toList());
}

	private UserUrlDto mapToUserDto(URLDetails usersurl) {
		UserUrlDto userUrlDto = new UserUrlDto();
		userUrlDto.setSaveUrl(usersurl.getUrl());
		userUrlDto.setUrlType(usersurl.getStatus());
		userUrlDto.setId(usersurl.getId());
		return userUrlDto;
	}

	@Override
	public Page<URLDetails> findPaginated(int PageNo, int PageSize) {
		Pageable pageable = PageRequest.of(PageNo - 1, PageSize);
		return this.reportRepository.findAll(pageable);
	}

	
}
