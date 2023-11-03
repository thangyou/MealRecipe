package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.DTO.GetRecipeRes;
import doubleni.mealrecipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // 레시피 이름으로 찾기
    Optional<Recipe> findByRcpNm(String rcpNm);

    // 레시피 id 찾기
    Optional<Recipe> findByRcpId(Long rcpId);


    GetRecipeRes findRecipeByRcpId(Long rcpId);

    // Order By
    // findFirstByRcpNmOrderByInfoProDescInfoFatAsc(String rcpNm)
    List<Recipe> findAllByOrderByInfoProDesc(); // 내림차순
    List<Recipe> findAllByOrderByInfoFatAsc(); // 오름차순

    // 키워드로 레시피 찾기 - LIKE %:keyword%"
    List<Recipe> findByRcpNmContaining(@Param("keyword") String keyword);
//    List<Recipe> findRecipesWithPartOfkeyword(@Param("keyword") String keyword);
    List<Recipe> findByRcpPartsDtlsContaining(@Param("keyword") String ingredient);

}

