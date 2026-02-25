package br.com.geac.backend.Aplication.Services;

import br.com.geac.backend.Domain.Entities.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String remetente;
    private final JavaMailSender mailSender;

    public void sendAlert(String email,Event event){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(remetente);
        message.setTo(email);
        message.setSubject("ALERT" + event.getTitle());
        message.setText(buildMessage(event));
        mailSender.send(message);
    }

    private String buildMessage(Event event){
        return String.format(
                "Olá cidadao" + "o evento que tu ta inscrito '%s' + %s/n jaja acontece, Data: %s/n, Local: %s/n",
                event.getTitle(), event.getDescription(),event.getStartTime(), event.getLocation()
        );
    }
}
