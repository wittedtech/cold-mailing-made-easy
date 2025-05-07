package com.wittedtech.mailSender.enums;

import java.util.Map;

public enum EmailTemplates {
    TEST_TEMPLATE("TEST TEMPLATE", "<html><body><h1>Hello,</h1><p>This is a test email.</p></body></html>"),

    JOB_APPLICATION("Java Spring Boot Developer for [Company Name|Your Team]’s Next Project?",
            "<html><body>" +
            "Dear [HR Manager's Name|Hiring Manager],<br><br>" +
            "I’m Harshit Singh, a Software Engineer with almost 3 years of experience building high-performance backend systems using Java, Spring Boot, and microservices. " +
            "I’m excited to explore Backend Developer roles at [Company Name|Your Team] and contribute to your success. My resume is attached for your review.<br><br>" +
            "<strong>Why I’m a fit:</strong><br>" +
            "<ul>" +
            "<li><strong>Performance</strong>: Built RESTful APIs and microservices, improving system speed by 40%.</li>" +
            "<li><strong>Reliability</strong>: Cut production downtime by 30% with Level-3 support and ELK Stack.</li>" +
            "<li><strong>Efficiency</strong>: Used Redis caching to slash response times by 60%.</li>" +
            "<li><strong>Security</strong>: Reduced vulnerabilities by 25% through enhanced authentication.</li>" +
            "</ul><br>" +
            "I’ve delivered projects like the Velocis Digital Framework (Spring Boot, Kafka) and a microservices-based logistics system (Redis, Oracle DB). " +
            "Check my work on <a href=\"[Your GitHub Profile]\">GitHub</a> or <a href=\"[Your LinkedIn Profile]\">LinkedIn</a>.<br><br>" +
            "Can we schedule a 10-minute call at your earlliest convenience to discuss how I can contribute to [Company Name|Your Team]? " +
            "Reply or reach me at [Your Phone Number]. I’m eager to connect!<br><br>" +
            "Best regards,<br>" +
            "Harshit Singh<br>" +
            "<a href=\"mailto:[Your Email Address]\">[Your Email Address]</a><br>" +
            "<a href=\"[Your LinkedIn Profile]\">LinkedIn</a> || <a href=\"[Your GitHub Profile]\">GitHub</a><br>" +
            "<i>P.S. My GitHub has live Spring Boot projects—take a look!</i>" +
            "</body></html>"),

    FOLLOW_UP("Follow-up on Java Backend Developer Application",
            "<html><body>" +
            "Dear [HR Manager's Name|Hiring Manager],<br><br>" +
            "I hope this message finds you well. I am following up regarding the Java Backend Developer position at [Company Name|Your Team], which I applied for last week.<br><br>" +
            "I believe my experience in distributed systems and scalable backend solutions aligns well with the position requirements.<br><br>" +
            "Please feel free to reach out if there are any further updates or additional information needed from my side.<br><br>" +
            "Best regards,<br>" +
            "Harshit Singh<br>" +
            "<a href=\"[Your LinkedIn Profile]\">LinkedIn</a> || <a href=\"[Your GitHub Profile]\">GitHub</a><br>" +
            "[Your Phone Number]" +
            "</body></html>");

    private final String subject;
    private final String body;

    EmailTemplates(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject(Map<String, String> dynamicFields) {
    	String processedSubject = this.subject;
    	// Define placeholders with their fallbacks
        Map<String, String> placeholders = Map.of(
            "HR Manager's Name", "Hiring Manager",
            "Company Name", "Your Team"
        );
    	// Replace placeholders in subject and body
        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
        	String key = placeholder.getKey();
            String fallback = placeholder.getValue();
            String value = dynamicFields.getOrDefault(key, fallback);
            value = (value != null && !value.trim().isEmpty()) ? value : fallback;

            // Replace in subject
            processedSubject = processedSubject.replace("[" + key + "|"+fallback+"]", value);
            processedSubject = processedSubject.replace("[" + key + "]", value);

        }
        return processedSubject;
    }

    public String getProcessedBody(Map<String, String> dynamicFields) {
        String processedSubject = this.subject;
        String processedBody = this.body;

        // Define placeholders with their fallbacks
        Map<String, String> placeholders = Map.of(
            "HR Manager's Name", "Hiring Manager",
            "Company Name", "Your Team"
        );

        // Replace placeholders in subject and body
        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            String key = placeholder.getKey();
            String fallback = placeholder.getValue();
            String value = dynamicFields.getOrDefault(key, fallback);
            value = (value != null && !value.trim().isEmpty()) ? value : fallback;

            // Replace in subject
            processedSubject = processedSubject.replace("[" + key + "|"+fallback+"]", value);
            processedSubject = processedSubject.replace("[" + key + "]", value);

            // Replace in body
            processedBody = processedBody.replace("[" + key + "|"+fallback+"]", value);
            processedBody = processedBody.replace("[" + key + "]", value);
        }

        // Replace personal details
        processedBody = processedBody.replace("[Your LinkedIn Profile]", PersonalDetails.LINKEDIN_PROFILE.getDetail());
        processedBody = processedBody.replace("[Your GitHub Profile]", PersonalDetails.GITHUB_PROFILE.getDetail());
        processedBody = processedBody.replace("[Your Phone Number]", PersonalDetails.PHONE_NUMBER.getDetail());
        processedBody = processedBody.replace("[Your Email Address]", PersonalDetails.EMAIL_ADDRESS.getDetail());

        return processedBody;
    }
}