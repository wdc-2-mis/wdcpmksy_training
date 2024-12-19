package com.springboot.training.entity;

import java.time.LocalDateTime;

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
    private Integer marksIbtained;
	
	@Column(name = "question_attempt")
	private Integer questionAttempt;

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
	@JoinColumn(name = "user_reg_id", referencedColumnName = "user_reg_id", insertable = false, updatable = false)
	private User user;
	
	
	
	
	
	
	
	
	

}
