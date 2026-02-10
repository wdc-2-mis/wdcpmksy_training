package com.springboot.training.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.InputStream; 
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;
import com.springboot.training.entity.LmsUserQuizDetails;
import com.springboot.training.repository.LmsUserQuizDetailsRepository;
import com.springboot.training.repository.UserRepository;
import com.springboot.training.service.UserCourseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserCourseController {

	@Autowired
	private UserCourseService ucSer;
	
	@Autowired
	LmsUserQuizDetailsRepository userQuizRepo;
	
	@Autowired
    UserRepository repo;
	
	private final TemplateEngine templateEngine;

	@PostMapping("/userCourse")
	public String getUserCourse(HttpSession session, Model model, HttpServletRequest request, @RequestParam("regid")Integer regid) {
		System.out.println("after login session:" +session.getId());
		String userId = repo.findusername(regid);
		Integer userRegId = regid;
		Integer totMarks = 0;
		Integer courseId = 0;
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
		LinkedHashMap<Integer, Boolean> firstTimeUser = new LinkedHashMap<>();
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
				LmsUserQuizDetails quizDetails = ucSer.getquizDetails(courseId, userRegId);

//				boolean passed = quizDetails.getStatus().equalsIgnoreCase("P");
////				boolean passed = ucSer.isUserPassed(userRegId, courseId);
//				userPassed.put(courseId, passed);
//
//				model.addAttribute("userPassed", userPassed);
				
				if (quizDetails != null) {
				    boolean passed = quizDetails.getStatus().equalsIgnoreCase("P");
				    userPassed.put(courseId, passed);
				    firstTimeUser.put(courseId, false);
				} 
				else {
				    firstTimeUser.put(courseId, true);
				}

				model.addAttribute("firstTimeUser", firstTimeUser);
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
			String fileName = courseDetails.getFile_name().replaceAll("\\s", "%20");; 
			String fileExtension = courseDetails.getFile_extension(); 
			String filePath = "https://wdcpmksylms.dolr.gov.in"+courseDetails.getFile_path()+fileName+"."+fileExtension; 
			URL url = new URL(filePath); 
			URLConnection connection = url.openConnection(); 
			try (InputStream inputStream = connection.getInputStream(); 
					OutputStream outputStream = response.getOutputStream()) { 
					response.setContentType("application/pdf"); 
					response.setHeader("Content-Disposition", "inline; filename=\"" + courseDetails.getFile_name() + "." + fileExtension + "\""); 
					byte[] buffer = new byte[4096]; 
					int bytesRead; 
					while ((bytesRead = inputStream.read(buffer)) != -1) { 
						outputStream.write(buffer, 0, bytesRead); 
					} 
					outputStream.flush();
					}
			} 
		else { 
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course details not found"); 
			} 
		}
	
	public UserCourseController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

	@PostMapping("/downloadCertificate")
	public void downloadCertificate(@RequestParam("training_id") Integer trainingId,@RequestParam("userRegId") Integer userRegId, @RequestParam("type") String type,
			HttpServletResponse response) throws IOException, DocumentException {
		
		
		LmsUserQuizDetails object = userQuizRepo.getQuizDetails(trainingId, userRegId);
		
		double percntge = 0.00;
		String progress = "";
		String grade = "";
		
		if(object.getStatus().equals("P")) {
			percntge = (object.getTotalMarks() > 0) ? ((double) object.getMarksObtained() / object.getTotalMarks()) * 100 : 0;
			progress = (percntge >= 70 && percntge <= 100) ? "Excellent" : 
		           (percntge >= 60 && percntge < 70) ? "Very Good" : 
		           (percntge >= 50 && percntge < 60) ? "Good" : 
		        	   (percntge >= 40 && percntge < 50) ? "Pass":
		        		   "Fail";

			grade = object.getGrade()==null?progress:object.getGrade().getGradeDesc();
		}

		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		Document document = new Document(layout);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);
		document.open();
		
		
		

		if (type.equals("completion")) {
			ClassPathResource imgResource = new ClassPathResource("/static/images/Completion_Certificate.jpg");
			byte[] imgBytes = FileCopyUtils.copyToByteArray(imgResource.getInputStream());

			Image background = Image.getInstance(imgBytes);
			background.setAbsolutePosition(0, 0);
			background.scaleToFit(PageSize.A4.getHeight(), PageSize.A4.getWidth() + 10);
			document.add(background);

			Paragraph p = new Paragraph(object.getUser().getUser_id(),
					FontFactory.getFont(FontFactory.TIMES, 65, new BaseColor(5, 71, 42)));

			// Set x and y coordinates
			PdfContentByte canvas = pdfWriter.getDirectContent();
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 420, 300, 0);

			p = new Paragraph(object.getTrainingDetails().getCourse_name(),
					FontFactory.getFont(FontFactory.TIMES_BOLD, 20, new BaseColor(5, 71, 42)));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 420, 230, 0);

			Date date = object.getCreatedDate();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
			String strDate = formatter.format(date);

			p = new Paragraph("on " + strDate.replaceAll("-", " "),
					FontFactory.getFont(FontFactory.TIMES, 18, new BaseColor(26, 17, 16)));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 420, 137, 0);

			p = new Paragraph("Grade: " + grade, FontFactory.getFont(FontFactory.TIMES, 16, new BaseColor(26, 17, 16)));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 126, 100, 0);

			ClassPathResource imgResource2 = new ClassPathResource("/static/images/Guite_sir_signature1.png");
			byte[] imgBytes2 = FileCopyUtils.copyToByteArray(imgResource2.getInputStream());

			Image logo2 = Image.getInstance(imgBytes2);
			logo2.scaleAbsolute(120, 30); // resize the logo
			logo2.setAbsolutePosition(580, 100);

			document.add(logo2);

			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			String strDate1 = formatter1.format(LocalDate.now());

			p = new Paragraph(strDate1, FontFactory.getFont(FontFactory.TIMES, 16, new BaseColor(26, 17, 16)));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 153, 65, 0);
			
		}else if (type.equals("participation")) {
			
			ClassPathResource imgResource = new ClassPathResource("/static/images/Participation_Certificate.jpg");
			byte[] imgBytes = FileCopyUtils.copyToByteArray(imgResource.getInputStream());

			Image background = Image.getInstance(imgBytes);
			background.setAbsolutePosition(0, 0);
			background.scaleToFit(PageSize.A4.getHeight(), PageSize.A4.getWidth()+10);
			document.add(background);

			Paragraph p = new Paragraph(object.getUser().getUser_id(),
					FontFactory.getFont(FontFactory.TIMES, 65, new BaseColor(128, 0, 32)));

			// Set x and y coordinates
			PdfContentByte canvas = pdfWriter.getDirectContent();
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 425, 280, 0);

			p = new Paragraph(object.getTrainingDetails().getCourse_name(),
					FontFactory.getFont(FontFactory.TIMES, 18, new BaseColor(26, 17, 16)));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 420, 210, 0);

			Date date = object.getCreatedDate();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			String strDate = formatter.format(date);

			p = new Paragraph("on " + strDate.replaceAll("-", " ")+", organised by the",
					FontFactory.getFont(FontFactory.TIMES, 18, new BaseColor(26, 17, 16)));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 420, 175, 0);

