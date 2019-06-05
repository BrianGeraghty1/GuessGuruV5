package io.guessguru.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String email, double amount, String name) throws MailException {

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("GuessGuruInfo@gmail.com");
		mail.setSubject("You have won!");
		mail.setText("Congratulations! You have won €" + amount + " in the tournament " + name
				+ " . The amount has been credited to your GuessGuru account.");
		javaMailSender.send(mail);
	}
}
