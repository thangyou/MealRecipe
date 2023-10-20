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
import java.util.ArrayList;
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
                    String rcp_seq = (String) result.get("RCP_SEQ");
                    recipe.setRcpSeq(rcp_seq);
                    String info_pro = (String) result.get("INFO_PRO");
                    recipe.setInfoPro(Integer.parseInt(info_pro));
                    String info_fat = (String) result.get("INFO_FAT");
                    recipe.setInfoFat(Integer.parseInt(info_fat));
                    String ingredient=(String) result.get("RCP_PARTS_DTLS");
                    recipe.setIngredient(ingredient);

                    System.out.println("==== API TEST ====");
                    System.out.println("레시피 : " + rcp_nm);
                    System.out.println("일련번호 : " + rcp_seq);
                    System.out.println("단백질 : " + info_pro);
                    System.out.println("지방 : " + info_pro);
                    System.out.println("재료 : " + ingredient);
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
                getRecipeIdRes.setRcpSeq(recipe.getRcpSeq());
                getRecipeIdRes.setInfoPro(recipe.getInfoPro());
                getRecipeIdRes.setInfoFat(recipe.getInfoFat());
                getRecipeIdRes.setIngredient(recipe.getIngredient());
                return getRecipeIdRes;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
        return null;
    }

    /* 정렬 */
    public List<GetApiRes> getApiByOrderByInfoProDesc() { // 고단백
        List<GetApiRes> getRecipeRes =
                apiRepository.findAllByOrderByInfoProDesc()
                        .stream()
                        .map(GetApiRes::new)
                        .toList();

        if (getRecipeRes.isEmpty()) {
            throw new IllegalStateException();
        }
        return getRecipeRes;
    }

    public List<GetApiRes> getApiByOrderByInfoFatAsc() { // 저지방
        List<GetApiRes> getRecipeRes =
                apiRepository.findAllByOrderByInfoFatAsc()
                        .stream()
                        .map(GetApiRes::new)
                        .toList();

        if (getRecipeRes.isEmpty()) {
            throw new IllegalStateException();
        }
        return getRecipeRes;
    }


    // ===============================================================================================

    /* 레시피(리스트) 출력 */
//    public List<GetApiRes> searchApiByKeyword(String keyword) {
//        List<GetApiRes> apiResList = apiRepository.findByRcpNmContainingOrIngredientContaining(keyword)
//                .stream()
//                .map(GetApiRes::new)
//                .toList();
//
//        if(apiResList.isEmpty()) {
//            throw new IllegalStateException();
//        }
//        return apiResList;
//    }

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
        /**
         * <문제점>
         *     정확한 명칭 "부추 콩가루 찜" 검색 해야 재료 출력
         * </문제점>
         * <해결방안>
         *      findByRcpNm().isEmpty()면, -> findByRcpNmContaining 사용?
         * </해결방안>
         */
        Api recipe = apiRepository.findByRcpNm(rcpNm)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다."));
        GetApiRes response = new GetApiRes(recipe);
//        return Collections.singletonList(response.getIngredient()); // 문자열 출력

        // 문자열에서 "\n"과 ","을 기준으로 분할하여 리스트로 저장
        String[] ingredients = response.getIngredient().split("[\\n,]");
        List<String> ingredients_list = new ArrayList<>();

        // 빈 문자열이나 공백 문자열을 제외하고 리스트에 추가
        for (String ingredient : ingredients) {
            ingredient = ingredient.trim();
            if (!ingredient.isEmpty()) {
                ingredients_list.add(ingredient);
            }
        }
        return ingredients_list;
    }


}
