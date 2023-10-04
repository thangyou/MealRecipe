package doubleni.mealrecipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import doubleni.mealrecipe.model.dto.AddRecipeRequest;
import doubleni.mealrecipe.model.dto.Recipe;
import doubleni.mealrecipe.model.dto.RecipeJSON;
import doubleni.mealrecipe.model.dto.UpdateRecipeRequest;
import doubleni.mealrecipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@Service
@RequiredArgsConstructor
public class RecipeService {
    private static final String API_URL = "http://openapi.foodsafetykorea.go.kr/api/7c74eb77831147ba9e86/COOKRCP01/xml/0/1";

    private final RestTemplate restTemplate;
    private final RecipeRepository recipeRepository;

    // 조회
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Recipe findById(long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

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

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    // Test
//    public String fetchDataAndConvertToJson() {
    public ResponseEntity<String> fetchDataAndConvertToJson() {
        StringBuilder apiResponse = new StringBuilder();
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
            return null; // 에러가 발생하면 null을 반환하거나 적절한 오류 처리를 수행할 수 있습니다.
        }

    }

//    public void saveJsonDataToDatabase(String jsonData) {
//        try {
//            // JSON 데이터를 Recipe 객체로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            Recipe recipe = objectMapper.readValue(jsonData, Recipe.class);
//
//            // Recipe 객체를 데이터베이스에 저장
//            recipeRepository.save(recipe);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace(); // JSON 처리 예외 발생 시 예외 처리를 수행하세요.
//        }
//    }

}