package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.DTO.GetApiRes;
import doubleni.mealrecipe.model.Api;
import doubleni.mealrecipe.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.DATABASE_ERROR;
import static doubleni.mealrecipe.config.exception.BaseResponseStatus.JSON_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiService {

    private final ApiRepository apiRepository;

    public void read() throws BaseException {
        JSONParser parser = new JSONParser();

        try{
            String fileName = "test.json";
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\json\\"+fileName;
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(projectPath));
            JSONArray recipeList = (JSONArray)jsonObject.get("row");

            for (int i=0; i<recipeList.size(); i++){
                Api recipe = new Api();
                JSONObject result = (JSONObject) recipeList.get(i);
                Optional<Api> skipRecipe = apiRepository.findByRcpNm((String) result.get("RCP_NM") );

                if(skipRecipe.isEmpty()){

                    String rcp_nm = (String) result.get("RCP_NM");
                    recipe.setRcpNm(rcp_nm);
                    String ingredient=(String) result.get("RCP_PARTS_DTLS");
                    recipe.setIngredient(ingredient);

                    System.out.println("==== API TEST ====");
                    System.out.println("Name : " + rcp_nm);
                    System.out.println("Ingredient : " + ingredient);
                    System.out.println("==================");

                    apiRepository.save(recipe);
                }
            }
        } catch (Exception exception){
            throw new BaseException(JSON_ERROR);
        }
    }

    public List<GetApiRes> getAllApi() {
        List<GetApiRes> getRecipeRes =
                apiRepository.findAll()
                        .stream()
                        .map(GetApiRes::new)
                        .toList();

        if (getRecipeRes.isEmpty()) {
            throw new IllegalStateException();
        }
        return getRecipeRes;
    }

    public GetApiRes getApiById(Long id) throws BaseException {
        try{
            Optional<Api> recipeOptional = apiRepository.findById(id);
            if (recipeOptional.isPresent()){
                Api recipe = recipeOptional.get();

                GetApiRes getRecipeIdRes = new GetApiRes();
                getRecipeIdRes.setId(recipe.getId());
                getRecipeIdRes.setRcpNm(recipe.getRcpNm());
                getRecipeIdRes.setIngredient(recipe.getIngredient());
                return getRecipeIdRes;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
        return null;
    }


    // ===============================================================================================

    /* 레시피(리스트) 출력 */
    // getRecipesByKeyword
    public List<GetApiRes> searchApiByName(String keyword) throws BaseException {
        List<GetApiRes> apiResList = apiRepository.findByRcpNmContaining(keyword)
                .stream()
                .map(GetApiRes::new)
                .collect(Collectors.toList());

        if(apiResList.isEmpty()) {
            throw new IllegalStateException();
        }
        return apiResList;
    }

    public List<GetApiRes> searchApiByIngredient(String ingredient) {
        List<GetApiRes> apiResList = apiRepository.findByIngredientContaining(ingredient)
                .stream()
                .map(GetApiRes::new)
                .collect(Collectors.toList());

        if(apiResList.isEmpty()) {
            throw new IllegalStateException();
        }
        return apiResList;
    }

    /* 재료 출력 */
    public List<String> searchIngredientByRcpNm(String rcpNm) {
        /*
        rcpNm으로 findByRcpNm을 수행하여, 해당하는 레시피가 존재하면 재료 출력
        아니면, findByRcpNmContaining을 수행
         */
//        try {
//            Api api = apiRepository.findByRcpNm(rcpNm)
//                    .orElse(List<Api> apiRepository.findByRcpNmContaining(rcpNm))
//        }
        Api recipe = apiRepository.findByRcpNm(rcpNm)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다."));
        GetApiRes response = new GetApiRes(recipe);
        return Collections.singletonList(response.getIngredient());
    }


}
