package com.springboot.training.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.training.dto.UserDto;
import com.springboot.training.dto.UserUrlDto;
import com.springboot.training.entity.URLDetails;
import com.springboot.training.entity.User;
import com.springboot.training.service.UserService;

import java.util.List;

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
    public String showCourseDetail(){
        return "showCoursedtl";
    }

    @GetMapping("/userlogin")
    public String userlogin(){
        return "userlogin";
    }
    
}
