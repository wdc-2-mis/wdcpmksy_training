package com.springboot.training.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lms_m_grade", schema = "public")
public class LMSMGrade {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id", nullable = false)
    private Integer grade_id;
	
	@Column(name = "min_per")
    private Integer minPer;
	
	@Column(name = "max_per")
    private Integer maxPer;
	
	@Column(name = "grade_desc")
    private String gradeDesc;

	public Integer getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}

	public Integer getMinPer() {
		return minPer;
	}

	public void setMinPer(Integer minPer) {
		this.minPer = minPer;
	}

	public Integer getMaxPer() {
		return maxPer;
	}

	public void setMaxPer(Integer maxPer) {
		this.maxPer = maxPer;
	}

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}
	
	
}
