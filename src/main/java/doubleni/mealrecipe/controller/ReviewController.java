package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.GetReviewRes;
import doubleni.mealrecipe.service.ReviewService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtService jwtService;


    /**
     * 리뷰 작성 api
     * [POST] /review
     *
     * @return BaseResponse<GetRecipeIdRes>
     */

    @PostMapping("/review")
    @ApiOperation(value = "리뷰 작성", notes = "리뷰 작성")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요."),@ApiResponse(code=2051,message = "레시피 아이디를 입력해주세요."),
            @ApiResponse(code=2033,message = "리뷰 내용을 입력해주세요."),@ApiResponse(code=2034,message = "리뷰 평점을 입력해주세요."),
            @ApiResponse(code=2035,message = "리뷰 작성 실패하였습니다.")
    })
    public BaseResponse<GetReviewRes> ReviewPost (@RequestParam String reviewContext, @RequestParam double reviewRating, @RequestParam Long recipeId,
                                       @RequestPart(value = "images" ,required = false) MultipartFile imageFile)
    {
        if (reviewContext == null){
            return new BaseResponse<>(POST_REVIEWS_NO_CONTEXT);
        }

        if (reviewRating == 0.0){
            return new BaseResponse<>(POST_REVIEWS_NO_RATINGS);
        }

        if (recipeId == null) {
            return new BaseResponse<>(RECIPE_ID_NOEXISTS);
        }


        try{
            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            GetReviewRes getReviewRes = reviewService.postReview(reviewContext,reviewRating,recipeId,imageFile,idx);
            return new BaseResponse<>(getReviewRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }


    }



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

            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            GetReviewRes getReviewRes = reviewService.getReviewId(reviewId);
            return new BaseResponse<>(getReviewRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
