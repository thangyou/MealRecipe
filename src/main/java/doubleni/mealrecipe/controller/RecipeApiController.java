package doubleni.mealrecipe.controller;
import com.google.gson.Gson;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.dto.*;
import doubleni.mealrecipe.service.DataService;
import doubleni.mealrecipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class RecipeApiController {

    private final RecipeService recipeService;
    private final DataService dataService;

    /**
     * json 저장 api
     * [POST] /recipe
     *
     * @return BaseResponse<String>
     */
    @PostMapping("/recipe")
//    @ApiOperation(value="json 읽어들이기", notes="식약처 API 사용")
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
//    @ApiOperation(value="rcpId로 레시피 하나 조회", notes="식약처 API 사용")
    public BaseResponse<RecipeViewResponse> RecipeByRecipeId (@PathVariable Long rcpId){
        try{
            RecipeViewResponse getRecipeIdRes = recipeService.getRecipeIdRes(rcpId);
            return new BaseResponse<>(getRecipeIdRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /* 레시피 추가 */
    @PostMapping("/api/recipes")
    public ResponseEntity<Recipe> addRecipe(@RequestBody AddRecipeRequest request) {
        Recipe savedRecipe = recipeService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRecipe);
    }

    /* 레시피 조회 */
    @GetMapping("/api/recipes")
    public ResponseEntity<List<RecipeResponse>> findAllRecipes() {
        // ** 페이징 처리 필요
        List<RecipeResponse> recipes = recipeService.findAll()
                .stream()
                .map(RecipeResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(recipes);
    }

    @GetMapping("/api/recipes/{id}") // URL에서 값 추출
    public ResponseEntity<RecipeResponse> findRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.findById(id);

        return ResponseEntity.ok()
                .body(new RecipeResponse(recipe));
    }

    /* 레시피 삭제 */
    @DeleteMapping("/api/recipes/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipe(id);

        return ResponseEntity.ok()
                .build();
    }

    /* 레시피 수정 */
    @PutMapping("/api/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id,
                                                @RequestBody UpdateRecipeRequest request) {
        Recipe updateRecipe = recipeService.update(id, request);

        return ResponseEntity.ok()
                .body(updateRecipe);
    }

    // JSON 테스트 ===================================================================
    /* API 응답 데이터 출력 확인 */
//    @GetMapping("/api/test")
//    public ResponseEntity<String> fetchAndConvert() {
//        return recipeService.fetchDataAndConvertToJson();
//    }
//
//    /* API 응답 데이터를 DB에 저장 */
//    @GetMapping("/api/fetchAndSaveData")
//    public ResponseEntity<String> fetchDataAndSaveToDatabase() {
//        return dataService.fetchDataAndSaveToDatabase();
//    }
//
//    /* JsonString -> Recipe, CookBook */
//    @PostMapping("/gson/string-to-recipe")
//    public Recipe convertStringToRecipe(@RequestBody String jsonStr) throws ParseException {
//        Gson gson = new Gson();
//        Recipe recipe = gson.fromJson(jsonStr, Recipe.class);
//        return recipe;
//    }
//
//    @PostMapping("/gson/string-to-cookbook")
//    public COOKRCP01 convertStringToCookBook(@RequestBody String jsonStr) {
//        Gson gson = new Gson();
//        COOKRCP01 cookbook = gson.fromJson(jsonStr, COOKRCP01.class);
//        return cookbook;
//    }


    /* JSON 수신 확인 */
//    @GetMapping("/api/RecipeTest")
//    public ArrayList<Recipe> getOnlyRecipes() {
//        return recipeService.getOnlyRecipes();
//    }
//
//    @GetMapping("/api/RecipeAllTest")
//    public COOKRCP01 getAllCookBook() {
//        return recipeService.getAllCookBook();
//    }
//
//    //@PostMapping("/api/RecipeTest")
//    @RequestMapping(value="/api/RecipeTest", method=RequestMethod.POST)
////    public String postOneRecipe(@RequestBody Recipe request) {
//    public String postOneRecipe(@ModelAttribute Recipe request) {
//        String response = String.format("<레시피 정보>\n번호 : %s\n레시피명 : %s\n조리법 : %s",
//                request.getRcpSeq(), request.getRcpNm(), request.getRcpWay2());
//        return response;
//    }
//
//
//    /* 다중 객체 수신 */
//    @PostMapping("/api/RecipeListTest") // /api/RecipeAllTest
////    public String postAllRecipes(@RequestBody List<Recipe> recipes) {
//    public String postAllRecipes(@RequestParam List<Recipe> recipes) {
//        String response = "<레시피 정보>\n";
//
//        int i = 1;
//        for (Recipe recipe : recipes) {
//            response += String.format("순서%d: 번호 : %s\n레시피명 : %s\n조리법 : %s\n",
//                    i, recipe.getRcpSeq(), recipe.getRcpNm(), recipe.getRcpWay2());
//           i++;
//        }
//        return response;
//    }
//
//    @PostMapping("/api/RecipeAllTest")
////    public String postCookBook(@RequestBody COOKRCP01 cookbook) {
////    public String postCookBook(@RequestBody RecipeAPI recipeAPI) {
//    public String postCookBook(@ModelAttribute RecipeAPI recipeAPI) {
//
////        RecipeAPI recipeAPI = cookbook.getRecipeAPI();
//
//        String response = String.format("<레시피북 정보>\n레시피 개수 : %s\n", recipeAPI.getTotal_count());
//
//        int i = 1;
//        for (Recipe recipe : recipeAPI.getRecipes()) {
//            response += String.format("순서%d: 번호 : %s\n레시피명 : %s\n조리법 : %s\n",
//                    i, recipe.getRcpSeq(), recipe.getRcpNm(), recipe.getRcpWay2());
//            i++;
//        }
//
//        return response;
//    }


}
