package com.springboot.training.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lms_user_quiz_details", schema = "public")
public class LmsUserQuizDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_quiz_id", nullable = false)
    private Integer user_quiz_id;
	
	@Column(name = "training_id")
    private Integer trainingId;
	
	@Column(name = "user_reg_id")
    private Integer userRegId;
	
	@Column(name = "marks_obtained")
    private Integer marksObtained;
	
	@Column(name = "question_attempt")
	private Integer questionAttempt;     
	
	@Column(name = "total_marks")
	private Integer totalMarks; 

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "requested_ip", length = 25)
    private String requestedIp;

    @Column(name = "updated_by", length = 25)
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "created_by", length = 25)
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "training_id", referencedColumnName = "training_id", insertable = false, updatable = false)
	private LMSTrainingDetails trainingDetails;
	
	@ManyToOne
	@JoinColumn(name = "user_reg_id", referencedColumnName = "user_reg_id", insertable = false, updatable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "grade_id", referencedColumnName = "grade_id", insertable = false, updatable = false)
	private LMSMGrade grade;

	
	
	
	public Integer getUser_quiz_id() {
		return user_quiz_id;
	}

	public void setUser_quiz_id(Integer user_quiz_id) {
		this.user_quiz_id = user_quiz_id;
	}

	public Integer getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}

	public Integer getUserRegId() {
		return userRegId;
	}

	public void setUserRegId(Integer userRegId) {
		this.userRegId = userRegId;
	}

	public Integer getMarksObtained() {
		return marksObtained;
	}

	public void setMarksObtained(Integer marksObtained) {
		this.marksObtained = marksObtained;
	}

	public Integer getQuestionAttempt() {
		return questionAttempt;
	}

	public void setQuestionAttempt(Integer questionAttempt) {
		this.questionAttempt = questionAttempt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestedIp() {
		return requestedIp;
	}

	public void setRequestedIp(String requestedIp) {
		this.requestedIp = requestedIp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public LMSTrainingDetails getTrainingDetails() {
		return trainingDetails;
	}

	public void setTrainingDetails(LMSTrainingDetails trainingDetails) {
		this.trainingDetails = trainingDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public LMSMGrade getGrade() {
		return grade;
	}

	public void setGrade(LMSMGrade grade) {
		this.grade = grade;
	}
	
	

}
