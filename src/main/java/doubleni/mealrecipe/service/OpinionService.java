package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.DTO.GetRecipeIdRes;
import doubleni.mealrecipe.model.opinion.*;
import doubleni.mealrecipe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.DATABASE_ERROR;
import static doubleni.mealrecipe.config.exception.BaseResponseStatus.SAVE_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
public class OpinionService {
    private final OpinionRepository opinionRepository;
    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;
    private final GoToFoodRepository goToFoodRepository;
    private final AllergyRepository allergyRepository;
    private final AllergySetRepository allergySetRepository;

    //저장
    public void saveOpinion(OpinionReq opinionReq, Long id) throws BaseException {

        List<String> foodList = opinionReq.getFoodList();
        List<String> allergyList = opinionReq.getAllergyList();

        try {
            //좋아하는 음식 리스트 저장
            for (int i = 0; i< foodList.size(); i++) {
                Long recipeId = recipeRepository.findByRcpNm(foodList.get(i)).get().getRcpId();

                GoToFood goToFood = new GoToFood();
                goToFood.setFoodId(foodRepository.findByRecipeId(recipeId).getFoodId());
                goToFood.setId(id);

                goToFoodRepository.save(goToFood);
            }

            //알레르기 목록 저장
            for (int i = 0; i<allergyList.size(); i++){
                Long allergyId = allergyRepository.findByAllergyName(allergyList.get(i)).getAllergyId();

                AllergySet allergySet = new AllergySet();
                allergySet.setId(id);
                allergySet.setAllergyId(allergyId);

                allergySetRepository.save(allergySet);
            }
        } catch (Exception exception){
            throw new BaseException(SAVE_ERROR);
        }
    }

    //좋아하는 음식 조회
    public List<GetRecipeIdRes> gotofoodList() throws BaseException {
        List<GetRecipeIdRes> getRecipeIdRes = new ArrayList<GetRecipeIdRes>();

        List<Food> foodList = foodRepository.findAll();

        try {
            if(foodList != null) {
                for (int i = 0 ; i < foodList.size(); i++){
                    getRecipeIdRes.add(recipeRepository.findRecipeByRcpId(foodList.get(i).getRecipeId()));
                }
            }
            return getRecipeIdRes;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //알러지 정보 전달
    public List<Allergy> allergyList() throws BaseException {
        List<Allergy> allergyList = allergyRepository.findAll();

        try {
            if(allergyList != null){
                return allergyList;
            }
            return null;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
