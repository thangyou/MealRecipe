package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


    // 키워드로 레시피 찾기
    @Query(value = "SELECT r.rcpId, r.rcpNm, r.rcpSeq FROM Recipe r WHERE r.rcpNm like %:keyword% OR " +
                    "r.rcpPat2 like %:keyword% OR r.rcpWay2 like %:keyword% OR r.rcpSeq like %:keyword%")
    List<Recipe> searchRecipesByKeyword(@Param("keyword") String keyword);

//    @Query("SELECT r.RCP_NM, rp.RCP_PARTS_DTLS FROM RECIPE r" +
//            "INNER JOIN RcpPartsDtls rp ON r.RCP_ID = rp.RECIPE_RCP_ID" +
//            "WHERE rp.RCP_PARTS_DTLS LIKE %:keyword%")
//    Optional<Recipe> searByIngredient(@Param("keyword") String keyword);
}

