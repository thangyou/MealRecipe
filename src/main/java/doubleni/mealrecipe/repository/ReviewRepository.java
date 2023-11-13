package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Recipe;
import doubleni.mealrecipe.model.Review;
import doubleni.mealrecipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByReviewId(Long reviewId);

    List<Review> findByUserOrderByReviewRatingDesc(User user);

    List<Review> findByRecipeOrderByReviewRatingDesc (Recipe recipe);

    List<Review> findAllByOrderByReviewRatingDesc();

    Optional<Review> findByUserAndRecipe (User user, Recipe recipe);
}
