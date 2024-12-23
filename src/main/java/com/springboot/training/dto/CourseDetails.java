package com.springboot.training.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CourseDetails {

	@NotEmpty(message = "Course Name should not be empty")
	private String cname;
	
	@NotEmpty(message = "Couse Description should not be empty")
	private String cdesc;
	
	@NotEmpty(message = "Total Question should not be empty")
	private String totquest;
	
	@NotEmpty(message = "Exam Duration should not be empty")
	private String durexam;
	
	/* @NotEmpty(message = "Passing Marks should not be empty") */
	private String passm;
	
	private String fromdt;
	
	private String todt;
	
	private MultipartFile theFile;
	
	private Integer trainingId;
	
	private String attemptquest;
	
	
	public Integer getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCdesc() {
		return cdesc;
	}
	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}
	public String getTotquest() {
		return totquest;
	}
	public void setTotquest(String totquest) {
		this.totquest = totquest;
	}
	public String getDurexam() {
		return durexam;
	}
	public void setDurexam(String durexam) {
		this.durexam = durexam;
	}
	public String getPassm() {
		return passm;
	}
	public void setPassm(String passm) {
		this.passm = passm;
	}
	
	
	public String getFromdt() {
		return fromdt;
	}
	public void setFromdt(String fromdt) {
		this.fromdt = fromdt;
	}
	public String getTodt() {
		return todt;
	}
	public void setTodt(String todt) {
		this.todt = todt;
	}
	
	 public MultipartFile getTheFile() 
	 { 
		 return theFile; 
	 } 
	 public void setTheFile(MultipartFile theFile) 
	 { 
		 this.theFile = theFile; 
	 }
	public String getAttemptquest() {
		return attemptquest;
	}
	public void setAttemptquest(String attemptquest) {
		this.attemptquest = attemptquest;
	}
	 
	
}
