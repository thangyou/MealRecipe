package doubleni.mealrecipe.controller;
import com.google.gson.Gson;
import doubleni.mealrecipe.model.dto.*;
import doubleni.mealrecipe.service.DataService;
import doubleni.mealrecipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
    @GetMapping("/api/test")
    public ResponseEntity<String> fetchAndConvert() {
        return recipeService.fetchDataAndConvertToJson();
    }

    /* API 응답 데이터를 DB에 저장 */
    @GetMapping("/api/fetchAndSaveData")
    public ResponseEntity<String> fetchDataAndSaveToDatabase() {
        return dataService.fetchDataAndSaveToDatabase();
    }

    /* JsonString -> Recipe, CookBook */
    @PostMapping("/gson/string-to-recipe")
    public Recipe convertStringToRecipe(@RequestBody String jsonStr) throws ParseException {
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(jsonStr, Recipe.class);
        return recipe;
    }

    @PostMapping("/gson/string-to-cookbook")
    public COOKRCP01 convertStringToCookBook(@RequestBody String jsonStr) {
        Gson gson = new Gson();
        COOKRCP01 cookbook = gson.fromJson(jsonStr, COOKRCP01.class);
        return cookbook;
    }


    /* JSON 수신 확인 */
    @GetMapping("/api/RecipeTest")
    public ArrayList<Recipe> getOnlyRecipes() {
        return recipeService.getOnlyRecipes();
    }

    @GetMapping("/api/RecipeAllTest")
    public COOKRCP01 getAllCookBook() {
        return recipeService.getAllCookBook();
    }

    @PostMapping("/api/RecipeTest")
    public String postOneRecipe(@RequestBody Recipe request) {
        String response = String.format("<레시피 정보>\n번호 : %s\n레시피명 : %s\n조리법 : %s",
                request.getRcp_seq(), request.getRcp_nm(), request.getRcp_way2());
        return response;
    }

    /* 다중 객체 수신 */
    @PostMapping("/api/RecipeListTest") // /api/RecipeAllTest
    public String postAllRecipes(@RequestBody List<Recipe> recipes) {
        String response = "<레시피 정보>\n";

        int i = 1;
        for (Recipe recipe : recipes) {
            response += String.format("순서%d: 번호 : %s\n레시피명 : %s\n조리법 : %s\n",
                    i, recipe.getRcp_seq(), recipe.getRcp_nm(), recipe.getRcp_way2());
           i++;
        }
        return response;
    }

    @PostMapping("/api/RecipeAllTest")
//    public String postCookBook(@RequestBody COOKRCP01 cookbook) {
    public String postCookBook(@RequestBody RecipeAPI recipeAPI) {

//        RecipeAPI recipeAPI = cookbook.getRecipeAPI();

        String response = String.format("<레시피북 정보>\n레시피 개수 : %s\n", recipeAPI.getTotal_count());

        int i = 1;
        for (Recipe recipe : recipeAPI.getRecipes()) {
            response += String.format("순서%d: 번호 : %s\n레시피명 : %s\n조리법 : %s\n",
                    i, recipe.getRcp_seq(), recipe.getRcp_nm(), recipe.getRcp_way2());
            i++;
        }

        return response;
    }



//    @GetMapping("/api/fetchAndSaveData")
//    public ResponseEntity<String> fetchAndSaveData() {
//        try {
//            // fetchDataAndConvertToJson() 메서드를 호출하여 JSON 데이터 가져오기
////            String jsonData = recipeService.fetchDataAndConvertToJson();
//
//            // JSON 데이터를 데이터베이스에 저장
////            recipeService.saveJsonDataToDatabase(jsonData);
//
////            return ResponseEntity.ok("Data fetched and saved to database successfully.");
//            return recipeService.fetchDataAndConvertToJson();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Failed to fetch and save data to database.");
//        }
//    }





}
