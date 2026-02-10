package com.springboot.training.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
/*import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;*/
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.training.dto.AddCourseQuestion;
import com.springboot.training.dto.CourseDetails;
import com.springboot.training.dto.UserDto;
import com.springboot.training.dto.UserUrlDto;
import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;
import com.springboot.training.entity.URLDetails;
import com.springboot.training.entity.User;
import com.springboot.training.repository.CourseDtlRepository;
import com.springboot.training.repository.LmsTrainingQuestionRepository;
import com.springboot.training.repository.ReportRepository;
import com.springboot.training.repository.RoleRepository;
import com.springboot.training.repository.SaveURLRepository;
import com.springboot.training.repository.UserRepository;
import com.springboot.training.service.UserService;

import jakarta.servlet.http.*;
import jakarta.transaction.Transactional;
@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private CourseDtlRepository courseDtlRepository;
	private ReportRepository reportRepository;
	private SaveURLRepository saveURlRepository;
	 private RoleRepository roleRepository; 
	 private LmsTrainingQuestionRepository trainingQuestionRepository;
		/* private final JavaMailSender javaMailSender; */
    private PasswordEncoder passwordEncoder;
     public UserServiceImpl(UserRepository userRepository,
				/* RoleRepository roleRepository, */
                           ReportRepository reportRepository,
                           SaveURLRepository saveURlRepository,
                           CourseDtlRepository courseDtlRepository,
                           LmsTrainingQuestionRepository trainingQuestionRepository,
				/* JavaMailSender javaMailSender, */
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
		/* this.roleRepository = roleRepository; */
        this.passwordEncoder = passwordEncoder;
        this.saveURlRepository = saveURlRepository;
        this.reportRepository = reportRepository;
        this.courseDtlRepository = courseDtlRepository;
		/* this.javaMailSender = javaMailSender; */
        this.trainingQuestionRepository = trainingQuestionRepository;
    }
     private String capitalizeName(String name) {
    	    if (name == null || name.isEmpty()) {
    	        return name;
    	    }
    	    // Trim leading/trailing spaces and replace multiple spaces with a single space
    	    name = name.trim().replaceAll("\\s+", " ");

    	    // Capitalize the first letter of each word and ensure the rest are lowercase
    	    String[] nameParts = name.split(" ");
    	    StringBuilder capitalizedName = new StringBuilder();

    	    for (String part : nameParts) {
    	        capitalizedName.append(part.substring(0, 1).toUpperCase())  // Capitalize first letter
    	                       .append(part.substring(1).toLowerCase())         // Lowercase the rest
    	                       .append(" ");                                     // Add a space between words
    	    }

    	    // Remove the trailing space and return the result
    	    return capitalizedName.toString().trim();
    	}

     
      public void saveUser(UserDto userDto) { 
      User user = new User();
      
      String firstName = capitalizeName(userDto.getFirstName().trim());
      String lastName = capitalizeName(userDto.getLastName());
      
      user.setUser_id(firstName + " " + lastName);
      user.setUser_name(firstName + " " + lastName);
   	  user.setMobile_no(userDto.getPhone());
   	  user.setStcode(userDto.getState());
   	  user.setDcode(userDto.getDistrict());
   	  user.setBcode(userDto.getBlock());
   	  user.setUser_type("user");
   	  user.setEmail(userDto.getEmail()); 
   	  user.setAddress(userDto.getAddress());
   	  user.setPassword(passwordEncoder.encode(userDto.getPassword()));
   	  user.setSecurity_id(userDto.getSecurityques());
   	  user.setSecurity_answer(userDto.getSecurity_answer().toUpperCase());
   	  user.setStatus("active"); 
   	  userRepository.save(user); }
	 

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


	@Override
	public void saveCourseDetailUrl(CourseDetails courseDetails, String ext, String file_name, String filePath, String userId, Integer regId) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromdt, todt;
		try {
				fromdt = formatter.parse(courseDetails.getFromdt());
				todt=formatter.parse(courseDetails.getTodt());
			
				LMSTrainingDetails lmsdtl = new LMSTrainingDetails();
				User user = new User();
				user.setUser_reg_id(regId);
				lmsdtl.setCourse_name(courseDetails.getCname());
				lmsdtl.setCourse_description(courseDetails.getCdesc());
				lmsdtl.setNoof_question(Integer.parseInt(courseDetails.getTotquest()));
				lmsdtl.setDuration_exam(Integer.parseInt(courseDetails.getDurexam()));
				lmsdtl.setMin_pass_marks(Integer.parseInt(courseDetails.getPassm()));
				lmsdtl.setAttempt_question(Integer.parseInt(courseDetails.getAttemptquest()));
				lmsdtl.setCourse_start(fromdt);
				lmsdtl.setCourse_end(todt);
				lmsdtl.setFile_extension(ext);
				lmsdtl.setFile_name(file_name);
				lmsdtl.setFile_path(filePath);
				lmsdtl.setStatus("D");
				lmsdtl.setCreated_date(new Date());
				lmsdtl.setCreated_by(userId);
				lmsdtl.setUser(user);
				
				courseDtlRepository.save(lmsdtl);
		
		
		
		} 
		catch (ParseException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void saveaddQuestion(AddCourseQuestion question, HttpSession session, Integer trainingId) {
		LmsTrainingQuestion trainingQuestion = new LmsTrainingQuestion();
		  trainingQuestion.setTrainingId(trainingId);
		  trainingQuestion.setQuestionDescription(question.getQuestionText());
		  trainingQuestion.setQuestionMarks(question.getMarks());
		  trainingQuestion.setOption1(question.getOption1());
		  trainingQuestion.setOption2(question.getOption2());
		  trainingQuestion.setOption3(question.getOption3());
		  trainingQuestion.setOption4(question.getOption4());
		  trainingQuestion.setOptionAnswer(question.getCorrectAnswer());
		  trainingQuestion.setCreatedDate(LocalDateTime.now());
		  trainingQuestion.setUpdatedDate(LocalDateTime.now());
		  trainingQuestion.setStatus("D"); 
		  String userId = (String)
		  session.getAttribute("userId"); 
		  Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
		  trainingQuestion.setUserRegId(regid); 
		  trainingQuestion.setCreatedBy(userId);
		  
		  trainingQuestionRepository.save(trainingQuestion); 
		  
	}
	
	
	public void generateAndSendOtp(String email) {
	    // Generate OTP
	    String otp = generateOtp();

	    // Get user and set OTP and expiration time
	    User user = userRepository.findByEmail(email);
	    if (user != null) {
	        user.setOtp(otp);
	        user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(5)); // OTP valid for 5 minutes
	        userRepository.save(user);

	        // Send OTP to user's email
			/* sendOtpToEmail(user.getEmail(), otp); */
	    }
	}

	private String generateOtp() {
		Random random = new Random(); 
		int otp = 100000 + random.nextInt(900000); 
		String generatedOtp = String.valueOf(otp);
		System.out.println("Generated OTP: " + generatedOtp);
		return generatedOtp;
	}

	@Override
	public boolean verifyUserDetails(String email, Integer securityQuestion, String security_answer) {
		boolean userverify = userRepository.checkUserDtl(email, securityQuestion, security_answer.toUpperCase());
	    	
	     if(userverify)
	     {
	    	 return true;
	     }
	     return false;
	}
 
	@Override
	@Transactional
	@Modifying
	public boolean updatePassword(String email, String newPassword) {
		int updatedRows = userRepository.updateUserPassword(email, passwordEncoder.encode(newPassword));
        return updatedRows > 0;
	}
	/*
	 * private void sendOtpToEmail(String email, String otp) { SimpleMailMessage
	 * message = new SimpleMailMessage(); message.setTo(email);
	 * message.setSubject("One Time Password");
	 * 
	 * // Update the message format String emailContent = String.format(
	 * "Dear User,\n\n%s is the One Time Password for your login which is valid for only 5 minutes. "
	 * +
	 * "Please don't share it with anyone for security reasons.\n\nBest Regards,\nDoLR, Ministry of Rural Development"
	 * , otp);
	 * 
	 * message.setText(emailContent); javaMailSender.send(message); }
	 */
}
