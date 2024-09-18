package com.wittedtech.mailSender.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wittedtech.mailSender.dto.SendMailDto;
import com.wittedtech.mailSender.enums.EmailTemplates;
import com.wittedtech.mailSender.enums.PersonalDetails;

@Service
public class SpreadSheetProcessingService {
	
	int EMAIL_CELL_INDEX =-1;
	int NAME_CELL_INDEX = -1;
	int COMPANY_CELL_INDEX = -1;
	Map<String,List<String>> emailMap = new HashMap<>();
	private static Logger LOGGER = LoggerFactory.getLogger(SpreadSheetProcessingService.class);
	
	// Reading xlsx File 
    public List<SendMailDto> readXLSXFile(MultipartFile file) {
    	List<SendMailDto> sendMailDtoList = new ArrayList<>();
    	// File Path /Users/macbookair/Downloads/Company Wise HR Contacts _ HR Contacts.xlsx
    	
        try(InputStream fileStream = file.getInputStream();XSSFWorkbook workbook = new XSSFWorkbook(fileStream)){ // Using try-with-resource to create FileInputStream Object and // Creating Complete workbook object.
             
            XSSFSheet sheet = workbook.getSheetAt(0); // Getting First Sheet of Excel File
            Iterator<Row> rowIterator = sheet.iterator();// Row-Wise Iteration.
            // fetch email columns index
            if(rowIterator.hasNext()) {
            	Row headerRow = rowIterator.next();
            	for(Cell cell: headerRow) {
            		if(cell.getCellType() == CellType.STRING) {
            			String header = cell.getStringCellValue().trim().toLowerCase();
            			if("email".equals(header)) {
            				EMAIL_CELL_INDEX = cell.getColumnIndex();
            			}
            			if("name".equals(header)) {
            				NAME_CELL_INDEX = cell.getColumnIndex();
            			}
            			if("company".equals(header)) {
            				COMPANY_CELL_INDEX = cell.getColumnIndex();
            			}
            		}
            	}
            	if(EMAIL_CELL_INDEX == -1) {
            		LOGGER.warn("No Email Cell in the Sheet");
            		throw new RuntimeException("Email Column Not Found in the sheet."+sheet.getSheetName());
            	}
            	if(NAME_CELL_INDEX == -1) {
            		LOGGER.warn("No Name Cell in the Sheet"+sheet.getSheetName());
            	}
            	if(COMPANY_CELL_INDEX == -1) {
            		LOGGER.warn("No Company Name Cell in the Sheet"+sheet.getSheetName());
            	}
            }
            // Start sending rows for further processing and setting details in List of SendMailDto.
            
            while(rowIterator.hasNext()) {
            	Row row = rowIterator.next();
            	sendMailDtoList.add(processRowData(row));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sendMailDtoList;
    }
    // Process Each row and return List of SendMailDto object
    SendMailDto processRowData(Row row) {
    	SendMailDto dto = new SendMailDto();
    	Map<String, String> dynamicFields = new HashMap<>();
    	dto.setSenderMail(PersonalDetails.EMAIL_ADDRESS.getDetail());
    	dto.setRecepientMail(row.getCell(EMAIL_CELL_INDEX).getStringCellValue());
    	dynamicFields.put("HR Manager's Name", row.getCell(NAME_CELL_INDEX).getStringCellValue().split(" ")[0]);
    	dynamicFields.put("Company Name", row.getCell(COMPANY_CELL_INDEX).getStringCellValue());
    	dto.setMessage(EmailTemplates.JOB_APPLICATION.getProcessedBody(dynamicFields));
    	dto.setSubject(EmailTemplates.JOB_APPLICATION.getSubject());
    	
    	return dto; 
    }
    
    
}
