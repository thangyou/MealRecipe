package doubleni.mealrecipe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import doubleni.mealrecipe.model.dto.*;
import doubleni.mealrecipe.repository.RecipeRepository;
import doubleni.mealrecipe.repository.RecipeRepositoryImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Configuration
@Service
@RequiredArgsConstructor
public class RecipeService {
    private static final String API_URL = "http://openapi.foodsafetykorea.go.kr/api/7c74eb77831147ba9e86/COOKRCP01/xml/0/1";

    private final RestTemplate restTemplate;
    private final RecipeRepository recipeRepository;
    private final RecipeRepositoryImpl repositoryImpl;

    /* 전체 레시피 조회 */
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    /* id로 레시피 조회 */
    public Recipe findById(long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }
    /*
    public Optional<Recipe> findById(long id) {
        return recipeRepository.findById(id);
        ** Optional<Recipe> recipe = recipeRepository.findById(id);
        ** Recipe findRecipe = recipe.get();
    }

     */

    /* name 으로 레시피 조회 */
//    public Recipe findByName(String rcpNm) {
//        return recipeRepository.findByName(rcpNm);
//                .orElseThrow(() -> new IllegalArgumentException("not found : " + rcpNm));
//    }
    public Recipe findByName(String rcpNm) {
        return repositoryImpl.findByName(rcpNm);
    }

    /* 레시피 추가 */
    public Recipe save(AddRecipeRequest request) {
        return recipeRepository.save(request.toEntity());
        // addRecipeRequest 클래스에 저장된 값들을 Recipe 데이터베이스에 저장
    }

    /* 레시피 삭제 */
    public void deleteRecipe(long id) { 
        recipeRepository.deleteById(id);
    }

    /* 레시피 수정 */
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

    /* Update */


    /* 레시피 생성 */
    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public void write(Recipe recipe, MultipartFile file) throws Exception{
        /* 경로를 지정 */
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        /* 식별자 . 랜덤으로 이름 만들어줌 */
        UUID uuid = UUID.randomUUID();

        /* 랜덤식별자_원래파일이름 = 저장될 파일이름 지정 */
        String fileName = uuid + "_" + file.getOriginalFilename();

        /* File을 생성 - 이름은 "name", projectPath 라는 경로에 담긴다 */
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        /* 디비에 파일 넣기 */
        recipe.setManual_img01(fileName);
        /* 저장되는 경로 */
        recipe.setManual_img01("/files/" + fileName); /* 저장된파일의이름, 저장된파일의경로 */

        /*파일 저장*/
        recipeRepository.save(recipe);
    }

    // ====================================================================

    /* 레시피 API to JSON 저장 */
    public ResponseEntity<String> fetchDataAndConvertToJson() {
        try {
            // API 요청
            String xmlResponse = restTemplate.getForObject(API_URL, String.class);

            // XML to JSON 변환 코드
            ObjectMapper xmlMapper = new XmlMapper();
            Object json = xmlMapper.readValue(xmlResponse, Object.class);
            ObjectMapper jsonResponseMapper = new ObjectMapper();
            String jsonString = jsonResponseMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

            // JSON 데이터를 Recipe 객체로 변환하여 데이터베이스에 저장
//            saveJsonDataToDatabase(jsonString);
//            return ResponseEntity.ok("Data fetched and saved to database successfully.");
            return ResponseEntity.ok(jsonString);
//            return jsonResponseMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 에러가 발생하면 null을 반환
        }

    }

    // dataService.saveJsonDataToDatabase() 와 중복
//    public void saveJsonDataToDatabase(String jsonData) {
//        try {
//            // JSON 데이터를 Recipe 객체로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            Recipe recipe = objectMapper.readValue(jsonData, Recipe.class);
//
//            // Recipe 객체를 데이터베이스에 저장
//            recipeRepository.save(recipe);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace(); 
//        }
//    }

    public ArrayList<Recipe> getOnlyRecipes() {
        Recipe recipe1 = new Recipe(3L, "일련번호1", "레시피명1", "조리방법1",
                "요리종류1", "메뉴얼1", "이미지1", LocalDateTime.now());
        Recipe recipe2 = new Recipe(3L, "일련번호1", "레시피명1", "조리방법1",
                "요리종류1", "메뉴얼1", "이미지1", LocalDateTime.now());
        Recipe recipe3 = new Recipe(3L, "일련번호1", "레시피명1", "조리방법1",
                "요리종류1", "메뉴얼1", "이미지1", LocalDateTime.now());

        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);
        return recipes;
    }

    public COOKRCP01 getAllCookBook() {
        Recipe recipe1 = new Recipe(3L, "일련번호1", "레시피명1", "조리방법1",
                "요리종류1", "메뉴얼1", "이미지1", LocalDateTime.now());
        Recipe recipe2 = new Recipe(3L, "일련번호1", "레시피명1", "조리방법1",
                "요리종류1", "메뉴얼1", "이미지1", LocalDateTime.now());
        Recipe recipe3 = new Recipe(3L, "일련번호1", "레시피명1", "조리방법1",
                "요리종류1", "메뉴얼1", "이미지1", LocalDateTime.now());

        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);

        RecipeAPI api = new RecipeAPI("1114", recipes);

        return new COOKRCP01("", api);
    }




}