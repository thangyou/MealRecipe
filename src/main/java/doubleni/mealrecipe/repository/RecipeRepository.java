package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    //레시피이름으로 찾기
    Optional<Recipe> findByRcpNm(String rcpNm);
}