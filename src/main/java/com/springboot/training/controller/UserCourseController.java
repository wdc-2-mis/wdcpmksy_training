package com.springboot.training.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;
import com.springboot.training.entity.LmsUserQuizDetails;
import com.springboot.training.repository.LmsUserQuizDetailsRepository;
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
	
	private final TemplateEngine templateEngine;

	@GetMapping("/userCourse")
	public String getUserCourse(HttpSession session, Model model, HttpServletRequest request) {

		String userId = (String) session.getAttribute("userId");
		Integer userRegId = (Integer) session.getAttribute("regid");
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
			String fileName = courseDetails.getFile_name();
			String fileExtension = courseDetails.getFile_extension();
			String filePath = courseDetails.getFile_path();

			File file = new File(filePath + fileName + "." + fileExtension);
			if (file.exists()) {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						"inline; filename=\"" + fileName + "." + fileExtension + "\"");

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
	
	public UserCourseController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

	@GetMapping("/downloadCertificate")
	public void downloadCertificate(@RequestParam("training_id") Integer trainingId,@RequestParam Integer userRegId, @RequestParam("type") String type,
			HttpServletResponse response) throws IOException, DocumentException {
		
		
		LmsUserQuizDetails object = userQuizRepo.getQuizDetails(trainingId, userRegId);
		
		double percntge = 0.00;
		String progress = "";
		String grade = "";
		
		if(object.getStatus().equals("P")) {
			percntge = (object.getTotalMarks() > 0) ? ((double) object.getMarksObtained() / object.getTotalMarks()) * 100 : 0;
			progress = object.getGrade().getGrade_id()==1?"EXCELLENT":object.getGrade().getGrade_id()==2?"VERY GOOD":object.getGrade().getGrade_id()==3?"GOOD":"AVERAGE";
			grade = object.getGrade().getGradeDesc();
		}

		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 50, 50, 50, 50);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);
		document.open();

		PdfContentByte canvas;
		try {
			canvas = pdfWriter.getDirectContent();
			canvas.rectangle(50, 50, 743, 500); // x, y, width, height
			canvas.stroke();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClassPathResource imgResource = new ClassPathResource("/static/images/tiranga_national_emblem.png");
		byte[] imgBytes = FileCopyUtils.copyToByteArray(imgResource.getInputStream());

		Image logo = Image.getInstance(imgBytes);
		logo.scaleAbsolute(50, 50); // resize the logo
		logo.setAbsolutePosition(450, 640); // set the position of the logo

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1, 1, 2 });
		table.getDefaultCell().setFixedHeight(60);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table.getDefaultCell().setBorderColor(BaseColor.WHITE);

		PdfPCell cell = new PdfPCell();
		cell.setPadding(10);
		cell.setBorder(Rectangle.BOTTOM);
		cell.setBorderColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		Paragraph par = new Paragraph("Logo of PIA", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.GRAY));
		par.setAlignment(Element.ALIGN_LEFT);
		cell.addElement(par);
		table.addCell(cell);
		table.addCell(logo);

		cell = new PdfPCell();
		cell.setPadding(10);
		cell.setBorder(Rectangle.BOTTOM);
		cell.setBorderColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		Paragraph para = new Paragraph("Department of Land Resources",
				FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.GRAY));
		para.setAlignment(Element.ALIGN_RIGHT);
		cell.addElement(para);
		table.addCell(cell);

		if (type.equals("completion")) {

			cell = new PdfPCell();
			cell.setColspan(3);
			cell.setFixedHeight(200);
			cell.setPadding(20);
			cell.setBackgroundColor(new BaseColor(0, 128, 0));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			Paragraph p = new Paragraph("Certificate of Course Completion",
					FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 34, BaseColor.WHITE));
			cell.addElement(p);
			cell.addElement(new Paragraph(object.getUser().getUser_id(), FontFactory.getFont(FontFactory.TIMES_BOLD, 55, BaseColor.WHITE)));
			table.addCell(cell);

			cell = new PdfPCell();
			cell.setColspan(3);
			cell.setPadding(30);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Date  date = object.getCreatedDate();
			SimpleDateFormat  formatter =  new SimpleDateFormat("dd-MMM-yyyy");
			String strDate= formatter.format(date);  
			
			Paragraph p1 = new Paragraph(
					"This is to certify that he/she has successfully completed and scored "+percntge+"% the online course on Watershed Structures in "+strDate+". The Course administered by Department of Land Resources, Ministry of Rural Development, Government of India in association with My Bharat Portal. ",
					FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.GRAY));
			cell.addElement(p1);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setColspan(2);
			Chunk chunk1 = new Chunk("Progress: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.GRAY));
			Chunk chunk2 = new Chunk(progress, FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 18, new BaseColor(0, 128, 0)));
			Paragraph p2 = new Paragraph();
			p2.add(chunk1);
			p2.add(chunk2);
			p2.setIndentationLeft(20);
			Paragraph p3 = new Paragraph("________________________________________");
			p3.setIndentationLeft(20);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.addElement(p2);
			cell.addElement(p3);
			table.addCell(cell);
			
			cell = new PdfPCell();
			
			Paragraph p8 = new Paragraph("Sign:", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.GRAY));
			p8.setIndentationLeft(30);
			Paragraph p4 = new Paragraph("________________________________________");
			p4.setIndentationLeft(30);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.addElement(p8);
			cell.addElement(p4);
			table.addCell(cell);

		}else if(type.equals("participation")) {

			cell = new PdfPCell();
			cell.setColspan(3);
			cell.setFixedHeight(200);
			cell.setPadding(20);
			cell.setBackgroundColor(new BaseColor(160, 32, 240));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			Paragraph p = new Paragraph("Certificate of Participation",
					FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 34, BaseColor.WHITE));
			cell.addElement(p);
			cell.addElement(new Paragraph(object.getUser().getUser_id(), FontFactory.getFont(FontFactory.TIMES_BOLD, 55, BaseColor.WHITE)));
			table.addCell(cell);

			cell = new PdfPCell();
			cell.setColspan(3);
			cell.setPadding(30);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			Date  date = object.getCreatedDate();
			SimpleDateFormat  formatter =  new SimpleDateFormat("dd-MMM-yyyy");
			String strDate= formatter.format(date);    
			Paragraph p1 = new Paragraph(
					"This is to certify that he/she has participated in shramdan in "+strDate+" organised by the Department of Land Resources, Ministry of Rural Development, Government of India.",
					FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.GRAY));
			cell.addElement(p1);
			table.addCell(cell);
			
			cell = new PdfPCell();
			cell.setColspan(3);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			Paragraph p2 = new Paragraph("Sign:", FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.GRAY));
			p2.setIndentationLeft(400);
			cell.addElement(p2);
			table.addCell(cell);

			cell = new PdfPCell();
			cell.setColspan(2);
			Paragraph p3 = new Paragraph("________________________________________");
			p3.setIndentationLeft(20);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.addElement(p3);
			table.addCell(cell);

			cell = new PdfPCell();
			Paragraph p4 = new Paragraph("________________________________________");
			p4.setIndentationLeft(30);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.addElement(p4);
			table.addCell(cell);

		}
		
		DateTimeFormatter  formatter =  DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		String strDate= formatter.format(LocalDate.now());
		cell = new PdfPCell();
		cell.setColspan(2);
		cell.setBorder(Rectangle.BOTTOM);
		cell.setBorderColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		Paragraph p5 = new Paragraph("Date: "+strDate, FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.GRAY));
		p5.setIndentationLeft(20);
		cell.addElement(p5);
		table.addCell(cell);
		
//		cell = new PdfPCell();
//		cell.setBorder(Rectangle.BOTTOM);
//		cell.setBorderColor(BaseColor.WHITE);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		Paragraph p8 = new Paragraph(strDate, FontFactory.getFont(FontFactory.HELVETICA, 18));
//		p8.setIndentationLeft(20);
//		cell.addElement(p8);
//		table.addCell(cell);

		cell = new PdfPCell();
		cell.setBorder(Rectangle.BOTTOM);
		cell.setBorderColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		Paragraph p6 = new Paragraph("Designation:",
				FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.GRAY));
		p6.setIndentationLeft(30);
		cell.addElement(p6);
		Paragraph p7 = new Paragraph("Department Name:",
				FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.GRAY));
		p7.setIndentationLeft(30);
		cell.addElement(p7);
		table.addCell(cell);
		
		document.add(table);

		document.close();
		pdfWriter.close();

		response.setContentType("application/pdf");
		if(type.equals("completion")) 
			response.setHeader("Content-Disposition", "attachment; filename=Completion Certificate.pdf");
		else if(type.equals("participation"))
			response.setHeader("Content-Disposition", "attachment; filename=Participation Certificate.pdf");
		response.getOutputStream().write(byteArrayOutputStream.toByteArray());
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
