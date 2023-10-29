package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Review;
import doubleni.mealrecipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByReviewId(Long reviewId);

    List<Review> findByUser(User user);
}
