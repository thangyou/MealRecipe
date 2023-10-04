package doubleni.mealrecipe.controller;
import doubleni.mealrecipe.model.dto.*;
import doubleni.mealrecipe.model.dto.RecipeJSON;
import doubleni.mealrecipe.service.DataService;
import doubleni.mealrecipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class RecipeApiController {

    private final RecipeService recipeService;
    private final DataService dataService;

    @PostMapping("/api/recipes")
    public ResponseEntity<Recipe> addRecipe(@RequestBody AddRecipeRequest request) {
        Recipe savedRecipe = recipeService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRecipe);
    }

    @GetMapping("/api/recipes")
    public ResponseEntity<List<RecipeResponse>> findAllRecipes() {
        List<RecipeResponse> recipes = recipeService.findAll()
                .stream()
                .map(RecipeResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(recipes);
    }

    @GetMapping("/api/recipes/{id}")
    // URL에서 값 추출
    public ResponseEntity<RecipeResponse> findRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.findById(id);

        return ResponseEntity.ok()
                .body(new RecipeResponse(recipe));
    }


    @DeleteMapping("/api/recipes/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipe(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id,
                                                @RequestBody UpdateRecipeRequest request) {
        Recipe updateRecipe = recipeService.update(id, request);

        return ResponseEntity.ok()
                .body(updateRecipe);
    }




    // JSON 테스트
    @GetMapping("/api/test")
    public ResponseEntity<String> fetchAndConvert() {
        return recipeService.fetchDataAndConvertToJson();
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

    @GetMapping("/fetchAndSaveData")
    public ResponseEntity<String> fetchDataAndSaveToDatabase() {
        return dataService.fetchDataAndSaveToDatabase();
    }




}
