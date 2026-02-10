/*
 * 20181207 RBSN153 Implemented Saving of Rendered models in MongoDB
 * 20181130 RBSN151 Implemented support for uploading/rendering MUZ files
 * 20181109 RBSN149 Implemented VRML2.0 and Collada file loader in 3D viewer
 * 20181026 RBSN146 Implemented GLTF file loader
 * 20180816 RBSN131 Added support for ply file rendering in 3D Viewer
 * 20180504 RBSN110 Implemented ply loader in 3D Viewer  
 * 20180412 RBSN102 Fixed AppScan issues and encoded logger messages
 * 20180410 RBSS100 Added ESAPI validation while updating file details
 * 20180301 RBSN086 Deleted Cookies for cache control
 * 20180228 RBSD085 Added validator for contact us fields
 * 20180221 RBSS090 Updated UserId regular expression
 * 20180124 RBSS072 Implemented functionality to upload image of a 3D Model
 * 20180105 RBSN066 Fixed AppScan Path Traversal and Validation Required issues
 * 20171109 RBSN045 Implemented Logging using Apache log4j logging framework
 * 20170906 RBSS017	package to add common functionalities
 */
package com.springboot.training.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



@Controller("commonFunctions")
public class CommonFunctions {
	
	
		
		
		public static void insertCell(PdfPTable table, String text, int align, int colspan, int rowspan, Font font) 
		{
			if (text == null || text.equalsIgnoreCase(null)) 
			{
				text = "";
			}
			// create a new cell with the specified Text and Font
			PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
			// set the cell alignment
			cell.setHorizontalAlignment(align);
			// set the cell column span in case you want to merge two or more cells
			cell.setColspan(colspan);
			cell.setRowspan(rowspan);
			cell.setBackgroundColor(BaseColor.WHITE);
			// in case there is no text and you wan to create an empty row
			if (text.trim().equalsIgnoreCase("")) 
			{
				cell.setMinimumHeight(10f);
			}
			// add the call to the table
			table.addCell(cell);
		}
		
		public static void insertCell3(PdfPTable table, String text, int align, int colspan, int rowspan, Font font) {
			if (text == null || text.equalsIgnoreCase(null)) {
				text = "";
			}
			// create a new cell with the specified Text and Font
			PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
			// set the cell alignment
			cell.setHorizontalAlignment(align);
			// set the cell column span in case you want to merge two or more cells
			cell.setColspan(colspan);
			cell.setRowspan(rowspan);
			cell.setBackgroundColor(new BaseColor(240, 249, 232));
			// in case there is no text and you wan to create an empty row
			if (text.trim().equalsIgnoreCase("")) {
				cell.setMinimumHeight(10f);
			}
			// add the call to the table
			table.addCell(cell);
		}
		public static void insertCellHeader(PdfPTable table, String text, int align, int colspan, int rowspan, Font font) 
		{
			// create a new cell with the specified Text and Font
			PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
			// set the cell alignment
			cell.setHorizontalAlignment(align);
			// set the cell column span in case you want to merge two or more cells
			cell.setColspan(colspan);
			cell.setRowspan(rowspan);
			cell.setBackgroundColor(new BaseColor(11, 38, 61));
			// in case there is no text and you wan to create an empty row
			if (text.trim().equalsIgnoreCase("")) 
			{
				cell.setMinimumHeight(5f);
			}
			// add the call to the table
			table.addCell(cell);
		}

		public static void insertCellPageHeader(PdfPTable table, String text, int align, int colspan, int rowspan, Font font) 
		{
			// create a new cell with the specified Text and Font
			PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
			// set the cell alignment
			cell.setHorizontalAlignment(align);
			// set the cell column span in case you want to merge two or more cells
			cell.setColspan(colspan);
			cell.setRowspan(rowspan);
			cell.setBorder(0);
			// cell.setBackgroundColor(new BaseColor(0, 100, 33));
			// in case there is no text and you wan to create an empty row
			if (text.trim().equalsIgnoreCase("")) 
			{
				cell.setMinimumHeight(10f);
			}
			// add the call to the table
			table.addCell(cell);
		}
		
		 public static String dateToString(Date date, String format)
		 {
		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		        if(date==null)
		        {
		            date = new Date();
		        }
		        String stringAsDate = simpleDateFormat.format(date);
		        return stringAsDate;    
		 }
		 
