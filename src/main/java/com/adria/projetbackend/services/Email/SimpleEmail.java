package com.adria.projetbackend.services.Email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SimpleEmail implements EmailService {

    @Autowired
    private JavaMailSender emailSender;


    @Override
    public String sendSimpleMail(EmailDetails details) {

        try {
            SimpleMailMessage message = new SimpleMailMessage( );
            message.setFrom("noreply@betabank.com");
            message.setTo(details.getRecipient( ));
            message.setSubject(details.getSubject( ));
            message.setText(details.getMsgBody( ));
            emailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        return null;
    }
}