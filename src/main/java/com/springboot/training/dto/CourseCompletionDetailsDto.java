package com.springboot.training.dto;

public class CourseCompletionDetailsDto {

	
	private Integer user_reg_id;

    private String user_id;

    private String mobile_no;
    
    private String email;

    private String status;
    
    private String user_type;
    
    private String registration_id;

	public CourseCompletionDetailsDto(Integer user_reg_id, String user_id, String mobile_no, String email,
			String status, String user_type, String registration_id) {
		super();
		this.user_reg_id = user_reg_id;
		this.user_id = user_id;
		this.mobile_no = mobile_no;
		this.email = email;
		this.status = status;
		this.user_type = user_type;
		this.registration_id = registration_id;
	}

	public Integer getUser_reg_id() {
		return user_reg_id;
	}

	public void setUser_reg_id(Integer user_reg_id) {
		this.user_reg_id = user_reg_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getRegistration_id() {
		return registration_id;
	}

	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	} 
    
    
    
}
