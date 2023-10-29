package doubleni.mealrecipe.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendController {


    @PostMapping("/recommend-recipes")
    @ApiOperation(value="키워드 기반 사용자 맞춤 레시피 추천", notes="\"새우 오징어 밀가루\"이렇게 식자재를 입력해서 보내면 가장 많이 포함된 음식 재료명을 추출함")
    public List<String> recommendRecipes(@RequestBody String user_profile) {
        // Flask API 엔드포인트 URL
        String apiUrl = "http://15.164.139.103:5000/recommend-recipes";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문 생성
        HttpEntity<String> request = new HttpEntity<>(user_profile, headers);

        // RestTemplate을 사용하여 Flask API로 POST 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                request,
                String.class
        );

        // Flask API에서 반환한 응답을 가져옵니다.
        String jsonResponse = response.getBody();

        //System.out.println(jsonResponse);

        // JSON 응답을 처리하기 위해 ObjectMapper를 생성합니다.
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 문자열에서 레시피 목록을 역직렬화합니다.
            List<String> recommendedRecipes = objectMapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
            //System.out.println(recommendedRecipes);

            return recommendedRecipes;
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
            return Collections.emptyList(); // 빈 목록 반환
        }
    }
}
