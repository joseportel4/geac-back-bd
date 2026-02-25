package br.com.geac.backend.Repositories;

import br.com.geac.backend.Domain.Entities.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {



    boolean existsByZipCodeAndNumberAndNameAndIdNot(String zipCode, String number, String name, Integer id);

    boolean existsByZipCodeAndNumberAndName(String zipCode, String number, String name);
}