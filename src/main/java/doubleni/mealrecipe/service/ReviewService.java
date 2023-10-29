package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.*;
import doubleni.mealrecipe.model.DTO.GetReviewRes;
import doubleni.mealrecipe.repository.RecipeRepository;
import doubleni.mealrecipe.repository.ReviewImageRepository;
import doubleni.mealrecipe.repository.ReviewRepository;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ReviewImageRepository reviewImageRepository;

    //리뷰 작성
    public GetReviewRes postReview (String reviewContext, double reviewRating, Long recipeId , MultipartFile imageFile, Long userId) throws BaseException {
        try{
            Optional<User> userOptional = userRepository.findById(userId);
            Optional<Recipe> recipeOptional = recipeRepository.findByRcpId(recipeId);

            if (userOptional.isPresent() && recipeOptional.isPresent()) {
                User user = userOptional.get();
                Recipe recipe = recipeOptional.get();

                Review review = Review.builder()
                        .reviewContext(reviewContext)
                        .user(user)
                        .reviewRating(reviewRating)
                        .reviewCreated(new Timestamp(System.currentTimeMillis()))
                        .recipe(recipe)
                        .build();

                if(imageFile != null){

                    // 이미지 파일 저장 경로
                    String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\templates\\image\\";
                    UUID uuid = UUID.randomUUID();
                    String originalFileName = uuid + "_" + imageFile.getOriginalFilename();
                    File saveFile = new File(projectPath +originalFileName);

                    // 이미지 URL 정보를 리스트에 추가
                    String imageUrl = "http://localhost:8080/review/images/" + originalFileName;

                    try {
                        imageFile.transferTo(saveFile);
                        ReviewImage reviewImage = new ReviewImage();
                        reviewImage.setImgName(originalFileName);
                        reviewImage.setImgOriName(imageFile.getOriginalFilename());
                        reviewImage.setImgPath(saveFile.getAbsolutePath());
                        reviewImage.setReview(review);
                        reviewImageRepository.save(reviewImage);

                        review.setReviewImageUrl(imageUrl);

                    } catch (IOException e){
                        throw new RuntimeException("이미지 저장에 실패하였습니다.", e);
                    }

                }

                reviewRepository.save(review);

                GetReviewRes getReviewRes = new GetReviewRes();
                getReviewRes.setReviewId(review.getReviewId());
                getReviewRes.setUserId(review.getUser().getId());
                getReviewRes.setRecipeId(review.getRecipe().getRcpId());
                getReviewRes.setReviewContext(review.getReviewContext());
                getReviewRes.setReviewImageUrl(review.getReviewImageUrl());
                getReviewRes.setReviewRating(review.getReviewRating());
                getReviewRes.setReviewCreated(review.getReviewCreated());
                getReviewRes.setReviewModified(review.getModifiedDate());
                getReviewRes.setNickName(review.getUser().getNickname());
                getReviewRes.setRecipeName(review.getRecipe().getRcpNm());

                return getReviewRes;

            } else {
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
        } catch (Exception exception){
            throw new BaseException(POST_REVIEWS_FAILS);
        }
    }



    //reviewId 조회
    public GetReviewRes getReviewId (Long reviewId) throws BaseException{
        try{
            Optional<Review> reviewOptional = reviewRepository.findByReviewId(reviewId);
            if (reviewOptional.isPresent()){
                Review review = reviewOptional.get();

                GetReviewRes getReviewRes = new GetReviewRes();
                getReviewRes.setReviewId(review.getReviewId());
                getReviewRes.setUserId(review.getUser().getId());
                getReviewRes.setRecipeId(review.getRecipe().getRcpId());
                getReviewRes.setReviewContext(review.getReviewContext());
                getReviewRes.setReviewImageUrl(review.getReviewImageUrl());
                getReviewRes.setReviewRating(review.getReviewRating());
                getReviewRes.setReviewCreated(review.getReviewCreated());
                getReviewRes.setReviewModified(review.getModifiedDate());
                getReviewRes.setNickName(review.getUser().getNickname());
                getReviewRes.setRecipeName(review.getRecipe().getRcpNm());

                return getReviewRes;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
        return null;
    }


}
