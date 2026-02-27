package br.com.geac.backend.Aplication.Services;

import br.com.geac.backend.Aplication.DTOs.Reponse.PendingRequestResponseDTO;
import br.com.geac.backend.Aplication.DTOs.Request.CreateOrganizerRequestDTO;
import br.com.geac.backend.Domain.Entities.*;
import br.com.geac.backend.Domain.Enums.RequestStatus;
import br.com.geac.backend.Domain.Enums.Role;
import br.com.geac.backend.Domain.Exceptions.ConflictException;
import br.com.geac.backend.Infrastructure.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizerRequestService {

    private final OrganizerRequestRepository requestRepository;
    private final OrganizerMemberRepository memberRepository;
    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final EventRepository eventRepository; //TODO testar notificação
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public List<PendingRequestResponseDTO> getPendingRequests() {
        return requestRepository.findAllByStatus(RequestStatus.PENDING).stream()
                .map(req -> new PendingRequestResponseDTO(
                        req.getId(),
                        req.getUser().getName(),
                        req.getUser().getEmail(),
                        req.getOrganizer().getName(),
                        req.getJustification(),
                        req.getCreatedAt()
                )).toList();
    }

    @Transactional
    public void approveRequest(Integer requestId) {
        OrganizerRequest request = findRequestOrThrow(requestId);
        var user = userRepository.findById(request.getUser().getId()).orElseThrow();

        request.setStatus(RequestStatus.APPROVED);
        request.setResolvedAt(LocalDateTime.now());
        requestRepository.save(request);

        // Se foi aprovado, adiciona como membro da organização (se ainda não for)
        boolean alreadyMember = memberRepository.existsByOrganizerIdAndUserId(
                request.getOrganizer().getId(), request.getUser().getId());

        if (!alreadyMember) {
            OrganizerMember newMember = new OrganizerMember();
            newMember.setOrganizer(request.getOrganizer());
            newMember.setUser(request.getUser());
            memberRepository.save(newMember);
            if (user.getRole().equals(Role.STUDENT)) {
                user.setRole(Role.ORGANIZER);
                userRepository.save(user);
            }

            Notification notif = new Notification();
            notif.setUser(user);
            notif.setTitle("Solicitação Aprovada!");
            notif.setMessage("Parabéns! Sua solicitação para participar da organização '" + request.getOrganizer().getName() + "' foi aceita. Você agora é um organizador.");
            notif.setType("CANCEL");
            notif.setRead(false);
            notif.setCreatedAt(LocalDateTime.now());
            notif.setEvent(eventRepository.findAll().getFirst());
            notificationService.notify(notif);

        }
    }

    @Transactional
    public void rejectRequest(Integer requestId) {
        OrganizerRequest request = findRequestOrThrow(requestId);

        request.setStatus(RequestStatus.REJECTED);
        request.setResolvedAt(LocalDateTime.now());
        requestRepository.save(request);
    }

    @Transactional
    public void createRequest(CreateOrganizerRequestDTO dto) {
        if (requestRepository.existsByUserIdAndOrganizerIdAndStatus(dto.userId(), dto.organizerId(), RequestStatus.PENDING)) {
            throw new ConflictException("Já existe uma solicitação pendente deste usuário para esta organização.");
        }

        if (memberRepository.existsByOrganizerIdAndUserId(dto.organizerId(), dto.userId())) {
            throw new ConflictException("Usuário já é membro desta organização.");
        }

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        Organizer organizer = organizerRepository.findById(dto.organizerId())
                .orElseThrow(() -> new RuntimeException("Organização não encontrada."));

        OrganizerRequest request = new OrganizerRequest();
        request.setUser(user);
        request.setOrganizer(organizer);
        request.setJustification(dto.justification());
        request.setStatus(RequestStatus.PENDING);

        requestRepository.save(request);

        List<User> admins = userRepository.findAllByRole(Role.ADMIN);
        for (User admin : admins) {
            Notification adminNotif = new Notification();
            adminNotif.setUser(admin);
            adminNotif.setTitle("Nova Solicitação de Organização");
            adminNotif.setMessage("O usuário " + user.getName() + " solicitou entrada na organização '" + organizer.getName() + "'. Acesse o painel para aprovar ou rejeitar.");
            adminNotif.setType("CANCEL");
            adminNotif.setRead(false);
            adminNotif.setCreatedAt(LocalDateTime.now());
//            adminNotif.setEvent(new Event());
            adminNotif.setEvent(eventRepository.findAll().getFirst());
            notificationService.notify(adminNotif);

        }

    }

    private OrganizerRequest findRequestOrThrow(Integer id) {
        OrganizerRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada."));
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new ConflictException("Esta solicitação já foi " + request.getStatus().name() + " e não pode ser alterada.");
        }
        return request;
    }
}