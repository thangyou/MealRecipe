package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.RecipeLike;
import doubleni.mealrecipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {
    void deleteByUserIdAndRecipe_RcpId(Long userId, Long rcpId);
    RecipeLike findRecipeLikeByUserAndRecipe_RcpId(User user, Long rcpId);
}
