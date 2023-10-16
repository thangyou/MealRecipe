package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.dto.Recipe;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@Component
@Configuration
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    //레시피이름으로 찾기
    Optional<Recipe> findByRcpNm(String rcpNm);

    //레시피 id 찾기
    Optional<Recipe> findByRcpId(Long rcpId);

}