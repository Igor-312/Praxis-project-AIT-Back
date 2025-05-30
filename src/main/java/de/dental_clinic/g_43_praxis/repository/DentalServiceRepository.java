package de.dental_clinic.g_43_praxis.repository;

import de.dental_clinic.g_43_praxis.domain.entity.DentalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DentalServiceRepository extends JpaRepository<DentalService, Long> {

    List<DentalService> findByIsActiveTrue();

    boolean existsByTitleEnContainingIgnoreCase(String title);

    boolean existsByTitleEnIgnoreCaseAndIdNot(String titleEn, Long id);

}