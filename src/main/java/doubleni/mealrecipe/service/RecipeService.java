package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.DTO.GetRecipeIdRes;
import doubleni.mealrecipe.model.DTO.RecipeResponse;
import doubleni.mealrecipe.model.Recipe;
import doubleni.mealrecipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    /* json 파일 읽기 */
    public void read() throws BaseException{
        JSONParser parser = new JSONParser();

        try{
            //json 파일 이름
//            String fileName = "recipe.json";
            String fileName = "recipes.json";

            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\json\\"+fileName;
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(projectPath));

            JSONArray recipeList = (JSONArray)jsonObject.get("row");
            for (int i=0; i<recipeList.size(); i++){
                //recipe 객체 생성
                Recipe recipe = new Recipe();
                JSONObject result = (JSONObject) recipeList.get(i);

                Optional<Recipe> skipRecipe = recipeRepository.findByRcpNm((String) result.get("RCP_NM") );

                //이미 들어가 있는 데이터는 제외
                if(skipRecipe.isEmpty()){

                    //레시피 제목
                    String rcp_nm = (String) result.get("RCP_NM");
                    recipe.setRcpNm(rcp_nm);

                    // 일련 번호
                    String rcp_seq=(String) result.get("RCP_SEQ");
                    recipe.setRcpSeq(rcp_seq);

                    // 조리 방법
                    String rcp_way2=(String) result.get("RCP_WAY2");
                    recipe.setRcpWay2(rcp_way2);

                    // 요리 종류
                    String rcp_pat2=(String) result.get("RCP_PAT2");
                    recipe.setRcpPat2(rcp_pat2);

                    // 중량(1인분)
                    String info_wgt=(String) result.get("INFO_WGT");
                    recipe.setInfoWgt(info_wgt);

                    // 열량
                    String info_eng=(String) result.get("INFO_ENG");
                    recipe.setInfoEng(info_eng);

                    // 탄수화물
                    String info_car=(String) result.get("INFO_CAR");
                    recipe.setInfoCar(info_car);

                    // 단백질
                    String info_pro=(String) result.get("INFO_PRO");
                    recipe.setInfoPro(info_pro);

                    // 지방
                    String info_fat=(String) result.get("INFO_FAT");
                    recipe.setInfoFat(info_fat);

                    // 나트륨
                    String info_na=(String) result.get("INFO_NA");
                    recipe.setInfoNa(info_na);

                    // 해시태그
                    String hash_tag=(String) result.get("HASH_TAG");
                    recipe.setInfoNa(hash_tag);

                    // 이미지 경로(소)
                    String att_file_no_main=(String) result.get("ATT_FILE_NO_MAIN");
                    recipe.setAttFileNoMain(att_file_no_main);

                    // 이미지 경로(대)
                    String att_file_no_mk=(String) result.get("ATT_FILE_NO_MK");
                    recipe.setAttFileNoMain(att_file_no_mk);

                    // 재료 정보
                    List<String> rcp_parts_dtls = (List<String>) result.get("RCP_PARTS_DTLS");
                    recipe.setRcpPartsDtls(rcp_parts_dtls);

                    // 레시피 설명
                    String manual01=(String) result.get("MANUAL_01");
                    recipe.setAttFileNoMain(manual01);

                    // 레시피 이미지
                    String manual_img01=(String) result.get("MANUAL_IMG01");
                    recipe.setAttFileNoMain(manual_img01);

                    // 저감 조리법
                    String rcp_na_tip=(String) result.get("RCP_NA_TIP");
                    recipe.setAttFileNoMain(rcp_na_tip);

                    System.out.println("rcpSeq = " + rcp_seq);
                    System.out.println("rcpNm = " + rcp_nm);
                    System.out.println("rcpWay2 = " + rcp_way2);
                    System.out.println("rcpPat2 = " + rcp_pat2);
                    System.out.println("manual01 = " + manual01);
                    System.out.println("manualImg01 = " + manual_img01);
                    System.out.println("RCP_NA_TIP = " + rcp_na_tip);
                    System.out.println("RCP_PARTS_DTLS = " + rcp_parts_dtls.toString());


                    recipeRepository.save(recipe);

//                    String manual02;
//                    String manual_img02;
//                    String manual03;
//                    String manual_img03;
//                    String manual04;
//                    String manual_img04;
//                    String manual05;
//                    String manual_img05;
//                    String manual06;
//                    String manual_img06;
//                    String manual07;
//                    String manual_img07;
//                    String manual08;
//                    String manual_img08;
//                    String manual09;
//                    String manual_img09;
//                    String manual10;
//                    String manual_img10;
//                    String manual11;
//                    String manual_img11;
//                    String manual12;
//                    String manual_img12;
//                    String manual13;
//                    String manual_img13;
//                    String manual14;
//                    String manual_img14;
//                    String manual15;
//                    String manual_img15;
//                    String manual16;
//                    String manual_img16;
//                    String manual17;
//                    String manual_img17;
//                    String manual18;
//                    String manual_img18;
//                    String manual19;
//                    String manual_img19;
//                    String manual20;
//                    String manual_img20;


                }
            }

        } catch (Exception exception){
            throw new BaseException(JSON_ERROR);
        }

    }

    /* 레시피 id로 레시피 조회 */
    public GetRecipeIdRes getRecipeIdRes(Long rcpId) throws BaseException {
        try{
            Optional<Recipe> recipeOptional = recipeRepository.findByRcpId(rcpId);
            if (recipeOptional.isPresent()){
                Recipe recipe = recipeOptional.get();

                GetRecipeIdRes getRecipeIdRes = new GetRecipeIdRes();
                getRecipeIdRes.setRcpId(recipe.getRcpId());
                getRecipeIdRes.setRcpSeq(recipe.getRcpSeq());
                getRecipeIdRes.setRcpNm(recipe.getRcpNm());
                getRecipeIdRes.setRcpWay2(recipe.getRcpWay2());
                getRecipeIdRes.setRcpPat2(recipe.getRcpPat2());
                getRecipeIdRes.setInfoWgt(recipe.getInfoWgt());
                getRecipeIdRes.setInfoEng(recipe.getInfoEng());
                getRecipeIdRes.setInfoCar(recipe.getInfoCar());
                getRecipeIdRes.setInfoPro(recipe.getInfoPro());
                getRecipeIdRes.setInfoFat(recipe.getInfoFat());
                getRecipeIdRes.setInfoNa(recipe.getInfoNa());
                getRecipeIdRes.setHashTag(recipe.getHashTag());
                getRecipeIdRes.setAttFileNoMain(recipe.getAttFileNoMain());
                getRecipeIdRes.setAttFileNoMk(recipe.getAttFileNoMk());

                /* 재료 */
                getRecipeIdRes.setRcpPartsDtls(new ArrayList<>(recipe.getRcpPartsDtls()));

                getRecipeIdRes.setManual01(recipe.getManual01());
                getRecipeIdRes.setManualImg01(recipe.getManualImg01());
//                getRecipeIdRes.setManual02(recipe.getManual02());
//                getRecipeIdRes.setManualImg02(recipe.getManualImg02());
//                getRecipeIdRes.setManual03(recipe.getManual03());
//                getRecipeIdRes.setManualImg03(recipe.getManualImg03());
//                getRecipeIdRes.setManual04(recipe.getManual04());
//                getRecipeIdRes.setManualImg04(recipe.getManualImg04());
//                getRecipeIdRes.setManual05(recipe.getManual05());
//                getRecipeIdRes.setManualImg05(recipe.getManualImg05());
//                getRecipeIdRes.setManual06(recipe.getManual06());
//                getRecipeIdRes.setManualImg06(recipe.getManualImg06());
//                getRecipeIdRes.setManual07(recipe.getManual07());
//                getRecipeIdRes.setManualImg07(recipe.getManualImg07());
//                getRecipeIdRes.setManual08(recipe.getManual08());
//                getRecipeIdRes.setManualImg08(recipe.getManualImg08());
//                getRecipeIdRes.setManual09(recipe.getManual09());
//                getRecipeIdRes.setManualImg09(recipe.getManualImg09());
//                getRecipeIdRes.setManual10(recipe.getManual10());
//                getRecipeIdRes.setManualImg10(recipe.getManualImg10());
//                getRecipeIdRes.setManual11(recipe.getManual11());
//                getRecipeIdRes.setManualImg11(recipe.getManualImg11());
//                getRecipeIdRes.setManual12(recipe.getManual12());
//                getRecipeIdRes.setManualImg12(recipe.getManualImg12());
//                getRecipeIdRes.setManual13(recipe.getManual13());
//                getRecipeIdRes.setManualImg13(recipe.getManualImg13());
//                getRecipeIdRes.setManual14(recipe.getManual14());
//                getRecipeIdRes.setManualImg14(recipe.getManualImg14());
//                getRecipeIdRes.setManual15(recipe.getManual15());
//                getRecipeIdRes.setManualImg15(recipe.getManualImg15());
//                getRecipeIdRes.setManual16(recipe.getManual16());
//                getRecipeIdRes.setManualImg16(recipe.getManualImg16());
//                getRecipeIdRes.setManual17(recipe.getManual17());
//                getRecipeIdRes.setManualImg17(recipe.getManualImg17());
//                getRecipeIdRes.setManual18(recipe.getManual18());
//                getRecipeIdRes.setManualImg18(recipe.getManualImg18());
//                getRecipeIdRes.setManual19(recipe.getManual19());
//                getRecipeIdRes.setManualImg19(recipe.getManualImg19());
//                getRecipeIdRes.setManual20(recipe.getManual20());
//                getRecipeIdRes.setManualImg20(recipe.getManualImg20());
                getRecipeIdRes.setRcpNaTip(recipe.getRcpNaTip());

                return getRecipeIdRes;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
        return null;
    }

    // ====================================================================

    /* keyword로 해당하는 레시피 조회 */
    public List<RecipeResponse> RecipeByKeyword(String keyword) {
        List<RecipeResponse> recipeResponses = recipeRepository.searchByKeyword(keyword)
                .stream()
                .map(RecipeResponse::new)
                .collect(Collectors.toList());

        if (recipeResponses.isEmpty()) {
            throw new IllegalStateException();
        }
        return recipeResponses;
    }

    /* 전체 레시피 목록 */
    public List<RecipeResponse> getAllRecipes() {
        List<RecipeResponse> recipeResponses =
                recipeRepository.findAll()
                        .stream()
                        .map(RecipeResponse::new)
                        .toList();

        if (recipeResponses.isEmpty()) {
            throw new IllegalStateException();
        }
        return  recipeResponses;
    }


    // ====================================================================

    /* 키워드로 레시피 검색 */



    // ====================================================================





}

