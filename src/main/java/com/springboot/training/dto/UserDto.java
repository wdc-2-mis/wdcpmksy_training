package com.springboot.training.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
    @NotEmpty(message = "First Name must not be empty")
    private String firstName;
    @NotEmpty(message = "Last Name must not be empty")
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    
    @NotEmpty(message = "Password should not be empty")
    private String password;
    
    @NotNull(message = "Please select a State.")
    private Integer state;
    
    @NotNull(message = "Please select a District.")
    private Integer district;
    
    @NotNull(message = "Please select a Block.")
    private Integer block;
    
    
    @NotEmpty(message = "Please Enter Mobile No.")
    private String phone;
    
    @NotEmpty(message = "Address must not be empty")
    private String address;
    
    @NotNull(message = "Please Choose Security Question")
    private Integer securityques;
    
    @NotEmpty(message = "Security Answer should not be empty")
    private String security_answer;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public Integer getBlock() {
		return block;
	}
	public void setBlock(Integer block) {
		this.block = block;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getSecurityques() {
		return securityques;
	}
	public void setSecurityques(Integer securityques) {
		this.securityques = securityques;
	}
	public String getSecurity_answer() {
		return security_answer;
	}
	public void setSecurity_answer(String security_answer) {
		this.security_answer = security_answer;
	}
    
    
}
