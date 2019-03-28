package com.dawid.ems.mailer;

public interface EmailSender {

    void sendEmail(String to, String subject, String content);

}
