package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.model.Recipe;
import doubleni.mealrecipe.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    /**
     * json 저장 api
     * [POST] /recipe
     *
     * @return BaseResponse<String>
     */
    @PostMapping("/read-json")
    @ApiOperation(value="json 읽어들이기", notes="식약처 API 사용")
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
     * rcpId 레시피 조회 api
     * [GET] /recipe/{rcpId}
     *
     * @return BaseResponse<GetRecipeIdRes>
     */
    @GetMapping("/{rcpId}")
    @ApiOperation(value="rcpId로 레시피 하나 조회", notes="식약처 API 사용")
    public BaseResponse<GetRecipeIdRes> RecipeByRecipeId (@PathVariable Long rcpId){
        try{
            GetRecipeIdRes getRecipeIdRes = recipeService.getRecipeIdRes(rcpId);
            return new BaseResponse<>(getRecipeIdRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/search?keyword={keyword}")
    @ApiOperation(value="keyword로 레시피 조회", notes="식약처 API 사용")
    public ResponseEntity<List<RecipeResponse>> RecipeByKeyword (@PathVariable String keyword){
        List<RecipeResponse> recipeResponses = new ArrayList<>();
        try{
            recipeResponses = recipeService.RecipeByKeyword(keyword);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(recipeResponses);
    }

    /**
     * 전체 레시피 목록
     * [GET] /recipe
     *
     * @return BaseResponse<List<RecipeResponse>>
     */
    @GetMapping("/List")
    @ApiOperation(value = "전체 레시피 조회", notes = "식약처 API 사용")
    public ResponseEntity<List<RecipeResponse>> getRecipes() {
        List<RecipeResponse> recipeResponses = new ArrayList<>();

        try {
            recipeResponses = recipeService.getAllRecipes();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(recipeResponses);

    }

    /**
     * 재료 키워드로 레시피 검색
     * [GET] /recipe/search/{keyword}
     *
     * @return BaseResponse<RecipeResponse>
     */


}
