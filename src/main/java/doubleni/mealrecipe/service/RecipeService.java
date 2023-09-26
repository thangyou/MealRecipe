package doubleni.mealrecipe.service;

import doubleni.mealrecipe.model.vo.RecipeVO;
import doubleni.mealrecipe.model.dto.AddRecipeRequest;
import doubleni.mealrecipe.model.dto.Recipe;
import doubleni.mealrecipe.model.dto.UpdateRecipeRequest;
import doubleni.mealrecipe.repository.RecipeRepository;
import doubleni.mealrecipe.repository.RecipeVoRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeVoRepository recipeVoRepository;

    // 조회 -----------------------------------------

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }


//    public Recipe findById(String rcp_seq) {
//        return recipeRepository.findById(rcp_seq)
//                .orElseThrow(() -> new IllegalArgumentException("not found : " + rcp_seq));}
//
//
//    // 생성
//
//    public Recipe save(AddRecipeRequest request) {
//        return recipeRepository.save(request.toEntity());
//        // addRecipeRequest 클래스에 저장된 값들을 Recipe 데이터베이스에 저장
//    }
//
//    // 삭제
//
//    public void deleteRecipe(String rcp_seq) {
//        recipeRepository.deleteById(rcp_seq);
//    }
//
//    // 수정
//
//    @Transactional
//    public Recipe update(String rcp_seq, UpdateRecipeRequest request) {
//        Recipe recipe = recipeRepository.findById(rcp_seq)
//                .orElseThrow(() -> new IllegalArgumentException("not found : " + rcp_seq));
//
////        recipe.update(request.getRcp_nm(), request.getRcp_way2(), request.getRcp_pat2(),
////                request.getInfo_wgt(), request.getHash_tag(), request.getAtt_file_no_main(), request.getAtt_file_no_mk(),
////                request.getRcp_parts_dtls(), request.getManual01(), request.getManual_img01());
//        recipe.update(recipe.getRcp_seq(), request.getRcp_nm(), request.getRcp_way2(), request.getRcp_pat2(),
//                request.getManual01(), request.getManual_img01());
//
//        return recipe;
//    }
public Recipe findById(long id) {
    return recipeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found : " + id));}


    // 생성

    public Recipe save(AddRecipeRequest request) {
        return recipeRepository.save(request.toEntity());
        // addRecipeRequest 클래스에 저장된 값들을 Recipe 데이터베이스에 저장
    }

    // 삭제

    public void deleteRecipe(long id) {
        recipeRepository.deleteById(id);
    }

    // 수정

    @Transactional
    public Recipe update(long id, UpdateRecipeRequest request) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

//        recipe.update(request.getRcp_nm(), request.getRcp_way2(), request.getRcp_pat2(),
//                request.getInfo_wgt(), request.getHash_tag(), request.getAtt_file_no_main(), request.getAtt_file_no_mk(),
//                request.getRcp_parts_dtls(), request.getManual01(), request.getManual_img01());
        recipe.update(recipe.getRcp_seq(), request.getRcp_nm(), request.getRcp_way2(), request.getRcp_pat2(), request.getManual01(), request.getManual_img01());

        return recipe;

    }


    // TEST
    @Transactional
    public int recipeJsonInsert()
    {
        System.out.println("recipeJsonInsert()");

        int cnt = 0;
//        FileInputStream fis = null;
        Reader fis = null;
        try
        {
//            fis = new FileInputStream(new File("output.json"));
//            String jsonString = IOUtils.toString(fis);
//            JSONObject jsonObj = new JSONObject((JSONObject)new JSONParser().parse(jsonString));

//            JSONParser parser = new JSONParser();
//            fis= new FileReader("C:\\json\\output.json");
//            JSONObject jsonObj = (JSONObject) parser.parse(fis);

            ClassPathResource resource = new ClassPathResource("json/recipe.json");
            JSONObject jsonObj = (JSONObject) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8")); //json-simple

            System.out.println(jsonObj.get("RCP_NM"));
            JSONArray rows = (JSONArray)jsonObj.get("row");
            System.out.println("row.size >>> : " + rows.size());
            for(int i=0; i<rows.size(); i++)
            {
                HashMap<String, Object> currentRow = (HashMap<String, Object>)rows.get(i);

                RecipeVO rvo = new RecipeVO();

                rvo.setRcp_seq((String)currentRow.get("RCP_SEQ"));
                rvo.setRcp_nm((String)currentRow.get("RCP_NM"));
                rvo.setRcp_way2((String)currentRow.get("RCP_WAY2"));
                rvo.setRcp_pat2((String)currentRow.get("RCP_PAT2"));
                rvo.setInfo_wgt((String)currentRow.get("INFO_WGT"));
                rvo.setInfo_eng((String)currentRow.get("INFO_ENG"));
                rvo.setInfo_car((String)currentRow.get("INFO_CAR"));
                rvo.setInfo_pro((String)currentRow.get("INFO_PRO"));
                rvo.setInfo_fat((String)currentRow.get("INFO_FAT"));
                rvo.setInfo_na((String)currentRow.get("INFO_NA"));
                rvo.setHash_tag((String)currentRow.get("HASH_TAG"));
                rvo.setAtt_file_no_main((String)currentRow.get("ATT_FILE_NO_MAIN"));
                rvo.setAtt_file_no_mk((String)currentRow.get("ATT_FILE_NO_MK"));
                rvo.setRcp_parts_dtls((String)currentRow.get("RCP_PARTS_DTLS"));
                rvo.setManual01((String)currentRow.get("MANUAL01"));
                rvo.setManual_img01((String)currentRow.get("MANUAL_IMG01"));
                rvo.setManual02((String)currentRow.get("MANUAL02"));
                rvo.setManual_img02((String)currentRow.get("MANUAL_IMG02"));
                rvo.setManual03((String)currentRow.get("MANUAL03"));
                rvo.setManual_img03((String)currentRow.get("MANUAL_IMG03"));
                rvo.setManual04((String)currentRow.get("MANUAL04"));
                rvo.setManual_img04((String)currentRow.get("MANUAL_IMG04"));
                rvo.setManual05((String)currentRow.get("MANUAL05"));
                rvo.setManual_img05((String)currentRow.get("MANUAL_IMG05"));
                rvo.setManual06((String)currentRow.get("MANUAL06"));
                rvo.setManual_img06((String)currentRow.get("MANUAL_IMG06"));
                rvo.setManual07((String)currentRow.get("MANUAL07"));
                rvo.setManual_img07((String)currentRow.get("MANUAL_IMG07"));
                rvo.setManual08((String)currentRow.get("MANUAL08"));
                rvo.setManual_img08((String)currentRow.get("MANUAL_IMG08"));
                rvo.setManual09((String)currentRow.get("MANUAL09"));
                rvo.setManual_img09((String)currentRow.get("MANUAL_IMG09"));
                rvo.setManual10((String)currentRow.get("MANUAL10"));
                rvo.setManual_img10((String)currentRow.get("MANUAL_IMG10"));
                rvo.setManual11((String)currentRow.get("MANUAL11"));
                rvo.setManual_img11((String)currentRow.get("MANUAL_IMG11"));
                rvo.setManual12((String)currentRow.get("MANUAL12"));
                rvo.setManual_img12((String)currentRow.get("MANUAL_IMG12"));
                rvo.setManual13((String)currentRow.get("MANUAL13"));
                rvo.setManual_img13((String)currentRow.get("MANUAL_IMG13"));
                rvo.setManual14((String)currentRow.get("MANUAL14"));
                rvo.setManual_img14((String)currentRow.get("MANUAL_IMG14"));
                rvo.setManual15((String)currentRow.get("MANUAL15"));
                rvo.setManual_img15((String)currentRow.get("MANUAL_IMG15"));
                rvo.setManual16((String)currentRow.get("MANUAL16"));
                rvo.setManual_img16((String)currentRow.get("MANUAL_IMG16"));
                rvo.setManual17((String)currentRow.get("MANUAL17"));
                rvo.setManual_img17((String)currentRow.get("MANUAL_IMG17"));
                rvo.setManual18((String)currentRow.get("MANUAL18"));
                rvo.setManual_img18((String)currentRow.get("MANUAL_IMG18"));
                rvo.setManual19((String)currentRow.get("MANUAL19"));
                rvo.setManual_img19((String)currentRow.get("MANUAL_IMG19"));
                rvo.setManual20((String)currentRow.get("MANUAL20"));
                rvo.setManual_img20((String)currentRow.get("MANUAL_IMG20"));
                rvo.setManual20((String)currentRow.get("RCP_NA_TIP"));
                rvo.setManual_img20((String)currentRow.get("RCP_NA_TIP"));

//                cnt += recipeRepository.recipeJsonInsert(rvo);

                // TEST
                recipeVoRepository.save(rvo);
                if (rvo.isNew()) {
                    cnt += 1;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("error >>> : " + e);
        }
        finally
        {
            try {
                fis.close();
                fis = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cnt;
    }
}
