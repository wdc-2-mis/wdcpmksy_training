package com.springboot.training.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.training.dto.CourseDetails;
import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.repository.CourseDtlRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewCourseAndAddQuestionController {

	@Autowired
	CourseDtlRepository courseDtlRepo;
	
	
	@GetMapping("/getCourseDetail")
	public String viewCourseDtlAndAddQustn(HttpSession session, Model model) {
		List<LMSTrainingDetails> lmsTrainingList =  new ArrayList<>();
		lmsTrainingList = courseDtlRepo.findAll();
		model.addAttribute("lmsTrainingList", lmsTrainingList);
		model.addAttribute("lmsTrainingListSize", lmsTrainingList.size());
//		List<CourseDetails> courseDtlList =  new ArrayList<>();
//		for(LMSTrainingDetails dtl: lmsTrainingList) {
//			CourseDetails courseDtl = new CourseDetails();
//			courseDtl.setCname(dtl.getCourse_name());
//			courseDtl.setCdesc(dtl.getCourse_description());
//			courseDtl.setTotquest(dtl.getNoof_question());
//		}
		
		return "viewCourseDetails";
		
	}
}
