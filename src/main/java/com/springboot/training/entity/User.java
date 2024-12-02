package com.springboot.training.entity;
import lombok.*;
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
    
   // private Set<LMSTrainingDetails> lmsTrainingDetails = new HashSet<LMSTrainingDetails>(0);
    
    
    
	
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

	/*
	 * @OneToMany(fetch=FetchType.LAZY, mappedBy="user") public
	 * Set<LMSTrainingDetails> getLmsTrainingDetails() { return lmsTrainingDetails;
	 * }
	 * 
	 * public void setLmsTrainingDetails(Set<LMSTrainingDetails> lmsTrainingDetails)
	 * { this.lmsTrainingDetails = lmsTrainingDetails; }
	 */
	
	
	
	
}
