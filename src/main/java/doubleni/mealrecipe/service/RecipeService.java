package doubleni.mealrecipe.service;

import doubleni.mealrecipe.Repository.RecipeRepository;
import doubleni.mealrecipe.model.dto.AddRecipeRequest;
import doubleni.mealrecipe.model.dto.Recipe;
import doubleni.mealrecipe.model.dto.UpdateRecipeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    // 조회 -----------------------------------------

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }


    public Recipe findById(String rcp_seq) {
        return recipeRepository.findById(rcp_seq)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + rcp_seq));}


    // 생성

    public Recipe save(AddRecipeRequest request) {
        return recipeRepository.save(request.toEntity());
        // addRecipeRequest 클래스에 저장된 값들을 Recipe 데이터베이스에 저장
    }

    // 삭제

    public void deleteRecipe(String rcp_seq) {
        recipeRepository.deleteById(rcp_seq);
    }

    // 수정

    @Transactional
    public Recipe update(String rcp_seq, UpdateRecipeRequest request) {
        Recipe recipe = recipeRepository.findById(rcp_seq)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + rcp_seq));

//        recipe.update(request.getRcp_nm(), request.getRcp_way2(), request.getRcp_pat2(),
//                request.getInfo_wgt(), request.getHash_tag(), request.getAtt_file_no_main(), request.getAtt_file_no_mk(),
//                request.getRcp_parts_dtls(), request.getManual01(), request.getManual_img01());
        recipe.update(recipe.getRcp_seq(), request.getRcp_nm(), request.getRcp_way2(), request.getRcp_pat2(),
                request.getManual01(), request.getManual_img01());



        return recipe;
    }


}
