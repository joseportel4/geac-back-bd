package br.com.geac.backend.Infrastructure.Scheduler;

import br.com.geac.backend.Aplication.Services.EventService;
import br.com.geac.backend.Aplication.Services.NotificationService;
import br.com.geac.backend.Aplication.Services.RegistrationService;
import br.com.geac.backend.Domain.Entities.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventAlertScheduler {

    private final EventService eventService;
    private final RegistrationService registrationService;
    private final NotificationService notificationService;

    @Scheduled(cron = "30 * * * * *") //TODO: TESTE PRA VE SE TA FUNCIONANDO M UDAR DPS
    public void checkCloseEvents() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventTimeCheck = now.plusHours(1); //TODO: colocar tag -> is notified e refatorar

        List<Event> closeEvents = eventService.getEventsBetween(now, eventTimeCheck);

        for (Event event : closeEvents) {

            List<String> userEmails = new ArrayList<>();
            var registrationsByEvent = registrationService.getRegistrationsByEvent(event.getId()); //TODO: mudar pra entity
            registrationsByEvent.forEach(reg -> {
                userEmails.add(reg.userEmail());
            });

            if (!userEmails.isEmpty()) {
                notificationService.notifyAll(userEmails,event);
            }

        }
    }
}
