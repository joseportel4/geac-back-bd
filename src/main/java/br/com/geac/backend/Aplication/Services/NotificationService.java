package br.com.geac.backend.Aplication.Services;

import br.com.geac.backend.Domain.Entities.Event;
import br.com.geac.backend.Infrastructure.Repositories.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private RegistrationRepository registrationRepository;

    public void notifyAll(List<String> emails, Event event){
        for(String email : emails){

            emailService.sendAlert(email,event);
            //mudar tabela de inscricao
        }
    }
}
