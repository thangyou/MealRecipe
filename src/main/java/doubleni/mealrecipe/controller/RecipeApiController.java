package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.model.dto.AddRecipeRequest;
import doubleni.mealrecipe.model.dto.Recipe;
import doubleni.mealrecipe.model.dto.RecipeResponse;
import doubleni.mealrecipe.model.dto.UpdateRecipeRequest;
import doubleni.mealrecipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class RecipeApiController {

    private final RecipeService recipeService;

    @PostMapping("/api/recipes")
    public ResponseEntity<Recipe> addArticle(@RequestBody AddRecipeRequest request) {
        Recipe savedRecipe = recipeService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRecipe);
    }

    @GetMapping("/api/recipes")
    public ResponseEntity<List<RecipeResponse>> findAllRecipe() {
        List<RecipeResponse> recipes = recipeService.findAll()
                .stream()
                .map(RecipeResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(recipes);
    }

    @GetMapping("/api/recipes/{rcp_seq}")
    // URL에서 값 추출
    public ResponseEntity<RecipeResponse> findRecipeId(@PathVariable String rcp_seq) {
        Recipe recipe = recipeService.findById(rcp_seq);

        return ResponseEntity.ok()
                .body(new RecipeResponse(recipe));
    }


    @DeleteMapping("/api/recipes/{rcp_seq}")
    public ResponseEntity<RecipeResponse> deleteRecipe(@PathVariable String rcp_seq) {
        recipeService.deleteRecipe(rcp_seq);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/recipes/{rcp_seq}")
    public ResponseEntity<Recipe> updateArticle(@PathVariable String rcp_seq,
                                                 @RequestBody UpdateRecipeRequest request) {
        Recipe updateRecipe = recipeService.update(rcp_seq, request);

        return ResponseEntity.ok()
                .body(updateRecipe);
    }

}