		public String uploadFile(MultipartFile mfile,String filePath, String filename ) throws Exception
		{
			int mid=0, k=0;
			String ext="", file_name = "", concatinate = ".";
			try {
					float size = mfile.getSize();
					size = size / 1024;
					if(size/1024 > 20)
					{
						return "File size should be less than 2 MB";
					}
					byte[] bytes = mfile.getBytes();
					String s1 = new String(bytes);
					if (s1 != null && !s1.equals(""))
					s1 = s1.substring(0, 2);

					if (s1.startsWith("mz") || s1.startsWith("MZ") || s1.startsWith("4d5a")
						|| s1.startsWith("7f454c46") || s1.startsWith("7F454C46")
						|| s1.startsWith("cafebabe") || s1.startsWith("CAFEBABE") 
						|| s1.startsWith("feedface") || s1.startsWith("FEEDFACE")) 
					{
						return "Please select Only .doc,.docx,.ppt,.pptx,.jpg,.jpeg,.png,.pdf,.xls,.xlsx, file for upload!"+"\\n file content exe/malware or Others";
					}
							
					//filePath="D:\\CircularMessageAlert\\";
					String fileName = mfile.getOriginalFilename();
					
				//	MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
				 //   String mimeType = fileTypeMap.getContentType(fileName);
				    
				  //  application/octet-stream
					
					Pattern p = Pattern.compile("[.]");
				    Matcher matcher = p.matcher(fileName);
				    while(matcher.find()) {
				        k++;
				    }
				    if(k>1)
				    	return "Please upload a valid file";
					
					if(mfile.isEmpty() || fileName.isEmpty())
					{
						return "Please upload a valid file";
					}
					File file = new File(filePath);
					if (!file.exists()) 
					{
						file.mkdir();
					}
					mid = fileName.lastIndexOf(".");
					ext = fileName.substring(mid + 1, fileName.length()); 
					if ((ext.compareToIgnoreCase("") == 0) || (ext.compareToIgnoreCase("doc") == 0) 
						|| (ext.compareToIgnoreCase("docx") == 0)|| (ext.compareToIgnoreCase("ppt") == 0) 
						|| (ext.compareToIgnoreCase("pptx") == 0)|| (ext.compareToIgnoreCase("jpg") == 0)
						|| (ext.compareToIgnoreCase("jpeg") == 0)|| (ext.compareToIgnoreCase("pdf") == 0)
						|| (ext.compareToIgnoreCase("png") == 0)
						|| (ext.compareToIgnoreCase("xls") == 0) || (ext.compareToIgnoreCase("xlsx") == 0)) 
					{
						file_name = filename;
						file_name = file_name.concat(concatinate).concat(ext);
						if (!file_name.equals("")) 
						{
							File fileToCreate = new File(filePath, file_name);
							if (!fileToCreate.exists()) 
							{
								BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToCreate));
								outputStream.write(bytes);
								outputStream.close();
							}
						}
						return "success";
					}
					else {
							return "Upload correct file.";
					}	
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "error";
			}	
		}
		
		public String uploadOnlyPDFFile(MultipartFile mfile,String filePath, String filename ) throws Exception
		{
			int mid=0, k=0;
			String ext="", file_name = "", concatinate = ".";
			try {
					float size = mfile.getSize();
					size = size / 1024;
					if(size/1024 > 20)
					{
						return "File size should be less than 20 MB";
					}
					byte[] bytes = mfile.getBytes();
					String s1 = new String(bytes);
					if (s1 != null && !s1.equals(""))
					s1 = s1.substring(0, 2);

					if (s1.startsWith("mz") || s1.startsWith("MZ") || s1.startsWith("4d5a")
						|| s1.startsWith("7f454c46") || s1.startsWith("7F454C46")
						|| s1.startsWith("cafebabe") || s1.startsWith("CAFEBABE") 
						|| s1.startsWith("feedface") || s1.startsWith("FEEDFACE")) 
					{
						return "Please select Only .pdf file for upload!"+"\\n file content exe/malware or Others";
					}
							
					//filePath="D:\\CircularMessageAlert\\";
					String fileName = mfile.getOriginalFilename();
					
					Pattern p = Pattern.compile("[.]");
				    Matcher matcher = p.matcher(fileName);
				    while(matcher.find()) {
				        k++;
				    }
				    if(k>1)
				    	return "Please upload a valid file";
					
					
					if(mfile.isEmpty() || fileName.isEmpty())
					{
						return "Please upload a valid file";
					}
					File file = new File(filePath);
					if (!file.exists()) 
					{
						file.mkdir();
					}
					mid = fileName.lastIndexOf(".");
					ext = fileName.substring(mid + 1, fileName.length()); 
					if ((ext.compareToIgnoreCase("") == 0) || (ext.compareToIgnoreCase("pdf") == 0)) 
					{
						file_name = filename;
						file_name = file_name.concat(concatinate).concat(ext);
						if (!file_name.equals("")) 
						{
							File fileToCreate = new File(filePath, file_name);
							if (!fileToCreate.exists()) 
							{
								BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToCreate));
								outputStream.write(bytes);
								outputStream.close();
							}
						}
						return "success";
					}
					else {
							return "fail";
					}	
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "error";
			}	
		}
		
		 public static void addFooter(PdfWriter writer){
		        PdfPTable footer = new PdfPTable(3);
		        try {
		            // set defaults
		            footer.setWidths(new int[]{24, 2, 1});
		            footer.setTotalWidth(527);
		            footer.setLockedWidth(true);
		            footer.getDefaultCell().setFixedHeight(40);
		            footer.getDefaultCell().setBorder(Rectangle.TOP);
		            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

		            // add copyright
		            footer.addCell(new Phrase("\u00A9 Memorynotfound.com", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

		            // add current page count
		            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		            footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));
	//
//		            // add placeholder for total page count
//		            PdfPCell totalPageCount = new PdfPCell(total);
//		            totalPageCount.setBorder(Rectangle.TOP);
//		            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
//		            footer.addCell(totalPageCount);

		            // write page
		            PdfContentByte canvas = writer.getDirectContent();
		            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
		            footer.writeSelectedRows(0, -1, 34, 50, canvas);
		            canvas.endMarkedContentSequence();
		        } catch(DocumentException de) {
		            throw new ExceptionConverter(de);
		        }
		    }

		 public static Document addHeader(Document writer){
		        PdfPTable header = new PdfPTable(3);
		        try {
		            // set defaults
		          //  header.setWidths(new int[]{2, 24});
		          //  header.setTotalWidth(527);
		          //  header.setLockedWidth(true);
		        	header.setWidths(new float[] { 1, 10,1 });
		            header.getDefaultCell().setFixedHeight(20);
		            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
		           header.getDefaultCell().setBorderColor(BaseColor.WHITE);

		            // add image
		            
					
					 String emb="https://wdcpmksy.dolr.gov.in/resources/images/tiranga_national_emblem.png";
					 String g20l="https://wdcpmksy.dolr.gov.in/resources/images/g20-logo.png";
					 
		           
		          // String emb="http://localhost/resources/images/tiranga_national_emblem.png";
				  // String g20l="http://localhost/resources/images/g20-logo.png";

					
					Image img = Image.getInstance(emb);
					Image imgg = Image.getInstance(g20l);
		           /// Image logo = Image.getInstance(HeaderFooterPageEvent.class.getResource("/memorynotfound-logo.jpg"));
		            header.addCell(img);

		            // add text
		            PdfPCell text = new PdfPCell();
		            text.setPaddingBottom(13);
		           /// text.setPaddingLeft(10);
		            text.setVerticalAlignment(Element.ALIGN_CENTER);
		            text.setHorizontalAlignment(Element.ALIGN_CENTER);
		            text.setBorder(Rectangle.BOTTOM);
		            text.setBorderColor(BaseColor.WHITE);
		          //  Paragraph paragraph = new Paragraph(document.add(img)+" Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY) 2.0, "+document.add(imgg), f1);
				//	Paragraph paragraph2 = new Paragraph("Department of Land Resources,  Ministry of Rural Development ", f1);
					
		            text.addElement(new Phrase("Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY) 2.0 ", new Font(Font.FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC )));
		        //    text.addElement(new Phrase("Department of Land Resources,  Ministry of Rural Development", new Font(Font.FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC )));
		           // text.addElement(new Phrase("https://memorynotfound.com", new Font(Font.FontFamily.HELVETICA, 8)));
		            header.addCell(text);
		            header.addCell(imgg);
		            writer.add(header);
		            // write content
		            //header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
		        } catch(DocumentException de) {
		            throw new ExceptionConverter(de);
		        } catch (MalformedURLException e) {
		            throw new ExceptionConverter(e);
		        } catch (IOException e) {
		            throw new ExceptionConverter(e);
		        }
		        return writer;
		    }
		 
		 public static void getExcelHeader(Sheet sheet, CellRangeAddress mergedRegion, String rptName,
				 int colLength, String areaAmtVal, Workbook workbook) {
			 	CellStyle style = workbook.createCellStyle();
			 	org.apache.poi.ss.usermodel.Font font = workbook.createFont();
				font.setFontHeightInPoints((short) 12);
				font.setBold(true);
				style.setFont(font);
				
			    mergedRegion = new CellRangeAddress(1,1,0,colLength);
		        sheet.addMergedRegion(mergedRegion);
		        mergedRegion = new CellRangeAddress(2,2,0,colLength);
		        sheet.addMergedRegion(mergedRegion);
		        mergedRegion = new CellRangeAddress(3,3,0,colLength);
		        sheet.addMergedRegion(mergedRegion);
		        
		        mergedRegion = new CellRangeAddress(4,4,0,colLength);
		        sheet.addMergedRegion(mergedRegion);
		        
		        Row title = sheet.createRow(1);
		        Cell cell = title.createCell(0);
				cell.setCellValue("Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana, Learning Management System");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				

		        Row title1 = sheet.createRow(2);
		        cell = title1.createCell(0);
				cell.setCellValue("Department of Land Resources, Ministry of Rural Development");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				
				Row title2 = sheet.createRow(3);
		        cell = title2.createCell(0);
				cell.setCellValue(rptName);
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				
				if (!areaAmtVal.equals("")) {
					CellStyle style1 = CommonFunctions.getStyle(workbook);
					Row areaAmtValDetail = sheet.createRow(4);
					cell = areaAmtValDetail.createCell(0);
					cell.setCellValue(areaAmtVal);
					cell.setCellStyle(style1);
					CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
					for (int i = 1; i < colLength+1; i++) {
						areaAmtValDetail.createCell(i).setCellStyle(style1);
					}
				}
				else {
					Row areaAmtValDetail = sheet.createRow(4);
					cell = areaAmtValDetail.createCell(0);
					cell.setCellValue(areaAmtVal);
					CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
				}
		 }
		 
		 public static void getExcelFooter(Sheet sheet, CellRangeAddress mergedRegion, int listSize,
				 int colLength) {
			 
			 	mergedRegion = new CellRangeAddress(listSize+12,listSize+12,0,colLength); 
		        sheet.addMergedRegion(mergedRegion);
		        mergedRegion = new CellRangeAddress(listSize+13,listSize+13,0,colLength); 
		        sheet.addMergedRegion(mergedRegion);
		        
		        Row row = sheet.createRow(listSize+12);
		        Cell cell = row.createCell(0);
		        cell.setCellValue("wdcpmksylms - wdcpmksylms Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State");
		        
		        row = sheet.createRow(listSize+13);
		        cell = row.createCell(0);
		        cell.setCellValue("Govt./UT Administration and DoLR "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			 
		 }
		 
		 public static void downloadExcel(jakarta.servlet.http.HttpServletResponse response, Workbook workbook, String fileName) {
			 
			 try
		        {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Expires", "0");
					response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		            response.setHeader("Content-Disposition", fileName);
		            response.setHeader("Pragma", "public");
					OutputStream os = response.getOutputStream();
		            
		            workbook.write(os); // Write workbook to response.
		            os.flush();
					os.close();
		        } 
		        catch (Exception e) 
		        {
		            e.printStackTrace();
		        }
		 }
		 
		 public static CellStyle getStyle(Workbook workbook) {
			 CellStyle style = workbook.createCellStyle();
				style.setBorderTop(BorderStyle.THIN); 
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderLeft(BorderStyle.THIN);
				style.setBorderRight(BorderStyle.THIN);
				style.setFillForegroundColor(IndexedColors.GREEN.getIndex());  
		        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
				org.apache.poi.ss.usermodel.Font font = workbook.createFont();
				font.setFontHeightInPoints((short) 12);
				font.setBold(true);
				font.setColor(IndexedColors.WHITE.getIndex());
				style.setFont(font);
				return style;
		 }
		 
		 public String uploadFileforLMS(MultipartFile mfile, String filePath, Integer bcode, String act) throws Exception
		 {
				int mid=0, k=0;
				String ext="", file_name = "", concatinate = ".";
				try {
						float size = mfile.getSize();
						size = size / 1024;
						if(size/1024 > 20)
						{
							return "File size should be less than 2 MB";
						}
						byte[] bytes = mfile.getBytes();
						String s1 = new String(bytes);
						if (s1 != null && !s1.equals(""))
						s1 = s1.substring(0, 2);

						if (s1.startsWith("mz") || s1.startsWith("MZ") || s1.startsWith("4d5a")
							|| s1.startsWith("7f454c46") || s1.startsWith("7F454C46")
							|| s1.startsWith("cafebabe") || s1.startsWith("CAFEBABE") 
							|| s1.startsWith("feedface") || s1.startsWith("FEEDFACE")) 
						{
							return "Please select Only .jpg,.jpeg,.png, file for upload!"+"\\n file content exe/malware or Others";
						}
								
						//filePath="D:\\CircularMessageAlert\\";
						String fileName = mfile.getOriginalFilename();
						
					//	MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
					 //   String mimeType = fileTypeMap.getContentType(fileName);
					    
					  //  application/octet-stream
						
						Pattern p = Pattern.compile("[.]");
					    Matcher matcher = p.matcher(fileName);
					    while(matcher.find()) {
					        k++;
					    }
					    if(k>1)
					    	return "Please upload a valid file";
						
						if(mfile.isEmpty() || fileName.isEmpty())
						{
							return "fileisempty";
						}
						File file = new File(filePath);
						if (!file.exists()) 
						{
							file.mkdir();
						}
						mid = fileName.lastIndexOf(".");
						ext = fileName.substring(mid + 1, fileName.length()); 
						if ((ext.compareToIgnoreCase("") == 0) || (ext.compareToIgnoreCase("jpg") == 0)
							|| (ext.compareToIgnoreCase("jpeg") == 0)
							|| (ext.compareToIgnoreCase("png") == 0)) 
						{
							file_name = "I"+act+bcode+"_"+fileName;
						//	file_name = file_name.concat(concatinate);
							if (!file_name.equals("")) 
							{
								File fileToCreate = new File(filePath, file_name);
								if (!fileToCreate.exists()) 
								{
									BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToCreate));
									outputStream.write(bytes);
									outputStream.close();
								}
							}
							return "upload";
						}
						else {
								return "unupload";
						}	
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					return "unupload";
					
				}	
			}
		 
		 public String uploadFileforwatershedYatra(MultipartFile mfile, String filePath, Integer vcode, String act) throws Exception
		 {
				int mid=0, k=0;
				String ext="", file_name = "", concatinate = ".";
				try {
						float size = mfile.getSize();
						size = size / 1024;
						if(size/1024 > 20)
						{
							return "File size should be less than 2 MB";
						}
						byte[] bytes = mfile.getBytes();
						String s1 = new String(bytes);
						if (s1 != null && !s1.equals(""))
						s1 = s1.substring(0, 2);

						if (s1.startsWith("mz") || s1.startsWith("MZ") || s1.startsWith("4d5a")
							|| s1.startsWith("7f454c46") || s1.startsWith("7F454C46")
							|| s1.startsWith("cafebabe") || s1.startsWith("CAFEBABE") 
							|| s1.startsWith("feedface") || s1.startsWith("FEEDFACE")) 
						{
							return "Please select Only .jpg,.jpeg,.png, file for upload!"+"\\n file content exe/malware or Others";
						}
								
						//filePath="D:\\CircularMessageAlert\\";
						String fileName = mfile.getOriginalFilename();
						
					//	MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
					 //   String mimeType = fileTypeMap.getContentType(fileName);
					    
					  //  application/octet-stream
						
						Pattern p = Pattern.compile("[.]");
					    Matcher matcher = p.matcher(fileName);
					    while(matcher.find()) {
					        k++;
					    }
					    if(k>1)
					    	return "Please upload a valid file";
						
						if(mfile.isEmpty() || fileName.isEmpty())
						{
							return "fileisempty";
						}
						File file = new File(filePath);
						if (!file.exists()) 
						{
							file.mkdir();
						}
						mid = fileName.lastIndexOf(".");
						ext = fileName.substring(mid + 1, fileName.length()); 
						if ((ext.compareToIgnoreCase("jpg") == 0)
							|| (ext.compareToIgnoreCase("jpeg") == 0)
							|| (ext.compareToIgnoreCase("png") == 0)) 
						{
							file_name = "W"+act+vcode+"_"+fileName;
						//	file_name = file_name.concat(concatinate);
							if (!file_name.equals("")) 
							{
								File fileToCreate = new File(filePath, file_name);
								if (!fileToCreate.exists()) 
								{
									BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToCreate));
									outputStream.write(bytes);
									outputStream.close();
								}
							}
							return "upload";
						}
						else {
								return "unupload";
						}	
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					return "unupload";
					
				}	
			}
		
}
