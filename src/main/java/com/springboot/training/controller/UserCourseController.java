package com.springboot.training.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;
import com.springboot.training.service.UserCourseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserCourseController {

	@Autowired
	private UserCourseService ucSer;

	@GetMapping("/userCourse")
	public String getUserCourse(HttpSession session, Model model, HttpServletRequest request) {

		String userId = (String) session.getAttribute("userId");
		Integer userRegId = (Integer) session.getAttribute("regid");
		Integer totMarks = 0;
		Integer courseId = 0;
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
		LinkedHashMap<Integer, Boolean> userPassed = new LinkedHashMap<>();
		List<LMSTrainingDetails> courses = ucSer.getUserCourse();
		for (LMSTrainingDetails course : courses) {
			courseId = course.getTraining_id();

			List<LmsTrainingQuestion> questions = ucSer.findByTrainingIdAndStatus(courseId, "C");
			if (!questions.isEmpty()) {
				totMarks = ucSer.calTotalMarks(courseId);
				model.addAttribute("totMarks", totMarks);
				map.put(courseId, totMarks);
				model.addAttribute("map", map);

				// Check if user has passed the course
				boolean passed = ucSer.isUserPassed(userRegId, courseId);
				userPassed.put(courseId, passed);

				model.addAttribute("userPassed", userPassed);

				LMSTrainingDetails courseDetails = ucSer.getCourseById(courseId).orElse(null);

				if (courseDetails != null) {
					String fileName = courseDetails.getFile_name();
					String fileExtension = courseDetails.getFile_extension();
					String filePath = courseDetails.getFile_path();

					// Store the file details in the model
					model.addAttribute("fileName", fileName);
					model.addAttribute("fileExtension", fileExtension);
					model.addAttribute("filePath", filePath);
				}
				model.addAttribute("courses", courses);
				model.addAttribute("userId", userId);
				model.addAttribute("userRegId", userRegId);
			}
		}
//		model.addAttribute("courses", courses);
//		model.addAttribute("userId", userId);
//		model.addAttribute("userRegId", userRegId);

		return "UserCourse";
	}

	// method to handle the file download
	@GetMapping("/downloadMaterial/{training_id}")
	public void downloadFile(@PathVariable Integer training_id, HttpServletResponse response) throws IOException {
		LMSTrainingDetails courseDetails = ucSer.getCourseById(training_id).orElse(null);
		if (courseDetails != null) {
			String fileName = courseDetails.getFile_name();
			String fileExtension = courseDetails.getFile_extension();
			String filePath = courseDetails.getFile_path();

			File file = new File(filePath + fileName + "." + fileExtension);
			if (file.exists()) {
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + fileName + "." + fileExtension + "\"");

				Files.copy(file.toPath(), response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND,
						"The file " + fileName + "." + fileExtension + " was not found at the specified location.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course details not found");
		}
	}

	@GetMapping("/downloadCertificate")
	public void downloadCertificate(@RequestParam("training_id") Integer trainingId, @RequestParam("type") String type,
			HttpServletResponse response) throws IOException {

		String certificatePath;
		String certificateName;

		if (type.equals("participation")) {
			certificatePath = "classpath:static/certificates/Participation Certificate.pdf";
			certificateName = "Participation Certificate" + ".pdf";
		} else if (type.equals("completion")) {
			certificatePath = "classpath:static/certificates/CompletionCertificate.pdf";
			certificateName = "Completion Certificate" + ".pdf";
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid certificate type");
			return;
		}

		// Get the certificate file
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(certificatePath.replace("classpath:", ""));
		File file = File.createTempFile("original-certificate", ".pdf");
		Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + certificateName + "\"");
		response.setHeader("Content-Length", String.valueOf(file.length()));

		// Copy the file to the response output stream
		Files.copy(file.toPath(), response.getOutputStream());

		// Flush and close the output stream
		response.getOutputStream().flush();
		response.getOutputStream().close();

		// Delete the temporary file
		file.delete();
	}
	
	@GetMapping("/viewCertificate")
	public String getCertificate(@RequestParam Integer trainingId, HttpSession session, Model model, HttpServletRequest request) {

		String userId = (String) session.getAttribute("userId");
		Integer userRegId = (Integer) session.getAttribute("regid");
		Integer courseId = trainingId;
		String courseName;
		String userName;
		Integer questionsAttempted;
		Integer marksObtained;
		
//		String[] nameParts = userId.split(" ");
		LinkedHashMap<Integer, Boolean> userPassed = new LinkedHashMap<>();
		
//		List<LMSTrainingDetails> courses = ucSer.getUserCourse();
		
//		for (LMSTrainingDetails course : courses) {
//			courseId = course.getTraining_id();

			// Check if user has passed the course
			boolean passed = ucSer.isUserPassed(userRegId, courseId);
			userPassed.put(courseId, passed);

			model.addAttribute("passed", passed);
//		}
		
		LMSTrainingDetails course = ucSer.getCourseById(courseId).orElse(null);
		
		courseId = course.getTraining_id();
		courseName = course.getCourse_name();
		userName = userId;
		questionsAttempted = ucSer.getQuestionsAttempted(courseId, userRegId);
		marksObtained = ucSer.calculateMarksObtained(courseId, userRegId);
		
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("courseId", courseId);
		model.addAttribute("courseName", courseName);
		model.addAttribute("totalQuestions", course.getAttempt_question());
	    model.addAttribute("passingMarks", course.getMin_pass_marks());
	    model.addAttribute("trainingId", trainingId);
	    model.addAttribute("userRegId", userRegId);
		model.addAttribute("questionsAttempted", questionsAttempted);
		model.addAttribute("marksObtained", marksObtained);

		return "certificate";
	}
	
}
