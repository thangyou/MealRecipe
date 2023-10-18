package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // 레시피 이름으로 찾기
    Optional<Recipe> findByRcpNm(String rcpNm);

    // 레시피 id 찾기
    Optional<Recipe> findByRcpId(Long rcpId);

    // 키워드로 레시피 찾기
    @Query(value =
            "SELECT r.rcpNm, r.rcpWay2, r.rcpPat2 FROM Recipe r WHERE r.rcpNm like %:keyword%" +
            "or EXISTS (" +
            "SELECT 1 FROM Recipe r WHERE r.rcpNm like concat('%', :keyword, '%')" +
            ")",
            nativeQuery = true)
    List<Recipe> searchByKeyword(@Param("keyword") String keyword);

}

