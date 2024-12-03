package com.springboot.training.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lms_training_question", schema = "public")
public class LmsTrainingQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "lms_training_question_seq")
    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Column(name = "training_id")
    private Integer trainingId;

    @Column(name = "question_description", length = 500)
    private String questionDescription;

    @Column(name = "option1", length = 200)
    private String option1;

    @Column(name = "option2", length = 200)
    private String option2;

    @Column(name = "option3", length = 200)
    private String option3;

    @Column(name = "option4", length = 200)
    private String option4;

    @Column(name = "option_answer", length = 200)
    private String optionAnswer;

    @Column(name = "question_marks")
    private Integer questionMarks;

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
    @JoinColumn(name = "user_reg_id", referencedColumnName = "user_reg_id", insertable = false, updatable = false)
    private User user;
}