package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.GetReviewRes;
import doubleni.mealrecipe.service.ReviewService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.INVALID_USER_JWT;
import static doubleni.mealrecipe.config.exception.BaseResponseStatus.USERS_EMPTY_USER_ID;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtService jwtService;

    /**
     * reviewId 리뷰 조회 api
     * [GET] /review/{reviewId}
     *
     * @return BaseResponse<GetRecipeIdRes>
     */

    @GetMapping("/review/{reviewId}")
    @ApiOperation(value="reviewId 리뷰 조회", notes="review")
    public BaseResponse<GetReviewRes> ReviewByReviewId (@PathVariable Long reviewId, @RequestParam Long id){
        try{
            Long idx = jwtService.getUserIdx();

            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            GetReviewRes getReviewRes = reviewService.getReviewId(reviewId);
            return new BaseResponse<>(getReviewRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
