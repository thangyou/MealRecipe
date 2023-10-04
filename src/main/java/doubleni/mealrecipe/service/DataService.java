package doubleni.mealrecipe.service;
import doubleni.mealrecipe.model.dto.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service
public class DataService {

    private static final String API_URL = "http://openapi.foodsafetykorea.go.kr/api/7c74eb77831147ba9e86/COOKRCP01/xml/0/1";

    private final RestTemplate restTemplate;
    private final RecipeService recipeService; // RecipeService를 주입받아야 합니다.

    public DataService(RestTemplate restTemplate, RecipeService recipeService) {
        this.restTemplate = restTemplate;
        this.recipeService = recipeService;
    }

    public ResponseEntity<String> fetchDataAndSaveToDatabase() {
        try {
            // API 요청
            String xmlResponse = restTemplate.getForObject(API_URL, String.class);

            // XML to JSON 변환 코드
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(xmlResponse);

            // 필요한 데이터 추출
            String rcpSeq = jsonNode.at("/COOKRCP01/row/0/RCP_SEQ").asText();
            String rcpNm = jsonNode.at("/COOKRCP01/row/0/RCP_NM").asText();
            String rcpWay2 = jsonNode.at("/COOKRCP01/row/0/RCP_WAY2").asText();
            String rcpPat2 = jsonNode.at("/COOKRCP01/row/0/RCP_PAT2").asText();
            String manual01 = jsonNode.at("/COOKRCP01/row/0/MANUAL01").asText();
            String manualImg01 = jsonNode.at("/COOKRCP01/row/0/MANUAL_IMG01").asText();

            System.out.println("rcpSeq = " + rcpSeq);
            System.out.println("rcpNm = " + rcpNm);
            System.out.println("rcpWay2 = " + rcpWay2);
            System.out.println("rcpPat2 = " + rcpPat2);
            System.out.println("manual01 = " + manual01);
            System.out.println("manualImg01 = " + manualImg01);

            // Recipe 객체 생성
            Recipe recipe = Recipe.builder()
                    .rcp_seq(rcpSeq)
                    .rcp_nm(rcpNm)
                    .rcp_way2(rcpWay2)
                    .rcp_pat2(rcpPat2)
                    .manual01(manual01)
                    .manual_img01(manualImg01)
                    .build();

            // Recipe 객체를 데이터베이스에 저장
            recipeService.saveRecipe(recipe);

            return ResponseEntity.ok("Data fetched and saved to database successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch and save data to database.");
        }
    }
}
