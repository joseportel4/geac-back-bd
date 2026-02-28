package br.com.geac.backend.Infrastructure.Repositories;

import br.com.geac.backend.Domain.Entities.EventStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventStatisticsRepositoryView extends JpaRepository<EventStatistics, UUID> {
}

