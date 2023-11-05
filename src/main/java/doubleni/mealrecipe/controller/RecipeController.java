package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.*;
import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
@Api(tags = "Recipe", description = "식약처 공공 API")
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping("/recipe")
public class RecipeController {
    /**
     * 조회 - get
     * 검색 - search(Controller & Service) / find(Repository)
     * 생성 - save
     * 수정 - update
     * 삭제 - delete
     */

    private final RecipeService recipeService;

    /**
     * json 저장 api
     * [POST] /recipe
     *
     * @return BaseResponse<String>
     */
    @PostMapping("/read-json")
    @ApiOperation(value="json 저장 API", notes="식약처 API 사용")
    public ResponseEntity<String> readRecipes() {
        try {
            // JSON 파일을 읽고 데이터베이스에 저장하는 작업 수행
            recipeService.read();
            return ResponseEntity.ok("레시피를 성공적으로 저장했습니다!");
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 모든 레시피(리스트) 조회 api
     * [GET] /recipe/List
     *
     * @return BaseResponse<List>
     */
    @GetMapping("/list")
    @ApiOperation(value = "레시피 조회 API", notes = "모든 레시피(리스트) 조회")
    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
    public BaseResponse<List<GetRecipeRes>> getAllRecipes() throws BaseException {
        List<GetRecipeRes> getRecipeRes = recipeService.getAllRecipes();
        if (getRecipeRes.isEmpty()) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
        return new BaseResponse<>(getRecipeRes);
    }


    /**
     * rcpId로 레시피 조회 api
     * [GET] /recipe/rcpId={rcpId}
     *
     * @return BaseResponse<GetRecipeIdRes>
     */
    @GetMapping("/rcpId={rcpId}")
    @ApiOperation(value="레시피 조회 API", notes="id로 레시피 조회")
    @ApiResponses(value={@ApiResponse(code =2000,message = "입력값을 확인해주세요."),
            @ApiResponse(code =2050,message = "존재하지 않는 레시피입니다."),
            @ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
    public BaseResponse<GetRecipeRes> getRecipeById (@PathVariable Long rcpId) {
        if (rcpId == null) {
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try{
            GetRecipeRes getRecipeIdRes = recipeService.getRecipeById(rcpId);
            if (getRecipeIdRes == null) {
                return new BaseResponse<>(RECIPE_NOT_EXISTS);
            }
            return new BaseResponse<>(getRecipeIdRes);
        }catch (BaseException exception){
            return new BaseResponse<>(RESPONSE_ERROR);
        }
    }

    /**
     * 고단백 조건 정렬 조회 api
     * [GET] /recipe/list-order-by-protein
     *
     * @return BaseResponse<GetRecipeIdRes>
     */
//    @GetMapping("/list-order-by-protein")
//    @ApiOperation(value="레시피 조건 정렬 API", notes="고단백 레시피 정렬")
//    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
//    public BaseResponse<List<GetRecipeOrderRes>> getRecipeByOrderByInfoProDesc() {
//        try {
//            List<GetRecipeOrderRes> getRecipeRes  = recipeService.getRecipeByOrderByInfoProDesc();
//            return new BaseResponse<>(getRecipeRes);
//        } catch (Exception e) {
//            return new BaseResponse<>(RESPONSE_ERROR);
//        }
//    }

    /**
     * 저지방 조건 정렬 조회 api
     * [GET] /recipe/list-order-by-fat
     *
     * @return BaseResponse<GetRecipeIdRes>
     */
//    @GetMapping("/list-order-by-fat")
//    @ApiOperation(value="레시피 조건 정렬 API", notes="저지방 레시피 정렬")
//    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
//    public BaseResponse<List<GetRecipeOrderRes>> getRecipeByOrderByInfoFatAsc() {
//        try {
//            List<GetRecipeOrderRes> getRecipeRes  = recipeService.getRecipeByOrderByInfoFatAsc();
//            return new BaseResponse<>(getRecipeRes);
//        } catch (Exception e) {
//            return new BaseResponse<>(RESPONSE_ERROR);
//        }
//    }

    // ===============================================================================================

    /**
     * 키워드(in 레시피) 리스트 검색 api
     * [GET] /recipe/search-recipe-of?keyword={keyword}
     *
     *  @return BaseResponse<String>
     */
    // 키워드로 검색 - 키워드가 레시피명에 속한 레시피 리스트 출력
    @GetMapping("/search-recipe-of")
    @ApiOperation(value="레시피 검색 API", notes="키워드로 레시피 검색")
    @ApiResponses(value={@ApiResponse(code =2000,message = "입력값을 확인해주세요."),
            @ApiResponse(code =2050,message = "존재하지 않는 레시피입니다.")})
    public BaseResponse<List<GetRecipeRes>> searchRecipeByKeyword (@RequestParam("keyword") String keyword){
        try{
            List<GetRecipeRes> response = recipeService.searchRecipeByName(keyword);
            if (response.isEmpty()) {
                return new BaseResponse<>(RECIPE_NOT_EXISTS);
            }
            return new BaseResponse<>(response);
        }catch (BaseException exception){
            return new BaseResponse<>(REQUEST_ERROR);
        }
    }

    /**
     * 레시피(in 재료) 상세 정보 검색 api
     * [GET] /recipe/search-recipe-of-ingredient?ingredient={ingredient}
     *
     *  @return BaseResponse<String>
     */
    // 재료로 검색 - 재료가 속한 레시피 리스트 출력
    @GetMapping("/search-recipe-of-ingredient")
    @ApiOperation(value = "레시피 검색 API", notes = "재료로 레시피 검색")
    @ApiResponses(value={@ApiResponse(code =2000,message = "입력값을 확인해주세요."),
            @ApiResponse(code =2050,message = "존재하지 않는 레시피입니다.")})
    public BaseResponse<List<GetRecipeRes>> searchRecipeByRcpPartsDtls (@RequestParam("keyword") String ingredient) throws BaseException {
        if (ingredient.isEmpty()) {
            return new BaseResponse<>(REQUEST_ERROR);
        }

        List<GetRecipeRes> response = recipeService.searchRecipeByRcpPartsDtls(ingredient);
        if (response.isEmpty()) {
           return new BaseResponse<>(RECIPE_NOT_EXISTS);
        }
        return new BaseResponse<>(response);

    }

    /**
     * 재료(of 레시피) 상세 정보 검색 api
     * [GET] /recipe/search-ingredient-of-recipe?keyword={keyword}
     *
     *  @return BaseResponse<String>
     */
    // 레시피명 검색 - 레시피의 재료 출력
    @GetMapping("/search-ingredient-of-recipe")
    @ApiOperation(value = "재료 검색 API", notes = "레시피의 재료 검색")
    @ApiResponses(value={@ApiResponse(code =2000,message = "입력값을 확인해주세요."),
            @ApiResponse(code =2050,message = "존재하지 않는 레시피입니다.")})
    public BaseResponse<List<String>> searchRcpPartsDtlsByRcpNm (@RequestParam("keyword") String keyword) throws BaseException {
        if (keyword.isEmpty()) {
            return new BaseResponse<>(REQUEST_ERROR);
        }
                List<String> response = recipeService.searchRcpPartsDtlsByRcpNm(keyword);
        if (response.isEmpty()) {
            return new BaseResponse<>(RECIPE_NOT_EXISTS);
        }
        return new BaseResponse<>(response);
    }



}
