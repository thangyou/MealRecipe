package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.dto.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Component
@Configuration
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // Entity에 의해 생성된 DB에 접근하는 메서드 사용
    // findAll(), findOne() ..


}
