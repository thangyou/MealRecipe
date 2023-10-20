package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.DTO.GetReviewRes;
import doubleni.mealrecipe.model.Review;
import doubleni.mealrecipe.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    //reviewId 조회
    public GetReviewRes getReviewId (Long reviewId) throws BaseException{
        try{
            Optional<Review> reviewOptional = reviewRepository.findByReviewId(reviewId);
            if (reviewOptional.isPresent()){
                Review review = reviewOptional.get();

                GetReviewRes getReviewRes = new GetReviewRes();
                getReviewRes.setReviewId(review.getReviewId());
                getReviewRes.setUserId(review.getUserId());
                getReviewRes.setRecipeId(review.getRecipeId());
                getReviewRes.setReviewContext(review.getReviewContext());
                getReviewRes.setReviewImage(review.getReviewImage());
                getReviewRes.setReviewRating(review.getReviewRating());
                getReviewRes.setReviewCreated(review.getReviewCreated());

                return getReviewRes;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
        return null;
    }


}
