package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.GetReviewRes;
import doubleni.mealrecipe.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * reviewId 리뷰 조회 api
     * [GET] /review/{reviewId}
     *
     * @return BaseResponse<GetRecipeIdRes>
     */

    @GetMapping("/review/{reviewId}")
    @ApiOperation(value="reviewId 리뷰 조회", notes="review")
    public BaseResponse<GetReviewRes> ReviewByReviewId (@PathVariable Long reviewId){
        try{
            GetReviewRes getReviewRes = reviewService.getReviewId(reviewId);
            return new BaseResponse<>(getReviewRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
