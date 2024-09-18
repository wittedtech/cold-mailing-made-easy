package com.wittedtech.mailSender.enums;

public enum PersonalDetails {
	LINKEDIN_PROFILE("https://www.linkedin.com/in/harshitsingh-wittedtech/"),
    GITHUB_PROFILE("https://github.com/wittedtech"),
    PORTFOLIO(""),
    PHONE_NUMBER("7307490600"),
    EMAIL_ADDRESS("sde.harshit@gmail.com");

    private final String detail;

    PersonalDetails(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
