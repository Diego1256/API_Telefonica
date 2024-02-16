package br.com.cotiinformatica.components;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailComponent {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	String userName;
	
	public void enviaMensagem(EmailModelComponent model) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(userName);
		helper.setTo(model.getEmailDest());
		helper.setSubject(model.getAssunto());
		helper.setText(model.getTexto(),model.isHtml());
		
		javaMailSender.send(message);
	}
}
