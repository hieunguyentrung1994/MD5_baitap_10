package ra.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(String to, String subject, String text) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
		
		message.setSubject(subject);
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(to);
		helper.setText(text, true);
		javaMailSender.send(message);
	}
	
}
