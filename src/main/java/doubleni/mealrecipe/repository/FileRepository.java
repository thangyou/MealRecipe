package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}