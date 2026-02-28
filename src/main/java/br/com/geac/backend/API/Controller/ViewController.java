package br.com.geac.backend.API.Controller;

import br.com.geac.backend.Domain.Entities.EventStatistics;
import br.com.geac.backend.Infrastructure.Repositories.EventStatisticsRepositoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/views")
public class ViewController {

    @Autowired
    private EventStatisticsRepositoryView repository;

    @GetMapping("/eventstatistics")
    public List<EventStatistics> getview1() {
        return repository.findAll();
    }
}