package br.com.geac.backend.Infrastructure.Repositories;

import br.com.geac.backend.Domain.Entities.Evaluation;
import br.com.geac.backend.Domain.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EvaluationRepository  extends JpaRepository<Evaluation, Integer> {
    List<Evaluation> findAllByRegistrationEvent(Event registrationEvent);

    boolean existsByRegistrationId(UUID registrationId);
}
