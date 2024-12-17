package com.springboot.training.dto;

import java.util.Date;

public class ViewCourseDetails {

private Integer training_id;
    
    private String course_name;

    private String course_description;
    
    private Integer noof_question;
    
    private Integer duration_exam;
    
    private Integer min_pass_marks;
    
    private Date course_start;
    
    private Date course_end;
    
    private String file_extension;
    
    private String file_name;
    
    private String file_path;
    
    private String status;
	
	private Integer useridcount;
	
	
	
	
	
	public ViewCourseDetails(Integer training_id, String course_name, String course_description, Integer noof_question,
			Integer duration_exam, Integer min_pass_marks, Date course_start, Date course_end, String status, Integer useridcount) {
		super();
		this.training_id = training_id;
		this.course_name = course_name;
		this.course_description = course_description;
		this.noof_question = noof_question;
		this.duration_exam = duration_exam;
		this.min_pass_marks = min_pass_marks;
		this.course_start = course_start;
		this.course_end = course_end;
		this.status = status;
		this.useridcount = useridcount;
	}


	public Integer getTraining_id() {
		return training_id;
	}


	public void setTraining_id(Integer training_id) {
		this.training_id = training_id;
	}


	public String getCourse_name() {
		return course_name;
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


	public Integer getMin_pass_marks() {
		return min_pass_marks;
	}


	public void setMin_pass_marks(Integer min_pass_marks) {
		this.min_pass_marks = min_pass_marks;
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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getUseridcount() {
		return useridcount;
	}


	public void setUseridcount(Integer useridcount) {
		this.useridcount = useridcount;
	}
	
	
	
	
	
	 
	
}
