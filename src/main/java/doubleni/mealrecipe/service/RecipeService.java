package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.Recipe;
import doubleni.mealrecipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.Optional;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    //json파일을 읽어보아요~
    public void read() throws BaseException{
        JSONParser parser = new JSONParser();

        try{
            //json 파일 이름
            String fileName = "recipe.json";

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
                    System.out.println(rcp_nm);
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

                    recipeRepository.save(recipe);


//                    String hash_tag; // 해시태그
//                    String att_file_no_main; // 이미지 경로(소)
//                    String att_file_no_mk; // 이미지 경로(대)
//                    String rcp_parts_dtls; // 재료 정보
//                    String manual01; // 레시피 설명
//                    String manual_img01; // 레시피 이미지
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
//                    String rcp_na_tip;



                }
            }

        } catch (Exception exception){
            throw new BaseException(JSON_ERROR);
        }

    }
}
