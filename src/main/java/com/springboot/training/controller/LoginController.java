package com.springboot.training.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.training.dto.CourseDetails;
import com.springboot.training.dto.UserDto;
import com.springboot.training.dto.UserUrlDto;
import com.springboot.training.entity.URLDetails;
import com.springboot.training.entity.User;
import com.springboot.training.service.UserService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class LoginController {

    private UserService userService;
    
    

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }
    
	
	  @GetMapping("/userurl") 
	  public String userurl(Model model){ 
		  UserUrlDto usersurl = new UserUrlDto();
		  model.addAttribute("usersurl" , usersurl);
		  return "userurl"; 
		  }
	 
	  @PostMapping("/saveurl")
	    public String saveurl(@Valid @ModelAttribute("usersurl") UserUrlDto userUrlDto, UserDto userDto,
	                               BindingResult result,
	                               Model model){
		   if(result.hasErrors()){
	            model.addAttribute("usersurl", userUrlDto);
	            return "/userurl";
	        }
	        userService.saveUserUrl(userUrlDto);
	        return "redirect:/userurl?success";
	    }
	  
	  
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/send-otp") 
    @ResponseBody public String sendOtp(@RequestParam String username) 
    { 
    	User user = userService.findUserByEmail(username); 
    	if (user != null) 
    	{ 
    		userService.generateAndSendOtp(username); 
    		return "OTP sent successfully to registered Email-Id"; 
    		} 
    	else 
    	{ 
    		return "User not found"; 
    		} 
    	}
    
    @PostMapping("/otp-login")
    public String otpLogin(@RequestParam String username, @RequestParam String otp, HttpSession session) {
        User user = userService.findUserByEmail(username);
        System.out.println("i am in...");
        if (user != null && otp.equals(user.getOtp()) && user.getOtpExpirationTime().isAfter(LocalDateTime.now())) {
            session.setAttribute("userId", user);
            session.setAttribute("regid", user.getUser_reg_id());
            session.setAttribute("userType", user.getUser_type());
            return "redirect:/login"; 
        }

        return "redirect:/login?error=Invalid OTP"; 
    }
    
     
     @GetMapping("/register")
    public String showRegistrationForm(Model model){
       
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    
    
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
   
    @GetMapping("/report")
    public String report(Model model){
        return findPaginated(1, model);
    } 

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo, Model model)
    {
    	int pageSize = 12;
    	Page<URLDetails> page = userService.findPaginated(pageNo, pageSize);
    	List<URLDetails> list  = page.getContent();
    	model.addAttribute("currentPage", pageNo);
    	model.addAttribute("totalPages", page.getTotalPages());
    	model.addAttribute("totalItems", page.getTotalElements());
    	model.addAttribute("details", list); 
    	
    	return "report";
    }

    @GetMapping("/showCourseDetail")
    public String showCourseDetail(HttpSession session, Model model){
    	 String userId = (String) session.getAttribute("userId"); 
    	 Integer regid =  Integer.parseInt(session.getAttribute("regid").toString());
     	 String userType = (String) session.getAttribute("userType");
    	 CourseDetails curl = new CourseDetails();
		 model.addAttribute("curl" , curl);
		 model.addAttribute("userId", userId);
		 return "showCoursedtl";
    }

    @GetMapping("/userlogin")
    public String userlogin(){
        return "userlogin";
    }
    
    @PostMapping("/saveCourseDetails")
    public String saveCourseDetails(@Valid @ModelAttribute("completeurl") CourseDetails courseDetails, UserDto userDto,
                               BindingResult result, Model model, HttpSession session){
    	
    	//session = request.getSession(true);
    	String userId = (String) session.getAttribute("userId"); 
    	Integer regid =  Integer.parseInt(session.getAttribute("regid").toString());
    	String userType = (String) session.getAttribute("userType");
    	
	   if(result.hasErrors()){
            model.addAttribute("completeurl", courseDetails);
            return "/showCoursedtl";
        }
        
        try {
        		MultipartFile mfile=courseDetails.getTheFile();
        		int mid=0, k=0;
        		String filePath; 
        		String ext="", file_name = "", concatinate = ".";
		
        		float size = mfile.getSize();
        		size = size / 1024;
        		if(size/1024 > 20)
        		{
        			return "File size should be less than 20 MB";
        		}
        		byte[] bytes = mfile.getBytes();
        		String s1 = new String(bytes);
			
        		if (s1 != null && !s1.equals(""))
        			s1 = s1.substring(0, 2);

				if (s1.startsWith("mz") || s1.startsWith("MZ") || s1.startsWith("4d5a")
					|| s1.startsWith("7f454c46") || s1.startsWith("7F454C46")
					|| s1.startsWith("cafebabe") || s1.startsWith("CAFEBABE") 
					|| s1.startsWith("feedface") || s1.startsWith("FEEDFACE")) 
				{
					return "Please select Only .pdf, file for upload!"+"\\n file content exe/malware or Others";
				}
		 
				//filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/vanyatradoc";
				filePath="D:\\vanyatradoc\\";
				String fileName = mfile.getOriginalFilename();
				
				Pattern p = Pattern.compile("[.]");
			    Matcher matcher = p.matcher(fileName);
			    while(matcher.find()) {
			        k++;
			    }
			    if(k>1)
			    	return "Please upload a valid file";
				
				
				if(mfile.isEmpty() || fileName.isEmpty())
				{
					return "Please upload a valid file";
				}
				File file = new File(filePath);
				if (!file.exists()) 
				{
					file.mkdir();
				}
				mid = fileName.lastIndexOf(".");
				ext = fileName.substring(mid + 1, fileName.length()); 
				if ((ext.compareToIgnoreCase("") == 0) || (ext.compareToIgnoreCase("pdf") == 0)) 
				{
					file_name = fileName;
					// file_name = file_name.concat(concatinate).concat(ext);
					if (!file_name.equals("")) 
					{
						File fileToCreate = new File(filePath, file_name);
						if (!fileToCreate.exists()) 
						{
							BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToCreate));
							outputStream.write(bytes);
							outputStream.close();
						}
					}
					//return "success";
				}
				else {
						return "fail";
				}
				
				userService.saveCourseDetailUrl(courseDetails, ext, file_name, filePath, userId, regid);
		 
		 	} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "error";
			}	
		   
        return "redirect:/showCourseDetail?success";
    }

    
}
