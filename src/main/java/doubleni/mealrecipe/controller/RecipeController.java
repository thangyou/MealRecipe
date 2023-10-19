package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
     * rcpId 레시피 조회 api
     * [GET] /recipe/{rcpId}
     *
     * @return BaseResponse<GetRecipeIdRes>
     */
    @GetMapping("/{rcpId}")
    @ApiOperation(value="레시피 조회 API", notes="rcpId로 레시피 조회")
    public BaseResponse<GetRecipeRes> RecipeByRecipeId (@PathVariable Long rcpId){
        try{
            GetRecipeRes getRecipeIdRes = recipeService.getRecipeIdRes(rcpId);
            return new BaseResponse<>(getRecipeIdRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 레시피 상세 정보 조회 api
     * [GET] /recipe/ingredient/{rcpNm}
     *
     *  @return BaseResponse<String>
     */
    @GetMapping("/ingredient/{rcpId}")
    @ApiOperation(value = "재료 검색 API", notes = "rcpId로 재료 검색")
    public ResponseEntity<?> getDetailById(@PathVariable Long rcpId) {
        /* 레시피명 검색하여 해당 레시피의 재료 읽어오기 TEST */
        return ResponseEntity.ok().body(recipeService.getDetailById(rcpId));
    }

//    @GetMapping("/ingredient/{rcpNm}")
//    @ApiOperation(value = "재료 검색 API", notes = "rcpNm로 재료 검색")
//    public ResponseEntity<?> getDetailByName(@PathVariable String rcpNm) {
//        /* 레시피명 검색하여 해당 레시피의 재료 읽어오기 TEST */
//        return ResponseEntity.ok().body(recipeService.getDetailByName(rcpNm));
//    }

    /**
     * [GET] /recipe/search?keyword={keyword}
     * @param keyword
     *
     * @return BaseResponse<List>
     */
    @GetMapping("/search")
    @ApiOperation(value="키워드 검색 API", notes="키워드로 레시피 검색")
    public ResponseEntity<List<GetRecipeRes>> searchByKeyword (@RequestParam("keyword") String keyword){
        List<GetRecipeRes> getRecipeRespons = new ArrayList<>();
        try{
            getRecipeRespons = recipeService.getRecipesSearchedBy(keyword);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(getRecipeRespons);
    }

    /* 재료 키워드로 레시피 찾기 */
//    @GetMapping("/search")
//    @ApiOperation(value="키워드 검색 API", notes="재료 키워드로 레시피 검색")
//    public ResponseEntity<?> searchByIngredient(@RequestParam("keyword") String keyword) {
//        return ResponseEntity.ok().body(recipeService.searchByIngredient(keyword));
//    }

    /**
     * 전체 레시피 목록 조회
     * [GET] /recipe/List
     *
     * @return BaseResponse<List>
     */
    @GetMapping("/list")
    @ApiOperation(value = "레시피 조회 API", notes = "레시피 리스트 조회")
    public ResponseEntity<List<GetRecipeRes>> getRecipes() {
        List<GetRecipeRes> getRecipeRes = new ArrayList<>();
        try {
            getRecipeRes = recipeService.getAllRecipes();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(getRecipeRes);
    }




}
