package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {

    Optional<Api> findById(Long id);
    Optional<Api> findByRcpNm(String rcpNm);

    // Order By
    // findFirstByNameOrderByIdDescEmailAsc(String name)
    List<Api> findAllByOrderByInfoProDesc(); // 내림차순
    List<Api> findAllByOrderByInfoFatAsc(); // 오름차순

    // LIKE %:keyword%"
    List<Api> findByRcpNmContaining(@Param("keyword") String keyword);
    List<Api> findByIngredientContaining(@Param("ingredient") String ingredient);

//    List<Api> findByRcpNmContainingOrIngredientContaining(@Param("keyword") String keyword);

}
