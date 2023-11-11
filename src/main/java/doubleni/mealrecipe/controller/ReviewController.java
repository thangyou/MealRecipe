package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.model.Recipe;
import doubleni.mealrecipe.service.RecipeService;
import doubleni.mealrecipe.service.ReviewService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@Api(tags = "Review", description = "리뷰")
public class ReviewController {

    private final ReviewService reviewService;
    private final RecipeService recipeService;
    private final JwtService jwtService;


    /**
     * 리뷰 작성 api
     * [POST] /review
     *
     * @return BaseResponse<GetReviewRes>
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
            return new BaseResponse<>(RECIPE_ID_NO_EXISTS);
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
     * 리뷰 전체 조회 api
     * [GET] /review
     *
     * @return BaseResponse<List<GetReviewRecipeRes>>
     */

    @GetMapping("/review")
    @ApiOperation(value="리뷰 전체 조회", notes="review")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다.")
    })
    public BaseResponse<ReviewResponse> ReviewByAll (){

        try{
            List<GetReviewRecipeRes> getReviewRes = reviewService.getReviewByAll();
            ReviewResponse response = new ReviewResponse(getReviewRes);
            return new BaseResponse<>(response);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



    /**
     * reviewId 리뷰 조회 api
     * [GET] /review/{reviewId}
     *
     * @return BaseResponse<GetReviewRecipeRes>
     */

    @GetMapping("/review/{reviewId}")
    @ApiOperation(value="reviewId 리뷰 조회", notes="review")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요."),@ApiResponse(code=2036,message = "저장된 리뷰가 없습니다.")
    })
    public BaseResponse<GetReviewRecipeRes> ReviewByReviewId (@PathVariable Long reviewId){

        try{

            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            GetReviewRecipeRes getReviewRes = reviewService.getReviewId(reviewId);
            return new BaseResponse<>(getReviewRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }




    /**
     * 사용자 리뷰 조회 api
     * [GET] /review/users
     *
     * @return BaseResponse<List<GetReviewRecipeRes>>
     */

    @GetMapping("/review/users")
    @ApiOperation(value="사용자 본인이 작성한 리뷰 조회", notes="review")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code=2011,message = "존재하지 않는 유저입니다."),@ApiResponse(code=2036,message = "저장된 리뷰가 없습니다.")
    })
    public BaseResponse<ReviewResponse> ReviewByUserId (){
        try{
            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            List<GetReviewRecipeRes> getReviewRes = reviewService.getReviewByUser(idx);
            ReviewResponse response = new ReviewResponse(getReviewRes);
            return new BaseResponse<>(response);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



    /**
     * 레시피별 리뷰 조회 api
     * [GET] /review/{rcpId}
     *
     * @return BaseResponse<List<GetReviewRecipeRes>>
     */

    @GetMapping("/review/recipe/{rcpId}")
    @ApiOperation(value="레시피 별 리뷰 조회", notes="review")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code=2050,message = "존재하지 않는 레시피입니다."),@ApiResponse(code=2036,message = "저장된 리뷰가 없습니다.")
    })
    public BaseResponse<ReviewRecipeResponse> ReviewByRecipeId (@PathVariable Long rcpId){

        try{
            List<GetReviewRes> getReviewRes = reviewService.getReviewByRecipeId(rcpId);
            Recipe recipe = recipeService.getRecipeByRcpId(rcpId);
            ReviewRecipeResponse response = new ReviewRecipeResponse(getReviewRes,recipe);
            return new BaseResponse<>(response);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 리뷰 수정 api
     * [PUT] /review/{reviewId}
     *
     * @return BaseResponse<GetRecipeIdRes>
     */

    @PutMapping("/review/{reviewId}")
    @ApiOperation(value="작성한 리뷰 수정", notes="review")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code=4031,message = "파일 수정 실패")
    })
    public BaseResponse<GetReviewRes> ReviewByFIX (@PathVariable Long reviewId, @RequestParam(required = false) String reviewContext, @RequestParam(required = false) double reviewRating,
                                                         @RequestPart(value = "images" ,required = false) MultipartFile imageFile){

        try{
            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            GetReviewRes getReviewRes = reviewService.reviewfixinfo(reviewContext,reviewRating,imageFile,reviewId);

            return new BaseResponse<>(getReviewRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 리뷰 삭제 api
     * [DELETE] /review/{reviewId}
     *
     * @return BaseResponse<String>
     */

    @DeleteMapping("/review/{reviewId}")
    @ApiOperation(value="작성한 리뷰 삭제", notes="review")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code=4031,message = "파일 수정 실패")
    })
    public BaseResponse<String> ReviewByDelete (@PathVariable Long reviewId){

        try{
            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            reviewService.ReviewIdDeleteImage(reviewId);
            reviewService.ReviewIdDelete(reviewId);

            return new BaseResponse<>("리뷰가 삭제 되었습니다.");
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    //저장된 이미지 조회
    //이미지 네임을 알 수 있음 그 거를 기반으로 이미지 조회하기
    //http://15.164.139.103:8080/review/images/7f658d91-ef68-4b59-a381-af5bc9938768_fighting.png
    @ResponseBody
    @GetMapping("/review/images/{imageName}")
    @ApiOperation(value="리뷰 이미지 조회", notes="이미지 조회할 때 url을 여기다가 붙여서 get 보내셈 \n 근데, localhost를 15.164.139.103으로 변경해야함!")
    public ResponseEntity<byte[]> getReviewImage(@PathVariable String imageName) {
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\templates\\image\\";
        String imagePath = projectPath + imageName;

        try {
            FileInputStream imageStream = new FileInputStream(imagePath);
            byte[] imageBytes = imageStream.readAllBytes();
            imageStream.close();

            String contentType = determineContentType(imageName); // 이미지 파일 확장자에 따라 MIME 타입 결정

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String determineContentType(String imageName) {
        String extension = FilenameUtils.getExtension(imageName); // Commons IO 라이브러리 사용
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            // 다른 이미지 타입 추가 가능
            default:
                return "application/octet-stream"; // 기본적으로 이진 파일로 다룸
        }
    }

}
