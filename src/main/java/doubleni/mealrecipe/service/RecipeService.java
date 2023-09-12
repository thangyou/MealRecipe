package doubleni.mealrecipe.service;

import doubleni.mealrecipe.model.Recipe;
import doubleni.mealrecipe.model.Recipe_mfds;

import java.util.List;

public interface RecipeService {

    // 레시피 조회
    public List<Recipe_mfds> recipeSelect();
    public List<Recipe_mfds> recipeSelectAll();

    // 레시피 검색
    public Recipe_mfds recipeSearch(Recipe_mfds recipe_mfds);

    // 레시피 저장
    public Recipe_mfds recipeSave(Recipe_mfds recipe_mfds);

    // 레시피 삭제
    public Recipe_mfds recipeDelete();

    // 레시피 추천
    public List<Recipe_mfds> recipeRecommend(Recipe_mfds recipe_mfds);

    // 레시피 평가
    public Recipe_mfds recipeEvaluate();

}
