package doubleni.mealrecipe.service;

import doubleni.mealrecipe.model.Recipe;

import java.util.List;

public interface RecipeService {

    // 레시피 조회
    public List<Recipe> recipeSelect();
    public List<Recipe> recipeSelectAll();

    // 레시피 검색
    public Recipe recipeSearch(Recipe recipe);

    // 레시피 저장
    public Recipe recipeSave(Recipe recipe);

    // 레시피 삭제
    public Recipe recipeDelete();

    // 레시피 추천
    public List<Recipe> recipeRecommend(Recipe recipe);

    // 레시피 평가
    public Recipe recipeEvaluate();

}
