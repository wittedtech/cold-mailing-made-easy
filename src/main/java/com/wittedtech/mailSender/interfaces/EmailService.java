package com.wittedtech.mailSender.interfaces;

import com.wittedtech.mailSender.dto.SendMailDto;

public interface EmailService {
	boolean sendEmailWithoutAttachment(SendMailDto mailingData);
	
	boolean sendEmailWithAttachment(SendMailDto mailingData);
}
