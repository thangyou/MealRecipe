package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
//import doubleni.mealrecipe.model.DTO.GetRecipeOrderRes;
import doubleni.mealrecipe.model.DTO.GetRecipeRes;
import doubleni.mealrecipe.model.DTO.GetReviewRes;
import doubleni.mealrecipe.model.Recipe;
import doubleni.mealrecipe.model.Review;
import doubleni.mealrecipe.repository.RecipeRepository;
import doubleni.mealrecipe.repository.RecommendRepository;
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
    private final RecommendRepository recommendRepository;

    /* json 파일 읽기 */
    public void read() throws BaseException{
        JSONParser parser = new JSONParser();

        try{
            //json 파일 이름
            String fileName = "recipe.json";

            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\json\\"+fileName;
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(projectPath));

            JSONArray recipeList = (JSONArray)jsonObject.get("row");
            System.out.println("recipeList.size() : " + recipeList.size());
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
                    recipe.setHashTag(hash_tag);

                    // 이미지 경로(소)
                    String att_file_no_main=(String) result.get("ATT_FILE_NO_MAIN");
                    recipe.setAttFileNoMain(att_file_no_main);

                    // 이미지 경로(대)
                    String att_file_no_mk=(String) result.get("ATT_FILE_NO_MK");
                    recipe.setAttFileNoMk(att_file_no_mk);

                    // 재료 정보
//                    List<String> rcp_parts_dtls = (List<String>) result.get("RCP_PARTS_DTLS");
                    String rcpPartsDtls = (String) result.get("RCP_PARTS_DTLS");
                    recipe.setRcpPartsDtls(rcpPartsDtls);

                    // 레시피 설명, 레시피 이미지
                    String manual01=(String) result.get("MANUAL01");
                    recipe.setManual01(manual01);
                    String manual_img01=(String) result.get("MANUAL_IMG01");
                    recipe.setManualImg01(manual_img01);

                    String manual02=(String) result.get("MANUAL02");
                    recipe.setManual02(manual02);
                    String manual_img02=(String) result.get("MANUAL_IMG02");
                    recipe.setManualImg02(manual_img02);

                    String manual03=(String) result.get("MANUAL03");
                    recipe.setManual03(manual03);
                    String manual_img03=(String) result.get("MANUAL_IMG03");
                    recipe.setManualImg03(manual_img03);

                    String manual04=(String) result.get("MANUAL04");
                    recipe.setManual04(manual04);
                    String manual_img04=(String) result.get("MANUAL_IMG04");
                    recipe.setManualImg04(manual_img04);

                    String manual05=(String) result.get("MANUAL05");
                    recipe.setManual05(manual05);
                    String manual_img05=(String) result.get("MANUAL_IMG05");
                    recipe.setManualImg05(manual_img05);

                    String manual06=(String) result.get("MANUAL06");
                    recipe.setManual06(manual06);
                    String manual_img06=(String) result.get("MANUAL_IMG06");
                    recipe.setManualImg06(manual_img06);

                    String manual07=(String) result.get("MANUAL07");
                    recipe.setManual07(manual07);
                    String manual_img07=(String) result.get("MANUAL_IMG07");
                    recipe.setManualImg07(manual_img07);

                    String manual08=(String) result.get("MANUAL08");
                    recipe.setManual08(manual08);
                    String manual_img08=(String) result.get("MANUAL_IMG08");
                    recipe.setManualImg08(manual_img08);

                    String manual09=(String) result.get("MANUAL09");
                    recipe.setManual09(manual09);
                    String manual_img09=(String) result.get("MANUAL_IMG09");
                    recipe.setManualImg09(manual_img09);

                    String manual10=(String) result.get("MANUAL10");
                    recipe.setManual10(manual10);
                    String manual_img10=(String) result.get("MANUAL_IMG10");
                    recipe.setManualImg10(manual_img10);

                    String manual11=(String) result.get("MANUAL11");
                    recipe.setManual11(manual11);
                    String manual_img11=(String) result.get("MANUAL_IMG11");
                    recipe.setManualImg11(manual_img11);

                    String manual12=(String) result.get("MANUAL12");
                    recipe.setManual12(manual12);
                    String manual_img12=(String) result.get("MANUAL_IMG12");
                    recipe.setManualImg12(manual_img12);

                    String manual13=(String) result.get("MANUAL13");
                    recipe.setManual13(manual13);
                    String manual_img13=(String) result.get("MANUAL_IMG13");
                    recipe.setManualImg13(manual_img13);

                    String manual14=(String) result.get("MANUAL14");
                    recipe.setManual14(manual14);
                    String manual_img14=(String) result.get("MANUAL_IMG14");
                    recipe.setManualImg14(manual_img14);

                    String manual15=(String) result.get("MANUAL15");
                    recipe.setManual15(manual15);
                    String manual_img15=(String) result.get("MANUAL_IMG15");
                    recipe.setManualImg15(manual_img15);

                    String manual16=(String) result.get("MANUAL16");
                    recipe.setManual16(manual16);
                    String manual_img16=(String) result.get("MANUAL_IMG16");
                    recipe.setManualImg16(manual_img16);

                    String manual17=(String) result.get("MANUAL17");
                    recipe.setManual17(manual17);
                    String manual_img17=(String) result.get("MANUAL_IMG17");
                    recipe.setManualImg17(manual_img17);

                    String manual18=(String) result.get("MANUAL18");
                    recipe.setManual18(manual18);
                    String manual_img18=(String) result.get("MANUAL_IMG18");
                    recipe.setManualImg18(manual_img18);

                    String manual19=(String) result.get("MANUAL19");
                    recipe.setManual19(manual19);
                    String manual_img19=(String) result.get("MANUAL_IMG19");
                    recipe.setManualImg19(manual_img19);

                    String manual20=(String) result.get("MANUAL20");
                    recipe.setManual20(manual20);
                    String manual_img20=(String) result.get("MANUAL_IMG20");
                    recipe.setManualImg20(manual_img20);

                    // 저감 조리법
                    String rcp_na_tip=(String) result.get("RCP_NA_TIP");
                    recipe.setRcpNaTip(rcp_na_tip);

                    recipeRepository.save(recipe);
                }
            }

        } catch (Exception exception){
            throw new BaseException(JSON_ERROR);
        }

    }

    /* 전체 레시피 조회 */
    public List<GetRecipeRes> getAllRecipes() throws BaseException {
        List<Recipe> recipes = recipeRepository.findAll();
        List<GetRecipeRes> getRecipeRes = recipes.stream()
                .map(GetRecipeRes::new)
                .collect(Collectors.toList());

//        List<GetRecipeRes> getRecipeRes =
//                recipeRepository.findAll()
//                        .stream()
//                        .map(GetRecipeRes::new)
//                        .toList();

        if (getRecipeRes.isEmpty()) {
            throw new BaseException(DATABASE_ERROR);
        }
        return getRecipeRes;
    }

    /* 레시피 id로 레시피 조회 */
    public GetRecipeRes getRecipeById(Long rcpId) throws BaseException {
        try{
            Optional<Recipe> recipeOptional = recipeRepository.findByRcpId(rcpId);
            if (recipeOptional.isPresent()){
                Recipe recipe = recipeOptional.get();

                GetRecipeRes getRecipeIdRes = new GetRecipeRes();
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
//                getRecipeIdRes.setRcpPartsDtls(new ArrayList<>(recipe.getRcpPartsDtls()));
                getRecipeIdRes.setRcpPartsDtls(recipe.getRcpPartsDtls());

                getRecipeIdRes.setManual01(recipe.getManual01());
                getRecipeIdRes.setManualImg01(recipe.getManualImg01());
                getRecipeIdRes.setManual02(recipe.getManual02());
                getRecipeIdRes.setManualImg02(recipe.getManualImg02());
                getRecipeIdRes.setManual03(recipe.getManual03());
                getRecipeIdRes.setManualImg03(recipe.getManualImg03());
                getRecipeIdRes.setManual04(recipe.getManual04());
                getRecipeIdRes.setManualImg04(recipe.getManualImg04());
                getRecipeIdRes.setManual05(recipe.getManual05());
                getRecipeIdRes.setManualImg05(recipe.getManualImg05());
                getRecipeIdRes.setManual06(recipe.getManual06());
                getRecipeIdRes.setManualImg06(recipe.getManualImg06());
                getRecipeIdRes.setManual07(recipe.getManual07());
                getRecipeIdRes.setManualImg07(recipe.getManualImg07());
                getRecipeIdRes.setManual08(recipe.getManual08());
                getRecipeIdRes.setManualImg08(recipe.getManualImg08());
                getRecipeIdRes.setManual09(recipe.getManual09());
                getRecipeIdRes.setManualImg09(recipe.getManualImg09());
                getRecipeIdRes.setManual10(recipe.getManual10());
                getRecipeIdRes.setManualImg10(recipe.getManualImg10());
                getRecipeIdRes.setManual11(recipe.getManual11());
                getRecipeIdRes.setManualImg11(recipe.getManualImg11());
                getRecipeIdRes.setManual12(recipe.getManual12());
                getRecipeIdRes.setManualImg12(recipe.getManualImg12());
                getRecipeIdRes.setManual13(recipe.getManual13());
                getRecipeIdRes.setManualImg13(recipe.getManualImg13());
                getRecipeIdRes.setManual14(recipe.getManual14());
                getRecipeIdRes.setManualImg14(recipe.getManualImg14());
                getRecipeIdRes.setManual15(recipe.getManual15());
                getRecipeIdRes.setManualImg15(recipe.getManualImg15());
                getRecipeIdRes.setManual16(recipe.getManual16());
                getRecipeIdRes.setManualImg16(recipe.getManualImg16());
                getRecipeIdRes.setManual17(recipe.getManual17());
                getRecipeIdRes.setManualImg17(recipe.getManualImg17());
                getRecipeIdRes.setManual18(recipe.getManual18());
                getRecipeIdRes.setManualImg18(recipe.getManualImg18());
                getRecipeIdRes.setManual19(recipe.getManual19());
                getRecipeIdRes.setManualImg19(recipe.getManualImg19());
                getRecipeIdRes.setManual20(recipe.getManual20());
                getRecipeIdRes.setManualImg20(recipe.getManualImg20());
                getRecipeIdRes.setRcpNaTip(recipe.getRcpNaTip());

                // 평균 평점 계산
                double averageRating = recipe.getAverageRating();
                getRecipeIdRes.setReviewAverge(averageRating);

                return getRecipeIdRes;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
        return null;
    }

    // ====================================================================

    /* 정렬 */

//    public List<GetRecipeOrderRes> getRecipeByOrderByInfoProDesc() throws BaseException { // 고단백
//        List<GetRecipeOrderRes> getRecipeRes =
//                recipeRepository.findAllByOrderByInfoProDesc()
//                        .stream()
//                        .map(GetRecipeOrderRes::new)
//                        .toList();
//
//        if (getRecipeRes.isEmpty()) {
//            throw new BaseException(DATABASE_ERROR);
////            throw new IllegalStateException();
//        }
//        return getRecipeRes;
//    }
//
//    public List<GetRecipeOrderRes> getRecipeByOrderByInfoFatAsc() throws BaseException { // 저지방
//        List<GetRecipeOrderRes> getRecipeRes =
//                recipeRepository.findAllByOrderByInfoFatAsc()
//                        .stream()
//                        .map(GetRecipeOrderRes::new)
//                        .toList();
//
//        if (getRecipeRes.isEmpty()) {
//            throw new BaseException(DATABASE_ERROR);
////            throw new IllegalStateException();
//        }
//        return getRecipeRes;
//    }

    // ====================================================================

    /* 레시피(리스트) 출력 */
    public List<GetRecipeRes> searchRecipeByName(String keyword) throws BaseException {
        List<GetRecipeRes> getRecipeResList = recipeRepository.findByRcpNmContaining(keyword)
                .stream()
                .map(GetRecipeRes::new)
                .collect(Collectors.toList());

        if(getRecipeResList.isEmpty()) {
            throw new BaseException(DATABASE_ERROR);
//            throw new IllegalStateException();
        }
        return getRecipeResList;
    }

    public List<GetRecipeRes> searchRecipeByRcpPartsDtls(String keyword) throws BaseException {
        List<GetRecipeRes> getRecipeResList = recipeRepository.findByRcpPartsDtlsContaining(keyword)
                .stream()
                .map(GetRecipeRes::new)
                .toList();

        if(getRecipeResList.isEmpty()) {
            throw new BaseException(DATABASE_ERROR);
//            throw new IllegalStateException();
        }
        return getRecipeResList;
    }

    /* 재료 출력 */
    public List<String> searchRcpPartsDtlsByRcpNm(String rcpNm) throws BaseException {
        /**
         * <문제점>
         *     정확한 명칭 "부추 콩가루 찜" 검색 해야 재료 출력
         * </문제점>
         * <해결방안>
         *      findByRcpNm().isEmpty()면, -> findByRcpNmContaining 사용?
         * </해결방안>
         */
        Recipe recipe = recipeRepository.findByRcpNm(rcpNm)
                .orElseThrow(() -> new BaseException(DATABASE_ERROR));
        GetRecipeRes response = new GetRecipeRes(recipe);
//        return Collections.singletonList(response.getRcpPartsDtls()); // 문자열 출력

        // 문자열에서 "\n"과 ","을 기준으로 분할하여 리스트로 저장
        String[] ingredients = response.getRcpPartsDtls().split("[\\n,]");
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


    // ====================================================================


    //키워드 기반 맞춤형 레시피 추출
    public GetRecipeRes searchGetRecipeResByKeyword (String rcpNm) throws BaseException {

        try{
            //System.out.println(rcpNm);

            Optional<Recipe> recipeOptional = recommendRepository.findByRcpNm(rcpNm);


            if(recipeOptional.isPresent()){

                Recipe recipe = recipeOptional.get();

                GetRecipeRes getRecipeIdRes = new GetRecipeRes();
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
//                getRecipeIdRes.setRcpPartsDtls(new ArrayList<>(recipe.getRcpPartsDtls()));
                getRecipeIdRes.setRcpPartsDtls(recipe.getRcpPartsDtls());

                getRecipeIdRes.setManual01(recipe.getManual01());
                getRecipeIdRes.setManualImg01(recipe.getManualImg01());
                getRecipeIdRes.setManual02(recipe.getManual02());
                getRecipeIdRes.setManualImg02(recipe.getManualImg02());
                getRecipeIdRes.setManual03(recipe.getManual03());
                getRecipeIdRes.setManualImg03(recipe.getManualImg03());
                getRecipeIdRes.setManual04(recipe.getManual04());
                getRecipeIdRes.setManualImg04(recipe.getManualImg04());
                getRecipeIdRes.setManual05(recipe.getManual05());
                getRecipeIdRes.setManualImg05(recipe.getManualImg05());
                getRecipeIdRes.setManual06(recipe.getManual06());
                getRecipeIdRes.setManualImg06(recipe.getManualImg06());
                getRecipeIdRes.setManual07(recipe.getManual07());
                getRecipeIdRes.setManualImg07(recipe.getManualImg07());
                getRecipeIdRes.setManual08(recipe.getManual08());
                getRecipeIdRes.setManualImg08(recipe.getManualImg08());
                getRecipeIdRes.setManual09(recipe.getManual09());
                getRecipeIdRes.setManualImg09(recipe.getManualImg09());
                getRecipeIdRes.setManual10(recipe.getManual10());
                getRecipeIdRes.setManualImg10(recipe.getManualImg10());
                getRecipeIdRes.setManual11(recipe.getManual11());
                getRecipeIdRes.setManualImg11(recipe.getManualImg11());
                getRecipeIdRes.setManual12(recipe.getManual12());
                getRecipeIdRes.setManualImg12(recipe.getManualImg12());
                getRecipeIdRes.setManual13(recipe.getManual13());
                getRecipeIdRes.setManualImg13(recipe.getManualImg13());
                getRecipeIdRes.setManual14(recipe.getManual14());
                getRecipeIdRes.setManualImg14(recipe.getManualImg14());
                getRecipeIdRes.setManual15(recipe.getManual15());
                getRecipeIdRes.setManualImg15(recipe.getManualImg15());
                getRecipeIdRes.setManual16(recipe.getManual16());
                getRecipeIdRes.setManualImg16(recipe.getManualImg16());
                getRecipeIdRes.setManual17(recipe.getManual17());
                getRecipeIdRes.setManualImg17(recipe.getManualImg17());
                getRecipeIdRes.setManual18(recipe.getManual18());
                getRecipeIdRes.setManualImg18(recipe.getManualImg18());
                getRecipeIdRes.setManual19(recipe.getManual19());
                getRecipeIdRes.setManualImg19(recipe.getManualImg19());
                getRecipeIdRes.setManual20(recipe.getManual20());
                getRecipeIdRes.setManualImg20(recipe.getManualImg20());
                getRecipeIdRes.setRcpNaTip(recipe.getRcpNaTip());

                // 평균 평점 계산
                double averageRating = recipe.getAverageRating();
                getRecipeIdRes.setReviewAverge(averageRating);

                return getRecipeIdRes;

            }
            else {
                GetRecipeRes getRecipeIdRes = new GetRecipeRes();
                getRecipeIdRes.setRcpNm(rcpNm);

                return getRecipeIdRes;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }



    public GetRecipeRes searchGetRecipeResByRcpId (String recipeId) throws BaseException {

        try{

            Optional<Recipe> recipeOptional = recommendRepository.findByRcpSeq(recipeId);


            if(recipeOptional.isPresent()){

                Recipe recipe = recipeOptional.get();

                GetRecipeRes getRecipeIdRes = new GetRecipeRes();
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
//                getRecipeIdRes.setRcpPartsDtls(new ArrayList<>(recipe.getRcpPartsDtls()));
                getRecipeIdRes.setRcpPartsDtls(recipe.getRcpPartsDtls());

                getRecipeIdRes.setManual01(recipe.getManual01());
                getRecipeIdRes.setManualImg01(recipe.getManualImg01());
                getRecipeIdRes.setManual02(recipe.getManual02());
                getRecipeIdRes.setManualImg02(recipe.getManualImg02());
                getRecipeIdRes.setManual03(recipe.getManual03());
                getRecipeIdRes.setManualImg03(recipe.getManualImg03());
                getRecipeIdRes.setManual04(recipe.getManual04());
                getRecipeIdRes.setManualImg04(recipe.getManualImg04());
                getRecipeIdRes.setManual05(recipe.getManual05());
                getRecipeIdRes.setManualImg05(recipe.getManualImg05());
                getRecipeIdRes.setManual06(recipe.getManual06());
                getRecipeIdRes.setManualImg06(recipe.getManualImg06());
                getRecipeIdRes.setManual07(recipe.getManual07());
                getRecipeIdRes.setManualImg07(recipe.getManualImg07());
                getRecipeIdRes.setManual08(recipe.getManual08());
                getRecipeIdRes.setManualImg08(recipe.getManualImg08());
                getRecipeIdRes.setManual09(recipe.getManual09());
                getRecipeIdRes.setManualImg09(recipe.getManualImg09());
                getRecipeIdRes.setManual10(recipe.getManual10());
                getRecipeIdRes.setManualImg10(recipe.getManualImg10());
                getRecipeIdRes.setManual11(recipe.getManual11());
                getRecipeIdRes.setManualImg11(recipe.getManualImg11());
                getRecipeIdRes.setManual12(recipe.getManual12());
                getRecipeIdRes.setManualImg12(recipe.getManualImg12());
                getRecipeIdRes.setManual13(recipe.getManual13());
                getRecipeIdRes.setManualImg13(recipe.getManualImg13());
                getRecipeIdRes.setManual14(recipe.getManual14());
                getRecipeIdRes.setManualImg14(recipe.getManualImg14());
                getRecipeIdRes.setManual15(recipe.getManual15());
                getRecipeIdRes.setManualImg15(recipe.getManualImg15());
                getRecipeIdRes.setManual16(recipe.getManual16());
                getRecipeIdRes.setManualImg16(recipe.getManualImg16());
                getRecipeIdRes.setManual17(recipe.getManual17());
                getRecipeIdRes.setManualImg17(recipe.getManualImg17());
                getRecipeIdRes.setManual18(recipe.getManual18());
                getRecipeIdRes.setManualImg18(recipe.getManualImg18());
                getRecipeIdRes.setManual19(recipe.getManual19());
                getRecipeIdRes.setManualImg19(recipe.getManualImg19());
                getRecipeIdRes.setManual20(recipe.getManual20());
                getRecipeIdRes.setManualImg20(recipe.getManualImg20());
                getRecipeIdRes.setRcpNaTip(recipe.getRcpNaTip());

                // 평균 평점 계산
                double averageRating = recipe.getAverageRating();
                getRecipeIdRes.setReviewAverge(averageRating);

                return getRecipeIdRes;

            }
            else {
                GetRecipeRes getRecipeIdRes = new GetRecipeRes();
                getRecipeIdRes.setRcpSeq(recipeId.toString());

                return getRecipeIdRes;
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }



}

