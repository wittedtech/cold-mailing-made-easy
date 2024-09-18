package com.wittedtech.mailSender.enums;

import java.util.Map;

public enum EmailTemplates {
	TEST_TEMPLATE("TEST TEMPLATE","<html><body><h1>Hello,</h1><p>This is a test email.</p></body></html>"),

	
	JOB_APPLICATION("Job Application for Java Backend Developer",
            "Dear [HR Manager's Name],<br><br>" +
            "I hope you are doing well.<br><br>" +
            "I am writing to express my interest in any Java Backend Developer or Spring Boot Developer positions available at [Company Name]. " +
            "With over 2 years of experience in building scalable backend solutions, particularly with Java, Spring Boot, and distributed systems, I am eager to contribute my expertise to your team.<br><br>" +
            "In my current role at Velocis System Private Ltd, I have successfully:<br><br>" +
            "- Engineered scalable backend APIs, resulting in a 40% improvement in performance.<br>" +
            "- Integrated distributed technologies like Redis, Apache Tomcat, and optimized microservices architecture.<br>" +
            "- Enhanced system security by implementing robust authentication mechanisms.<br><br>" +
            "Please find my resume attached for your consideration. I would welcome the opportunity to discuss how my skills align with your team's needs. " +
            "I am available for an interview at your convenience and can be reached at [Your Phone Number] or [Your Email Address].<br><br>" +
            "Best regards,<br>" +
            "Harshit Singh<br>" +
            "<a href=\"[Your LinkedIn Profile]\">LinkedIn</a> || <a href=\"[Your GitHub Profile]\">GitHub</a><br>" +
            "[Your Phone Number]"),

    FOLLOW_UP("Follow-up on Java Backend Developer Application",
            "Dear [HR Manager's Name],<br><br>" +
            "I hope this message finds you well. I am following up regarding the Java Backend Developer position at [Company Name], which I applied for last week.<br><br>" +
            "I believe my experience in distributed systems and scalable backend solutions aligns well with the position requirements.<br><br>" +
            "Please feel free to reach out if there are any further updates or additional information needed from my side.<br><br>" +
            "Best regards,<br>" +
            "Harshit Singh<br>" +
            "<a href=\"[Your LinkedIn Profile]\">LinkedIn</a> || <a href=\"[Your GitHub Profile]\">GitHub</a><br>" +
            "[Your Phone Number]");

    private final String subject;
    private final String body;

    // Constructor now accepts both subject and body
    EmailTemplates(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    // Get the subject for the email template
    public String getSubject() {
        return subject;
    }

    // Method to replace placeholders with actual values in the email body
    public String getProcessedBody(Map<String, String> dynamicFields) {
        String processedBody = this.body;

        // Replace dynamic placeholders from dynamicFields
        for (Map.Entry<String, String> entry : dynamicFields.entrySet()) {
            processedBody = processedBody.replace("[" + entry.getKey() + "]", entry.getValue());
        }

        // Replace placeholders with personal details from PersonalDetails enum
        processedBody = processedBody.replace("[Your LinkedIn Profile]", PersonalDetails.LINKEDIN_PROFILE.getDetail());
        processedBody = processedBody.replace("[Your GitHub Profile]", PersonalDetails.GITHUB_PROFILE.getDetail());
        processedBody = processedBody.replace("[Your Phone Number]", PersonalDetails.PHONE_NUMBER.getDetail());
        processedBody = processedBody.replace("[Your Email Address]", PersonalDetails.EMAIL_ADDRESS.getDetail());

        return processedBody;
	}
}
