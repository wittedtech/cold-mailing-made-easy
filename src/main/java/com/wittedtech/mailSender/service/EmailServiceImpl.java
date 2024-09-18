package com.wittedtech.mailSender.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.wittedtech.mailSender.dto.SendMailDto;
import com.wittedtech.mailSender.enums.DocumentsPath;
import com.wittedtech.mailSender.enums.PersonalDetails;
import com.wittedtech.mailSender.interfaces.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Override
	public boolean sendEmailWithoutAttachment(SendMailDto mailingData) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			//Mail Details
			mimeMessageHelper.setFrom(PersonalDetails.EMAIL_ADDRESS.getDetail());
			mimeMessageHelper.setTo(mailingData.getRecepientMail());
			mimeMessageHelper.setSubject(mailingData.getSubject());
			mimeMessageHelper.setText(mailingData.getMessage(),true);
			
			// send mail
			javaMailSender.send(mimeMessage);
			LOGGER.info("Mail Send Succefully To: {}",mailingData.getRecepientMail());
			return true;
		}catch(Exception e) {
			LOGGER.warn("Error while Sending Mail To: {}", mailingData.getRecepientMail());
			return false;
		}
	}

	@Override
	public boolean sendEmailWithAttachment(SendMailDto mailingData) {
		// Creating multipurpose internet mail extensions(MIME) message
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			//setting multipart as true for attachment to be send
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(PersonalDetails.EMAIL_ADDRESS.getDetail());
			mimeMessageHelper.setTo(mailingData.getRecepientMail());
			mimeMessageHelper.setSubject(mailingData.getSubject());
			mimeMessageHelper.setText(mailingData.getMessage(),true);
			
			// Load the file as a ClassPath resource
	        ClassPathResource fileResource = new ClassPathResource(DocumentsPath.RESUME_HARSHIT_SINGH_JAVA_2_YEAR.getFilePath());

	        // Attach the file
	        mimeMessageHelper.addAttachment(fileResource.getFilename(), fileResource);
			// Sending mail
			javaMailSender.send(mimeMessage);
			LOGGER.info("Mail Send Successfulle with File: {} Attached to: {}",fileResource.getFilename(),mailingData.getRecepientMail());
			return true;
		}catch(Exception e) {
			LOGGER.warn("Error sending file with attachment to: {}",mailingData.getRecepientMail());
			LOGGER.error("Exception Thrown: ", e.getMessage());
			return false;
		}
	}

}
