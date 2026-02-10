package com.springboot.training.controller;

import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.training.dto.CourseCompletionDetailsDto;
import com.springboot.training.dto.StateWiseNoofUserDetailDto;
import com.springboot.training.dto.StateWisePictureDetailDto;
import com.springboot.training.entity.MediaCoverageVideos;
import com.springboot.training.entity.YoutubeVideos;
import com.springboot.training.repository.StateWiseNoofUserDetailRepository;
import com.springboot.training.repository.StateWisePictureDetailRepository;
import com.springboot.training.service.MediaCoverageService;
import com.springboot.training.service.YoutubeVideoService;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
public class StateWiseNoofUserDetailController {
	
	@Autowired
	StateWiseNoofUserDetailRepository Repo;
	
	@Autowired
	private YoutubeVideoService youtubeVideoService;
	
	@Autowired
	MediaCoverageService mcser;
	
	@Autowired
	StateWisePictureDetailRepository Repop;

	
	@GetMapping("/getStateWiseNoofUserDetail") //, @PathVariable("id") int id
    public String getStateWiseNoofUserDetail(HttpSession session, Model model){
		int totuser=0;
		int totpart=0;
		int totpass=0;
		int totfail=0;
		
		try {
		
    /*	 String userId = (String) session.getAttribute("userId"); 
    	 if (userId == null) { 
    		 return "redirect:/login?error=session"; 
    		 }
    	 Integer regid =  Integer.parseInt(session.getAttribute("regid").toString());
     	 String userType = (String) session.getAttribute("userType");
   			*/
			
     	 List<StateWiseNoofUserDetailDto> user = Repo.getStateWiseNoofUserDetail();
     	 
     	 for (StateWiseNoofUserDetailDto usr : user) 
     	 {
     		totuser=totuser+usr.getStuser();
     		totpart=totpart+usr.getStparticipant();
     		totpass=totpass+usr.getStpassparticipant();
     		totfail=totfail+usr.getStfailparticipant();
            
     	 }
     	 model.addAttribute("user", user);
     	 model.addAttribute("totuser", totuser);
     	 model.addAttribute("totpart", totpart);
     	 model.addAttribute("totpass", totpass);
     	 model.addAttribute("totfail", totfail);
		
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("kdy");
		}
		
		return "stateWiseNoofUserDetail";
    }
	
