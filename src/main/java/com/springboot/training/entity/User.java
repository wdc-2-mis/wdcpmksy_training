package com.springboot.training.entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="lms_user")
public class User {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_reg_id;

    @Column
    private Integer bcode;
    
    @Column(nullable=false)
    private String user_id;

    @Column(nullable=false, unique=true)
    private String mobile_no;
    
    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String status;
    
    @Column(nullable=false)
    private String user_type;
    
    @Column(nullable=false)
    private String password;
    
    @Column
    private String otp;
    
    @Column
    private LocalDateTime otpExpirationTime;
    
   // private Set<LMSTrainingDetails> lmsTrainingDetails = new HashSet<LMSTrainingDetails>(0);
    
    @Column
    private String registration_id; 
    
    @Column(nullable=false)
    private String user_name;

    @Column(nullable=false)
    private Integer dcode;

    @Column(nullable=false)
    private Integer stcode;
	
    @Column(nullable=false)
    private Integer security_id;
    
    @Column(nullable=false)
    private String address;
    @ManyToOne
    @JoinColumn(name = "bcode", referencedColumnName = "bcode", insertable = false, updatable = false)
    private IwmpBlock iwmpBlock;

    @ManyToOne
    @JoinColumn(name = "dcode", referencedColumnName = "dcode", insertable = false, updatable = false)
    private IwmpDistrict iwmpDistrict;

    @ManyToOne
    @JoinColumn(name = "stcode", referencedColumnName = "st_code", insertable = false, updatable = false)
    private IwmpState iwmpState;
    
    @ManyToOne
    @JoinColumn(name = "security_id", referencedColumnName = "security_id", insertable = false, updatable = false)
    private LmsSecurityQuestion lmsSecurityQuestion;
    
    @Column(nullable=false)
    private String security_answer;
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public Integer getUser_reg_id() {
		return user_reg_id;
	}

	public void setUser_reg_id(Integer user_reg_id) {
		this.user_reg_id = user_reg_id;
	}

	public Integer getBcode() {
		return bcode;
	}

	public void setBcode(Integer bcode) {
		this.bcode = bcode;
	}

	public String getUser_id() {
//		return user_id;
		
		String[] usr = user_id.split("\\s");
		StringBuilder strb = new StringBuilder();
		for(String str : usr) {
			strb.append(Character.toTitleCase(str.charAt(0)))
			.append(str.substring(1))
			.append(" ");
		}
		return strb.toString().trim();
		
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

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getOtpExpirationTime() {
		return otpExpirationTime;
	}

	public void setOtpExpirationTime(LocalDateTime otpExpirationTime) {
		this.otpExpirationTime = otpExpirationTime;
	}

	public String getRegistration_id() {
		return registration_id;
	}

	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Integer getDcode() {
		return dcode;
	}

	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}

	public Integer getStcode() {
		return stcode;
	}

	public void setStcode(Integer stcode) {
		this.stcode = stcode;
	}

	public IwmpBlock getIwmpBlock() {
		return iwmpBlock;
	}

	public void setIwmpBlock(IwmpBlock iwmpBlock) {
		this.iwmpBlock = iwmpBlock;
	}

	public IwmpDistrict getIwmpDistrict() {
		return iwmpDistrict;
	}

	public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
		this.iwmpDistrict = iwmpDistrict;
	}

	public IwmpState getIwmpState() {
		return iwmpState;
	}

	public void setIwmpState(IwmpState iwmpState) {
		this.iwmpState = iwmpState;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LmsSecurityQuestion getLmsSecurityQuestion() {
		return lmsSecurityQuestion;
	}

	public void setLmsSecurityQuestion(LmsSecurityQuestion lmsSecurityQuestion) {
		this.lmsSecurityQuestion = lmsSecurityQuestion;
	}

	public String getSecurity_answer() {
		return security_answer;
	}

	public void setSecurity_answer(String security_answer) {
		this.security_answer = security_answer;
	}

	public Integer getSecurity_id() {
		return security_id;
	}

	public void setSecurity_id(Integer security_id) {
		this.security_id = security_id;
	}

	
	/*
	 * @OneToMany(fetch=FetchType.LAZY, mappedBy="user") public
	 * Set<LMSTrainingDetails> getLmsTrainingDetails() { return lmsTrainingDetails;
	 * }
	 * 
	 * public void setLmsTrainingDetails(Set<LMSTrainingDetails> lmsTrainingDetails)
	 * { this.lmsTrainingDetails = lmsTrainingDetails; }
	 */
	
	
	
	
}
