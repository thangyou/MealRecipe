package doubleni.mealrecipe.Repository;

import doubleni.mealrecipe.model.dto.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String> {
    // Entity에 의해 생성된 DB에 접근하는 메서드 사용
    // findAll(), findOne() ..

}
