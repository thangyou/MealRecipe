package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Review;
import doubleni.mealrecipe.model.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    Optional<ReviewImage> findByReview(Review review);
}
