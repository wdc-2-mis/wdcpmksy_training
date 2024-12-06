package com.springboot.training.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lms_user_question_answer", schema = "public")
public class LmsUserQuestionAnswer { 
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_answer_id", nullable = false)
    private Integer user_answer_id;
	
	@Column(name = "training_id")
    private Integer trainingId;
	
	@Column(name = "question_id")
    private Integer questionId;
	
	@Column(name = "user_answer", length = 200)
    private String userAnswer;
	
	@Column(name = "user_reg_id")
    private Integer userRegId;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "requested_ip", length = 25)
    private String requestedIp;

    @Column(name = "updated_by", length = 25)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "created_by", length = 25)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
	
	@ManyToOne
	@JoinColumn(name = "training_id", referencedColumnName = "training_id", insertable = false, updatable = false)
	private LMSTrainingDetails trainingDetails;
	
	@ManyToOne
	@JoinColumn(name = "question_id", referencedColumnName = "question_id", insertable = false, updatable = false)
	private LmsTrainingQuestion lmsTrainingQuestion;

	@ManyToOne
	@JoinColumn(name = "user_reg_id", referencedColumnName = "user_reg_id", insertable = false, updatable = false)
	private User user;

	public Integer getUser_answer_id() {
		return user_answer_id;
	}

	public void setUser_answer_id(Integer user_answer_id) {
		this.user_answer_id = user_answer_id;
	}

	public Integer getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public Integer getUserRegId() {
		return userRegId;
	}

	public void setUserRegId(Integer userRegId) {
		this.userRegId = userRegId;
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

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LMSTrainingDetails getTrainingDetails() {
		return trainingDetails;
	}

	public void setTrainingDetails(LMSTrainingDetails trainingDetails) {
		this.trainingDetails = trainingDetails;
	}

	public LmsTrainingQuestion getLmsTrainingQuestion() {
		return lmsTrainingQuestion;
	}

	public void setLmsTrainingQuestion(LmsTrainingQuestion lmsTrainingQuestion) {
		this.lmsTrainingQuestion = lmsTrainingQuestion;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
	
	
	
	

}
