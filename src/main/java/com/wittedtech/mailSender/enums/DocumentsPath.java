package com.wittedtech.mailSender.enums;

public enum DocumentsPath {
	RESUME_HARSHIT_SINGH_JAVA_2_YEAR("Resume/Harshit_Singh_Java_2_Year.pdf");
	
	private String filePath;
	
	DocumentsPath(String filePath){
	this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
}
