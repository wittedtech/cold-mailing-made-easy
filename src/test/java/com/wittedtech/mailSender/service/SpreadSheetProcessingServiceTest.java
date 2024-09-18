package com.wittedtech.mailSender.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import com.wittedtech.mailSender.dto.SendMailDto;
import com.wittedtech.mailSender.enums.PersonalDetails;

public class SpreadSheetProcessingServiceTest {
	@Mock
	private MailSenderService mailSenderService;
	
	@InjectMocks
	private SpreadSheetProcessingService spreadSheetProcessingService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	// Creating mock Excel Sheet
	private byte[] createMockExcelData() throws IOException{
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Contacts");
		
		//Header Rows
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Company");
		headerRow.createCell(2).setCellValue("email");
		headerRow.createCell(3).setCellValue("");
		
		// Data Rows
		Row dataRows1 = sheet.createRow(1);
		dataRows1.createCell(0).setCellValue("Harshit");
		dataRows1.createCell(1).setCellValue("xyz");
		dataRows1.createCell(2).setCellValue("ceh.harshit@gmail.com");
		dataRows1.createCell(3).setCellValue("");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		workbook.close();
		
		return new ByteArrayInputStream(out.toByteArray()).readAllBytes();
	}
	
	// Creating Mock Excel sheet without email
	// Helper method to create mock Excel data missing the "email" column
    private byte[] createMockExcelDataMissingEmail() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contacts");

        // Header Row without "email"
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("name");
        headerRow.createCell(1).setCellValue("company");

        // Data Row
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("John Doe");
        dataRow.createCell(1).setCellValue("TechCorp");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray()).readAllBytes();
    }
	
	@Test
	void testReadXLSXFile_successfulProcessing() throws IOException {
		//Arrange: Create a mock excel file with Header and Data
		MockMultipartFile mockFile = new MockMultipartFile("file", "test.xlsx", null, createMockExcelData());
		
		//Act: Call the method of spreadSheetService to be tested
		spreadSheetProcessingService.readXLSXFile(mockFile);
		//Assert: Verify that mailsend is called with the correct List
		ArgumentCaptor<List<SendMailDto>> captor = ArgumentCaptor.forClass(List.class);
		verify(mailSenderService, times(1)).mailSend(captor.capture());
		
		List<SendMailDto> capturedList = captor.getValue();
		assertEquals(1, capturedList.size());
		
		// Verify first Mocked DTO Object
        SendMailDto dto1 = capturedList.get(0);
        assertEquals(PersonalDetails.EMAIL_ADDRESS.getDetail(), dto1.getSenderMail());
        assertEquals("ceh.harshit@gmail.com", dto1.getRecepientMail());
        assertEquals("Job Application for Java Backend Developer", dto1.getSubject());
        assertTrue(dto1.getMessage().contains("John Doe"));
        assertTrue(dto1.getMessage().contains("TechCorp"));
        
	}
	
	@Test
    void testReadXLSXFile_missingEmailColumn() throws Exception {
        // Arrange: Create a mock Excel file without the "email" header
        MockMultipartFile mockFile = new MockMultipartFile("file", "test_missing_email.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                createMockExcelDataMissingEmail());

        // Act & Assert: Expect a RuntimeException due to missing email column
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            spreadSheetProcessingService.readXLSXFile(mockFile);
        });

        assertTrue(exception.getMessage().contains("Email Column Not Found"));
    }
	
	@Test
	void testProcessRowData() {
		// Arrange: Create a mock row
		Row mockRow = mock(Row.class);
		Cell mockEmailCell = mock(Cell.class);
		Cell mockNameCell = mock(Cell.class);
		Cell mockCompanyCell = mock(Cell.class);
		
		spreadSheetProcessingService.EMAIL_CELL_INDEX = 2;
		spreadSheetProcessingService.NAME_CELL_INDEX = 3;
		spreadSheetProcessingService.COMPANY_CELL_INDEX = 4;
		
		when(mockRow.getCell(2)).thenReturn(mockEmailCell); // EMAIL_CELL_INDEX = 2
        when(mockRow.getCell(3)).thenReturn(mockNameCell);  // NAME_CELL_INDEX = 3
        when(mockRow.getCell(4)).thenReturn(mockCompanyCell); // COMPANY_CELL_INDEX = 4
        
        when(mockEmailCell.getCellType()).thenReturn(CellType.STRING);
        when(mockEmailCell.getStringCellValue()).thenReturn("hr@example.com");

        when(mockNameCell.getCellType()).thenReturn(CellType.STRING);
        when(mockNameCell.getStringCellValue()).thenReturn("John Doe");

        when(mockCompanyCell.getCellType()).thenReturn(CellType.STRING);
        when(mockCompanyCell.getStringCellValue()).thenReturn("TechCorp");
        
        // Act: Call processRowData
        SendMailDto dto = spreadSheetProcessingService.processRowData(mockRow);

        // Assert: Verify the DTO fields
        assertEquals(PersonalDetails.EMAIL_ADDRESS.getDetail(), dto.getSenderMail());
        assertEquals("hr@example.com", dto.getRecepientMail());
        assertEquals("Job Application for Java Backend Developer", dto.getSubject());
        assertTrue(dto.getMessage().contains("John"));
        assertTrue(dto.getMessage().contains("TechCorp"));
        assertTrue(dto.getMessage().contains(PersonalDetails.PHONE_NUMBER.getDetail()));
		
	}

}
