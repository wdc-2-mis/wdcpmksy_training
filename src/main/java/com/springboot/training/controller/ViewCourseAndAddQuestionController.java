package com.springboot.training.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.training.dto.CourseDetails;
import com.springboot.training.dto.ViewCourseDetails;
import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;
import com.springboot.training.repository.CourseDtlRepository;
import com.springboot.training.repository.CousreDetailBeanRepository;
import com.springboot.training.repository.QuestionRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewCourseAndAddQuestionController {

	@Autowired
	CourseDtlRepository courseDtlRepo;
	
	@Autowired
	CousreDetailBeanRepository courseDtlBeanRepo;
	
	@Autowired
	QuestionRepository qustnRepo;
	
	
	@GetMapping("/getCourseDetail")
	public String viewCourseDtlAndAddQustn(HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");
//		List<LMSTrainingDetails> lmsTrainingList =  new ArrayList<>();
//		lmsTrainingList = courseDtlRepo.findAll();
		List<ViewCourseDetails> courseDtlList =  new ArrayList<>();
		courseDtlList = courseDtlBeanRepo.getCousrseDetails();
		
//		List<ViewCourseDetails> crseDtLst =  new ArrayList<>();
//		for(LMSTrainingDetails dtl: lmsTrainingList) {
//			
//			for(ViewCourseDetails crsdtl : courseDtlList) {
//				if(dtl.getTraining_id().equals(crsdtl.getTraining_id())) {
//					ViewCourseDetails courseDtl = new ViewCourseDetails();
//					courseDtl.setTraining_id(crsdtl.getTraining_id());
//					courseDtl.setCourse_name(dtl.getCourse_name());
//					courseDtl.setCourse_description(dtl.getCourse_description());
//					courseDtl.setNoof_question(dtl.getNoof_question());
//					courseDtl.setCourse_start(dtl.getCourse_start());
//					courseDtl.setCourse_end(dtl.getCourse_end());
//					courseDtl.setUseridcount(crsdtl.getUseridcount());
//					courseDtl.setStatus(dtl.getStatus());
//					crseDtLst.add(courseDtl);
//				}
//			}
//		}
		model.addAttribute("lmsTrainingList", courseDtlList);
		model.addAttribute("lmsTrainingListSize", courseDtlList.size());
		model.addAttribute("userId", userId);
		return "viewCourseDetails";
		
	}
	
	@GetMapping("/viewCourseDetail/{id}")
	public String viewCourseDtl(HttpSession session, @PathVariable("id") int id, Model model) {
		String userId = (String) session.getAttribute("userId");
		List<LMSTrainingDetails> lmsTrainingList =  new ArrayList<>();
		LMSTrainingDetails list = courseDtlRepo.findById(id).orElse(null);
		lmsTrainingList.add(list);
		List<LmsTrainingQuestion> qList = new ArrayList<>();
		qList = qustnRepo.findByTrainingId(id);
		model.addAttribute("lmsTrainingList", lmsTrainingList);
		model.addAttribute("lmsTrainingListSize", lmsTrainingList.size());
		model.addAttribute("userId", userId);
		model.addAttribute("questionList", qList);
		model.addAttribute("questionListSize", qList.size());
		model.addAttribute("courseDesc",list.getCourse_description());
//		List<CourseDetails> courseDtlList =  new ArrayList<>();
//		for(LMSTrainingDetails dtl: lmsTrainingList) {
//			CourseDetails courseDtl = new CourseDetails();
//			courseDtl.setCname(dtl.getCourse_name());
//			courseDtl.setCdesc(dtl.getCourse_description());
//			courseDtl.setTotquest(dtl.getNoof_question());
//		}
		
		return "getCourseDetails";
		
	}
}