	@GetMapping("/getMediaTypeDetails")
	public String getMediaTypeDetails(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "12") int size,
	        Model model) {

	    File folder = new File("/usr/local/apache-tomcat101-nic/webapps/filepath/media/");
	    File[] files = folder.listFiles();

	    List<String> imageNames = new ArrayList<>();

	    if (files != null) {
	        for (File file : files) {
	            if (file.isFile()) {
	                imageNames.add(file.getName());
	            }
	        }
	    }

	    // pagination 
	    int totalImages = imageNames.size();
	    int start = page * size;
	    int end = Math.min(start + size, totalImages);

	    List<String> pagedImages = new ArrayList<>();
	    if (start < totalImages) {
	        pagedImages = imageNames.subList(start, end);
	    }

	    int totalPages = (int) Math.ceil((double) totalImages / size);

	    model.addAttribute("images", pagedImages);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);

	    // videos (unchanged)
	   // List<MediaCoverageVideos> videos = mcser.getAllVideos();
	   // model.addAttribute("videos", videos);

	    return "mediatype";
	}

	
	
	
	@GetMapping("/videos")
	public String showVideos(Model model,
	                         @RequestParam(defaultValue = "0") int page,
	                         @RequestParam(defaultValue = "12") int size) {

	    Pageable pageable = PageRequest.of(page, size); // page index starts from 0
	    Page<YoutubeVideos> videoPage = youtubeVideoService.getVideos(pageable);

	    model.addAttribute("videos", videoPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", videoPage.getTotalPages());
	    System.out.println("totalPages "+ videoPage.getTotalPages());
	    return "youtubevideosview";
	}
	
	@GetMapping("/pictures")
	public String showallpictures(HttpSession session, Model model) {
	   
	    return "statepicturesview";
	}

		
	@GetMapping("/getStatepictures")
	public String showstatepictures(HttpSession session, @RequestParam("stateid") int state_cd, Model model) {
	    
	    File folder = new File("/usr/local/apache-tomcat101-nic/webapps/filepath/pictures/"+state_cd);
	    
	    
	    File[] files = folder.listFiles();

	    List<String> imageNames = new ArrayList<>();

	    if (files != null) {
	        for (File file : files) {
	            if (file.isFile()) {
	                imageNames.add(file.getName());
	            }
	        }
	    }
	    
	    model.addAttribute("images", imageNames);
	    model.addAttribute("stateid", state_cd);
	    
	    return "stategallery";
	}
	
	@GetMapping("/getDistWiseNoofUserDetail") 
    public String getDistWiseNoofUserDetail(HttpSession session, @RequestParam("state_id") int state_cd, @RequestParam("statename") String stname, Model model){
		int totuser=0;
		int totpart=0;
		int totpass=0;
		int totfail=0;
		
		try {
		
    /*	 String userId = (String) session.getAttribute("userId"); 
    	 if (userId == null) { 
    		 return "redirect:/login?error=session"; 
    		 }
    	 Integer regid =  Integer.parseInt(session.getAttribute("regid").toString());
     	 String userType = (String) session.getAttribute("userType");
   			*/
			
     	 List<StateWiseNoofUserDetailDto> user = Repo.getDistWiseNoofUserDetail(state_cd);
     	 
     	 for (StateWiseNoofUserDetailDto usr : user) 
     	 {
     		totuser=totuser+usr.getStuser();
     		totpart=totpart+usr.getStparticipant();
     		totpass=totpass+usr.getStpassparticipant();
     		totfail=totfail+usr.getStfailparticipant();
            
     	 }
     	 model.addAttribute("user", user);
     	 model.addAttribute("totuser", totuser);
     	 model.addAttribute("totpart", totpart);
     	 model.addAttribute("totpass", totpass);
     	 model.addAttribute("totfail", totfail);
     	 model.addAttribute("stname", stname);
     	 model.addAttribute("state_id", state_cd);
		
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("kdy");
		}
		
		return "distWiseNoofUserDetail";
    }
	
	@PostMapping("/ReportingStatusPDF")
    public String ReportingStatusPDF(HttpSession session, Model model, HttpServletResponse response){
		int totuser=0;
		int totpart=0;
		int totpass=0;
		int totfail=0;
		
		try {
		
   
		     	List<StateWiseNoofUserDetailDto> user = Repo.getStateWiseNoofUserDetail();
		     	Rectangle layout = new Rectangle(PageSize.A4.rotate());
				layout.setBackgroundColor(new BaseColor(255, 255, 255));
				Document document = new Document(layout, 25, 10, 10, 0);
				document.addTitle("status Report");
				document.addCreationDate();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter writer=PdfWriter.getInstance(document, baos);
				
				document.open(); 
		       
				Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
				Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
				Font bf8 = new Font(FontFamily.HELVETICA, 8);
				Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
				Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

				PdfPTable table = null;
				document.newPage();
				Paragraph paragraph3 = null; 
			//	Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
				
					paragraph3 = new Paragraph("State wise Quiz Participants Details", f3);
				
			//	paragraph2.setAlignment(Element.ALIGN_CENTER);
				paragraph3.setAlignment(Element.ALIGN_CENTER);
			//	paragraph2.setSpacingAfter(10);
				paragraph3.setSpacingAfter(10);
			//	CommonFunctions.addHeader(document);
			//	document.add(paragraph2);
				document.add(paragraph3);
				
					table = new PdfPTable(6);
					table.setWidths(new int[] { 2, 5, 5, 5, 5, 5});
					table.setWidthPercentage(70);
				
				//	table.setWidthPercentage(100);
					table.setSpacingBefore(0f);
					table.setSpacingAfter(0f);
				//	table.setHeaderRows(2);
					
				//	list=ser.getRoutePlanReportData(stCode, distCode, blkCode, gpCode);
					
				//	CommonFunctions.insertCellHeader(table,"State : "+ stName+"     District : "+distName+" Block : "+blkName+"  Gram Panchayat Name : "+gpkName, Element.ALIGN_LEFT, 9, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total Register User", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total User Attempted Quiz", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total Passed User", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total Failed User", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
					
					int stcd=0;
					int i=0;
					int k=1;
					int totNum = 0;
					if(user.size()!=0)
						for( i=0;i<user.size();i++) 
						{
							CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, user.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, String.valueOf(user.get(i).getStuser()), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, String.valueOf(user.get(i).getStparticipant()), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, String.valueOf(user.get(i).getStpassparticipant()), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, String.valueOf(user.get(i).getStfailparticipant()), Element.ALIGN_RIGHT, 1, 1, bf8);
							k=k+1;
							totuser=totuser+user.get(i).getStuser();
				     		totpart=totpart+user.get(i).getStparticipant();
				     		totpass=totpass+user.get(i).getStpassparticipant();
				     		totfail=totfail+user.get(i).getStfailparticipant();
						}
					
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totuser) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totpart) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totpass) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totfail) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					if(user.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
					
						
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksylms - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;state.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
		     	 
		     	 
		     	 for (StateWiseNoofUserDetailDto usr : user) 
		     	 {
		     		totuser=totuser+usr.getStuser();
		     		totpart=totpart+usr.getStparticipant();
		     		totpass=totpass+usr.getStpassparticipant();
		     		totfail=totfail+usr.getStfailparticipant();
		            
		     	 }
		     	 model.addAttribute("user", user);
		     	 model.addAttribute("totuser", totuser);
		     	 model.addAttribute("totpart", totpart);
		     	 model.addAttribute("totpass", totpass);
		     	 model.addAttribute("totfail", totfail);
		
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("kdy");
		}
		
		return null;
    }
	
	@PostMapping("/distWiseReportingStatusPDF") 
    public String distWiseReportingStatusPDF(HttpSession session, HttpServletResponse response, 
    		@RequestParam("stcode") int state_cd, @RequestParam("stname") String stname, Model model){
		int totuser=0;
		int totpart=0;
		int totpass=0;
		int totfail=0;
		
		try {
		
    /*	 String userId = (String) session.getAttribute("userId"); 
    	 if (userId == null) { 
    		 return "redirect:/login?error=session"; 
    		 }
    	 Integer regid =  Integer.parseInt(session.getAttribute("regid").toString());
     	 String userType = (String) session.getAttribute("userType");
   			*/
			
     	 List<StateWiseNoofUserDetailDto> user = Repo.getDistWiseNoofUserDetail(state_cd);
     	 
     	Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("status Report");
		document.addCreationDate();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer=PdfWriter.getInstance(document, baos);
		
		document.open(); 
       
		Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
		Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
		Font bf8 = new Font(FontFamily.HELVETICA, 8);
		Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
		Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

		PdfPTable table = null;
		document.newPage();
		Paragraph paragraph3 = null; 
	//	Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
		
			paragraph3 = new Paragraph("District wise Quiz Participants Details", f3);
		
	//	paragraph2.setAlignment(Element.ALIGN_CENTER);
		paragraph3.setAlignment(Element.ALIGN_CENTER);
	//	paragraph2.setSpacingAfter(10);
		paragraph3.setSpacingAfter(10);
	//	CommonFunctions.addHeader(document);
	//	document.add(paragraph2);
		document.add(paragraph3);
		
			table = new PdfPTable(6);
			table.setWidths(new int[] { 2, 5, 5, 5, 5, 5});
			table.setWidthPercentage(70);
		
		//	table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
		//	table.setHeaderRows(2);
			
		//	list=ser.getRoutePlanReportData(stCode, distCode, blkCode, gpCode);
			
			CommonFunctions.insertCellHeader(table,"State : "+ stname, Element.ALIGN_LEFT, 6, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Sl. No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Register User", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total User Attempted Quiz", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Passed User", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Failed User", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
			int stcd=0;
			int i=0;
			int k=1;
			int totNum = 0;
			if(user.size()!=0)
				for( i=0;i<user.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, user.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(user.get(i).getStuser()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(user.get(i).getStparticipant()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(user.get(i).getStpassparticipant()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(user.get(i).getStfailparticipant()), Element.ALIGN_RIGHT, 1, 1, bf8);
					k=k+1;
					totuser=totuser+user.get(i).getStuser();
		     		totpart=totpart+user.get(i).getStparticipant();
		     		totpass=totpass+user.get(i).getStpassparticipant();
		     		totfail=totfail+user.get(i).getStfailparticipant();
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totuser) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totpart) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totpass) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totfail) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			
			if(user.size()==0) 
			CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
			
				
	document.add(table);
	table = new PdfPTable(1);
	table.setWidthPercentage(70);
	table.setSpacingBefore(15f);
	table.setSpacingAfter(0f);
	CommonFunctions.insertCellPageHeader(table,"wdcpmksylms - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
	CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
	document.add(table);
	document.close();
	response.setContentType("application/pdf");
	response.setHeader("Expires", "0");
	response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	response.setHeader("Content-Disposition", "attachment;district.pdf");
	response.setHeader("Pragma", "public");
	response.setContentLength(baos.size());
	OutputStream os = response.getOutputStream();
	baos.writeTo(os);
	os.flush();
	os.close();
     	 
     
		
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("kdy");
		}
		
		return null;
    }
	
	@PostMapping("/ReportingStatusExcel")
    public String ReportingStatusExcel(HttpSession session, Model model, HttpServletResponse response)
	{
		int totuser=0;
		int totpart=0;
		int totpass=0;
		int totfail=0;
		
			List<StateWiseNoofUserDetailDto> user = Repo.getStateWiseNoofUserDetail();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("State wise Quiz Participants Details");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "State wise Quiz Participants Details";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("Sl.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Register User");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total User Attempted Quiz");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Total Passed User");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Total Failed User"); 
			cell.setCellStyle(style);
			
			/*
			 * Row rowhead1 = sheet.createRow(6); for(int i=0;i<6;i++) { cell
			 * =rowhead1.createCell(i); cell.setCellValue(i+1); cell.setCellStyle(style); }
			 */
	        
	        int sno = 1;
	        int rowno  = 6;
			int i=0;
	
			if(user.size()!=0)
				for( i=0;i<user.size();i++) 
				{
					Row row = sheet.createRow(rowno);
					
					row.createCell(0).setCellValue(sno);
	        		row.createCell(1).setCellValue(user.get(i).getSt_name());
	        		sno++;
	        		
	        		row.createCell(2).setCellValue(user.get(i).getStuser());
	        		row.createCell(3).setCellValue(user.get(i).getStparticipant());
	        		row.createCell(4).setCellValue(user.get(i).getStpassparticipant());
	        		row.createCell(5).setCellValue(user.get(i).getStfailparticipant());
	        		
					
					totuser=totuser+user.get(i).getStuser();
		     		totpart=totpart+user.get(i).getStparticipant();
		     		totpass=totpass+user.get(i).getStpassparticipant();
		     		totfail=totfail+user.get(i).getStfailparticipant();
		     		rowno++;
				}
	        
	        CellStyle style1 = workbook.createCellStyle();
	    	style1.setBorderTop(BorderStyle.THIN); 
	    	style1.setBorderBottom(BorderStyle.THIN);
	    	style1.setBorderLeft(BorderStyle.THIN);
	    	style1.setBorderRight(BorderStyle.THIN);
	    	style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	    	style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
	    	org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
	    	font1.setFontHeightInPoints((short) 12);
	    	font1.setBold(true);
//	    	font1.setColor(IndexedColors.WHITE.getIndex());
	    	style1.setFont(font1);
	    	
	    	mergedRegion = new CellRangeAddress(user.size()+6, user.size()+6,0,1);
			sheet.addMergedRegion(mergedRegion);
	    	
	    	Row row = sheet.createRow(user.size()+6);
	    	cell = row.createCell(0);
	    	cell.setCellValue("Grand Total");
	    	cell.setCellStyle(style1);
	    	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	    	cell = row.createCell(1);
	    	cell.setCellStyle(style1);
	    	
	    	cell = row.createCell(2);
	    	cell.setCellValue(totuser);
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(3);
	    	cell.setCellValue(totpart);
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(4);
	    	cell.setCellValue(totpass);
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(5);
	    	cell.setCellValue(totfail);
	    	cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, user.size(), 5);
	        String fileName = "attachment; filename=State wise Quiz Participants Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}
	
	@PostMapping("/distWiseReportingStatusExcel")
    public String distWiseReportingStatusExcel(HttpSession session, Model model, HttpServletResponse response,
    		@RequestParam("stcode") int state_cd, @RequestParam("stname") String stname)
	{
		int totuser=0;
		int totpart=0;
		int totpass=0;
		int totfail=0;
		
			List<StateWiseNoofUserDetailDto> user = Repo.getDistWiseNoofUserDetail(state_cd);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("District wise Quiz Participants Details");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "District wise Quiz Participants Details";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
			
			mergedRegion = new CellRangeAddress(5,5,0,5);
			sheet.addMergedRegion(mergedRegion);
			
			Row rowDetail = sheet.createRow(5);
			
			Cell cell = rowDetail.createCell(0);
			cell.setCellValue("State : "+ stname);
			cell.setCellStyle(style);
			
			for(int i=1;i<6;i++)
			{
				cell =rowDetail.createCell(i);
				cell.setCellStyle(style);
			}
			
			
	        
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("Sl.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Register User");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total User Attempted Quiz");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Total Passed User");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Total Failed User"); 
			cell.setCellStyle(style);
			
			/*
			 * Row rowhead1 = sheet.createRow(6); for(int i=0;i<6;i++) { cell
			 * =rowhead1.createCell(i); cell.setCellValue(i+1); cell.setCellStyle(style); }
			 */
	        
	        int sno = 1;
	        int rowno  = 7;
			int i=0;
			
			if(user.size()!=0)
				for( i=0;i<user.size();i++) 
				{
					Row row = sheet.createRow(rowno);
					
					row.createCell(0).setCellValue(sno);
	        		row.createCell(1).setCellValue(user.get(i).getSt_name());
	        		sno++;
	        		
	        		row.createCell(2).setCellValue(user.get(i).getStuser());
	        		row.createCell(3).setCellValue(user.get(i).getStparticipant());
	        		row.createCell(4).setCellValue(user.get(i).getStpassparticipant());
	        		row.createCell(5).setCellValue(user.get(i).getStfailparticipant());
	        		
					
					totuser=totuser+user.get(i).getStuser();
		     		totpart=totpart+user.get(i).getStparticipant();
		     		totpass=totpass+user.get(i).getStpassparticipant();
		     		totfail=totfail+user.get(i).getStfailparticipant();
		     		rowno++;
				}
	        
	        CellStyle style1 = workbook.createCellStyle();
	    	style1.setBorderTop(BorderStyle.THIN); 
	    	style1.setBorderBottom(BorderStyle.THIN);
	    	style1.setBorderLeft(BorderStyle.THIN);
	    	style1.setBorderRight(BorderStyle.THIN);
	    	style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	    	style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
	    	org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
	    	font1.setFontHeightInPoints((short) 12);
	    	font1.setBold(true);
//	    	font1.setColor(IndexedColors.WHITE.getIndex());
	    	style1.setFont(font1);
	    	
	    	mergedRegion = new CellRangeAddress(user.size()+7, user.size()+7,0,1);
			sheet.addMergedRegion(mergedRegion);
	    	
	    	Row row = sheet.createRow(user.size()+7);
	    	cell = row.createCell(0);
	    	cell.setCellValue("Grand Total");
	    	cell.setCellStyle(style1);
	    	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	    	cell = row.createCell(1);
	    	cell.setCellStyle(style1);
	    	
	    	cell = row.createCell(2);
	    	cell.setCellValue(totuser);
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(3);
	    	cell.setCellValue(totpart);
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(4);
	    	cell.setCellValue(totpass);
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(5);
	    	cell.setCellValue(totfail);
	    	cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, user.size(), 5);
	        String fileName = "attachment; filename=District wise Quiz Participants Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}
	

}
