package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<ImageFile, Long> {
    Optional<ImageFile> findByFileId(Long fileId);

}