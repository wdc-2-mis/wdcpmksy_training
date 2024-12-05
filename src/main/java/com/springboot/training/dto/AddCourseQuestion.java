package com.springboot.training.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AddCourseQuestion {

	@NotNull(message = "Course selection is required")
    private Integer courseId;
	
	@NotBlank(message = "Question should not be empty")
	private String questionText;
	
	@NotNull(message = "Question Marks should not be null")
	@Positive(message = "Marks should be a positive number")
    private Integer marks;
	
	@NotBlank(message = "Option A should not be empty")
	private String option1;
	
	@NotBlank(message = "Option B should not be empty")
	private String option2;
	
	@NotBlank(message = "Option C should not be empty")
	private String option3;
	
	@NotBlank(message = "Option D should not be empty")
	private String option4;
	
	@NotBlank(message = "Correct Answer should not be empty")
	private String correctAnswer;

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	

	public Integer getMarks() {
		return marks;
	}

	public void setMarks(Integer marks) {
		this.marks = marks;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	
	
	
	
}
