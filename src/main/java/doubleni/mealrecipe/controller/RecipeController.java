package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.model.dto.AddRecipeRequest;
import doubleni.mealrecipe.model.dto.Recipe;
import doubleni.mealrecipe.model.dto.RecipeListViewResponse;
import doubleni.mealrecipe.model.dto.RecipeViewResponse;
import doubleni.mealrecipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class RecipeController {
    // 사용자 Request 전달 받아 Service 호출
    // Service에서 처리한 내용을 View로 넘김

    private final RecipeService recipeService;

    /* 모든 레시피 조회 */
    @GetMapping("/recipes") 
    public String getRecipes(Model model) {
        // ** 페이징 처리 필요
        List<RecipeListViewResponse> recipes  = recipeService.findAll().stream()
                .map(RecipeListViewResponse::new)
                .toList();
        model.addAttribute("recipes", recipes); // 레시피 리스트 저장

        return "recipe/recipeList";
    }

    /* id 로 레시피 조회 */
    @GetMapping("/recipes/id={id}")
    public String getRecipe(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", new RecipeViewResponse(recipe));
        return "recipe/recipe";
    }

    /* name 으로 레시피 조회 */
    @GetMapping("/recipes/rcp_nm={rcp_nm}")
    public String getRecipeName(@PathVariable String rcp_nm, Model model) {
        Recipe recipe = recipeService.findByName(rcp_nm);
        model.addAttribute("recipe", new RecipeViewResponse(recipe));
        return "recipe/recipe";
    }


    /* 레시피 생성, 수정 */
    @GetMapping("/new-recipe") 
    // id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑(id는 없을 수도 있음)
    public String newRecipe(@RequestParam(required = false) Long id, Model model) throws Exception {
        if (id == null) { // id가 없으면 생성
            model.addAttribute("recipe", new RecipeViewResponse());
        } else { // id가 있으면 수정
            Recipe recipe = recipeService.findById(id);
            model.addAttribute("recipe", new RecipeViewResponse(recipe));
        }

        return "recipe/newRecipe";
    }





}
