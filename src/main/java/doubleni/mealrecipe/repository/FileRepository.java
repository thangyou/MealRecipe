package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<Files, Long> {
    Optional<Files> findByFileId(Long fileId);

}