package com.wittedtech.mailSender.dto;


import org.springframework.web.multipart.MultipartFile;



public class SendMailDto {
    private String senderMail;
    private String recepientMail;
    private String subject;
    private String message;
    private String attachment;
    private MultipartFile file;

    public SendMailDto(){}

    public SendMailDto(String senderMail, String recepientMail, String subject, String message, String attachment, MultipartFile file) {
        this.senderMail = senderMail;
        this.recepientMail = recepientMail;
        this.subject = subject;
        this.message = message;
        this.attachment = attachment;
        this.file = file;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getRecepientMail() {
        return recepientMail;
    }

    public void setRecepientMail(String recepientMail) {
        this.recepientMail = recepientMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "SendMailDto{" +
                "senderMail='" + senderMail + '\'' +
                ", recepientMail='" + recepientMail + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", attachment='" + attachment + '\'' +
                ", file=" + file +
                '}';
    }
}
