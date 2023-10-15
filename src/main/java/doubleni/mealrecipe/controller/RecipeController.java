package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.GetRecipeIdRes;
import doubleni.mealrecipe.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class RecipeController {
    private final RecipeService recipeService;

    /**
     * json 저장 api
     * [POST] /recipe
     *
     * @return BaseResponse<String>
     */
    @PostMapping("/recipe")
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

    @GetMapping("/recipe/{rcpId}")
    @ApiOperation(value="rcpId로 레시피 하나 조회", notes="식약처 API 사용")
    public BaseResponse<GetRecipeIdRes> RecipeByRecipeId (@PathVariable Long rcpId){
        try{
            GetRecipeIdRes getRecipeIdRes = recipeService.getRecipeIdRes(rcpId);
            return new BaseResponse<>(getRecipeIdRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
