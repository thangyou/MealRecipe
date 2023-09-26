package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.vo.RecipeVO;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeVoRepository extends JpaRepository<RecipeVO, Long> {
}