//			p = new Paragraph("Grade: " + grade, FontFactory.getFont(FontFactory.TIMES, 16, new BaseColor(26, 17, 16)));
//			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 126, 100, 0);

			ClassPathResource imgResource2 = new ClassPathResource("/static/images/Guite_sir_signature1.png");
			byte[] imgBytes2 = FileCopyUtils.copyToByteArray(imgResource2.getInputStream());

			Image logo2 = Image.getInstance(imgBytes2);
			logo2.scaleAbsolute(120, 30); // resize the logo
			logo2.setAbsolutePosition(580, 94);

			document.add(logo2);

			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			String strDate1 = formatter1.format(LocalDate.now());

			p = new Paragraph(strDate1, FontFactory.getFont(FontFactory.TIMES, 16, new BaseColor(26, 17, 16)));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(p), 153, 64, 0);
		}

		document.close();
		pdfWriter.close();

		response.setContentType("application/pdf");
		if(type.equals("completion")) 
			response.setHeader("Content-Disposition", "attachment; filename=Completion Certificate.pdf");
		else if(type.equals("participation"))
			response.setHeader("Content-Disposition", "attachment; filename=Participation Certificate.pdf");
		response.getOutputStream().write(byteArrayOutputStream.toByteArray());
	}
	
	@PostMapping("/viewCertificate")
	public String getCertificate(@RequestParam Integer trainingId, @RequestParam("regid")Integer regid, Model model, HttpServletRequest request) {

		String userId = repo.findusername(regid);;
		Integer userRegId = regid;
		Integer courseId = trainingId;
		String courseName;
		String userName;
		Integer questionsAttempted;
		Integer marksObtained;
		Boolean passed;
		
		
//		String[] nameParts = userId.split(" ");
//		LinkedHashMap<Integer, Boolean> userPassed = new LinkedHashMap<>();
		
//		List<LMSTrainingDetails> courses = ucSer.getUserCourse();
		
//		for (LMSTrainingDetails course : courses) {
//			courseId = course.getTraining_id();

			// Check if user has passed the course
//			boolean passed = ucSer.isUserPassed(userRegId, courseId);
//			userPassed.put(courseId, passed);
//
//			model.addAttribute("passed", passed);
//		}
		
		LMSTrainingDetails course = ucSer.getCourseById(courseId).orElse(null);
		
		courseId = course.getTraining_id();
		courseName = course.getCourse_name();
		userName = userId;
		
		LmsUserQuizDetails quizDetails = ucSer.getQuizResult(courseId, userRegId);
		questionsAttempted = quizDetails.getQuestionAttempt();
		marksObtained = quizDetails.getMarksObtained();
		passed = quizDetails.getStatus().equalsIgnoreCase("P");
		
//		questionsAttempted = ucSer.getQuestionsAttempted(courseId, userRegId);
//		marksObtained = ucSer.calculateMarksObtained(courseId, userRegId);
		
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("courseId", courseId);
		model.addAttribute("courseName", courseName);
		model.addAttribute("passed", passed);
		model.addAttribute("totalQuestions", course.getAttempt_question());
	    model.addAttribute("passingMarks", course.getMin_pass_marks());
	    model.addAttribute("trainingId", trainingId);
	    model.addAttribute("userRegId", userRegId);
		model.addAttribute("questionsAttempted", questionsAttempted);
		model.addAttribute("marksObtained", marksObtained);

		return "certificate";
	}
	
}
