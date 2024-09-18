package com.wittedtech.mailSender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wittedtech.mailSender.dto.SendMailDto;
import com.wittedtech.mailSender.service.MailSenderService;
import com.wittedtech.mailSender.service.SpreadSheetProcessingService;

@Controller
@RequestMapping(value = "/send-mail",method = RequestMethod.POST)
public class MailSenderController {
    @Autowired
    private SpreadSheetProcessingService spreadsheetService;
    
    @Autowired
	private MailSenderService mailService;
    
    @GetMapping
    public String sendMail(@ModelAttribute SendMailDto dto){
    	boolean isSent = false;
    	List<SendMailDto> extractedMailDataListFromSheet = spreadsheetService.readXLSXFile(dto.getFile());
    	if(!extractedMailDataListFromSheet.isEmpty()) {
    		//isSent = mailService.mailSend(extractedMailDataListFromSheet);
    		if(isSent) {
    			return "success";
    		}else {
    			return "error";
    		}
    	}
    	return "error";
    }
}
