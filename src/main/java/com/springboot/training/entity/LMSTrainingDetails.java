package com.springboot.training.entity;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="lms_training_details", schema = "public")
public class LMSTrainingDetails {

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer training_id;
    
    @Column(nullable=false, unique=true)
    private String course_name;

    @Column(nullable=false, unique=true)
    private String course_description;
    
    @Column
    private Integer noof_question;
    
    @Column
    private Integer duration_exam;
    
    @Column
    private Integer min_pass_marks;
    
    @Column
    private Date course_start;
    
    @Column
    private Date course_end;
    
    @Column
    private String file_extension;
    
    @Column
    private String file_name;
    
    @Column
    private String file_path;
    
    
    @Column
    private String status;
    
    @Column
    private String requested_ip;
    
    @Column
    private String updated_by;
    
    @Column
    private Date updated_date;
    
    @Column
    private String created_by;
    
    @Column
    private Date created_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_reg_id", referencedColumnName = "user_reg_id", nullable = false) 
    private User user;
    
	public Integer getTraining_id() {
		return training_id;
	}

	public void setTraining_id(Integer training_id) {
		this.training_id = training_id;
	}

	public String getCourse_name() {
		String[] crse = course_name.split("\\s");
		StringBuilder strb = new StringBuilder();
		for(String str : crse) {
			strb.append(Character.toTitleCase(str.charAt(0)))
			.append(str.substring(1))
			.append(" ");
		}
		return strb.toString().trim();
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_description() {
		return course_description;
	}

	public void setCourse_description(String course_description) {
		this.course_description = course_description;
	}

	public Integer getNoof_question() {
		return noof_question;
	}

	public void setNoof_question(Integer noof_question) {
		this.noof_question = noof_question;
	}

	public Integer getDuration_exam() {
		return duration_exam;
	}

	public void setDuration_exam(Integer duration_exam) {
		this.duration_exam = duration_exam;
	}

	
	public Date getCourse_start() {
		return course_start;
	}

	public void setCourse_start(Date course_start) {
		this.course_start = course_start;
	}

	public Date getCourse_end() {
		return course_end;
	}

	public void setCourse_end(Date course_end) {
		this.course_end = course_end;
	}

	public String getFile_extension() {
		return file_extension;
	}

	public void setFile_extension(String file_extension) {
		this.file_extension = file_extension;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequested_ip() {
		return requested_ip;
	}

	public void setRequested_ip(String requested_ip) {
		this.requested_ip = requested_ip;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Integer getMin_pass_marks() {
		return min_pass_marks;
	}

	public void setMin_pass_marks(Integer min_pass_marks) {
		this.min_pass_marks = min_pass_marks;
	}


	
}
