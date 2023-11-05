package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RecommendRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByRcpNm (String rcpNm);

    Optional<Recipe> findByRcpSeq (String RcpSeq);
}
