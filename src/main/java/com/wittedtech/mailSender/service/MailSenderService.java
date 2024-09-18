package com.wittedtech.mailSender.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wittedtech.mailSender.dto.SendMailDto;

@Service
public class MailSenderService {
	@Autowired
	private EmailServiceImpl emailService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderService.class);
	// Send Mail
    public boolean mailSend(List<SendMailDto> mailDtoList) {
    	boolean isSent = false;
    	for(SendMailDto mailindData: mailDtoList) {
    		 isSent = emailService.sendEmailWithAttachment(mailindData);
//    		boolean isSent = emailService.sendEmailWithoutAttachment(mailindData);
    	}
    	return isSent;
    }
}
