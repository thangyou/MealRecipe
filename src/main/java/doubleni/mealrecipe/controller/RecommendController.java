package doubleni.mealrecipe.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.FlaskDTO;
import doubleni.mealrecipe.model.DTO.GetRecipeRes;
import doubleni.mealrecipe.service.RecipeService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.USERS_EMPTY_USER_ID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Recommend", description = "사용자 맞춤 레시피")
public class RecommendController {

    private final RecipeService recipeService;
    private final JwtService jwtService;


//    @PostMapping("/recommend-recipes")
//    @ApiOperation(value="키워드 기반 사용자 맞춤 레시피 추천", notes="\"새우 오징어 밀가루\"이렇게 식자재를 입력해서 보내면 가장 많이 포함된 음식 재료명을 추출함")
//    public List<String> recommendRecipes(@RequestBody String user_profile) {
//        // Flask API 엔드포인트 URL
//        String apiUrl = "http://15.164.139.103:5000/recommend-recipes";
//
//        // HTTP 헤더 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // 요청 본문 생성
//        HttpEntity<String> request = new HttpEntity<>(user_profile, headers);
//
//        // RestTemplate을 사용하여 Flask API로 POST 요청 보내기
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(
//                apiUrl,
//                HttpMethod.POST,
//                request,
//                String.class
//        );
//
//        // Flask API에서 반환한 응답을 가져옵니다.
//        String jsonResponse = response.getBody();
//
//        //System.out.println(jsonResponse);
//
//        // JSON 응답을 처리하기 위해 ObjectMapper를 생성합니다.
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            // JSON 문자열에서 레시피 목록을 역직렬화합니다.
//            List<String> recommendedRecipes = objectMapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
//            //System.out.println(recommendedRecipes);
//
//            return recommendedRecipes;
//        } catch (Exception e) {
//            System.out.println("예외 발생: " + e.getMessage());
//            return Collections.emptyList(); // 빈 목록 반환
//        }
//    }


//    @PostMapping("/recommend-recipeList")
//    @ApiOperation(value="키워드 기반 사용자 맞춤 레시피 추천 - 좋아하는 식자재만", notes="\"{  \"user_profile\" : \"새우 오징어 밀가루\" \" \n 이렇게 식자재를 입력해서 보내면 가장 많이 포함된 음식 재료명을 추출함")
//    @ApiResponses(value={@ApiResponse(code=4000,message = "데이터베이스 연결에 실패하였습니다.")})
//    public List<GetRecipeRes> recommendRecipesList(@RequestBody String user_profile) {
//        // Flask API 엔드포인트 URL
//        String apiUrl = "http://15.164.139.103:5000/recommend-recipes";
//
//        // HTTP 헤더 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // 요청 본문 생성
//        HttpEntity<String> request = new HttpEntity<>(user_profile, headers);
//
//        // RestTemplate을 사용하여 Flask API로 POST 요청 보내기
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(
//                apiUrl,
//                HttpMethod.POST,
//                request,
//                String.class
//        );
//
//        // Flask API에서 반환한 응답을 가져옵니다.
//        String jsonResponse = response.getBody();
//
//        //System.out.println(jsonResponse);
//
//        // JSON 응답을 처리하기 위해 ObjectMapper를 생성합니다.
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            // JSON 문자열에서 레시피 목록을 역직렬화합니다.
//            List<String> recommendedRecipes = objectMapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
//            //System.out.println(recommendedRecipes);
//
//            List<GetRecipeRes> recipeResList = new ArrayList();
//
//            for (int i = 0; i < recommendedRecipes.size(); i++) {
//                String recipeName = recommendedRecipes.get(i);
//                GetRecipeRes recipeRes = recipeService.searchGetRecipeResByKeyword(recipeName);
//
//                if (recipeRes != null) {
//                    recipeResList.add(recipeRes);
//                } else {
//                    // 검색 결과가 null인 경우 로그에 기록
//                    System.out.println("Recipe for " + recipeName + " not found.");
//
//                }
//            }
//
//            return recipeResList;
//        } catch (Exception e) {
//            if (e.getMessage() != null) {
//                System.out.println("예외 발생: " + e.getMessage());
//            } else {
//                System.out.println("예외 발생: " + e.toString());
//            }
//            return Collections.emptyList(); // 빈 목록 반환
//        }
//    }



    @PostMapping("/recommend-recipe")
    @ApiOperation(value="키워드 기반 사용자 맞춤 레시피 추천 - 좋아하는 식재료 + 알러지 ", notes="header 값이 필요업음")
    @ApiResponses(value={@ApiResponse(code=4000,message = "데이터베이스 연결에 실패하였습니다.")})
    public List<GetRecipeRes> recommendRecipes(@RequestBody FlaskDTO user_profile) {
        // Flask API 엔드포인트 URL
        String apiUrl = "http://15.164.139.103:5000/recommend-recipes-not-allergy";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문 생성
        HttpEntity<FlaskDTO> request = new HttpEntity<>(user_profile, headers);

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

            List<GetRecipeRes> recipeResList = new ArrayList();

            for (int i = 0; i < recommendedRecipes.size(); i++) {
                String recipeName = recommendedRecipes.get(i);
                GetRecipeRes recipeRes = recipeService.searchGetRecipeResByKeyword(recipeName);

                //System.out.println(recipeRes);

                if (recipeRes != null) {
                    recipeResList.add(recipeRes);
                } else {
                    // 검색 결과가 null인 경우 로그에 기록
                    System.out.println("Recipe for " + recipeName + " not found.");

                }
            }

            return recipeResList;
        } catch (Exception e) {
            if (e.getMessage() != null) {
                System.out.println("예외 발생: " + e.getMessage());
            } else {
                System.out.println("예외 발생: " + e.toString());
            }
            return Collections.emptyList(); // 빈 목록 반환
        }
    }

    @PostMapping("/recommend-user")
    @ApiOperation(value="협업 필터링 기반 사용자 맞춤 레시피 추천 - 리뷰 평점 ", notes="리뷰 평점으로 결과 출력 \n header 값이 필요함 \n - X-ACCESS-TOKEN : jwt")
    @ApiResponses(value={@ApiResponse(code=4000,message = "데이터베이스 연결에 실패하였습니다.")})
    public BaseResponse<List<GetRecipeRes>> recommendRecipesCollaborative() {
        try {
            // Flask API 엔드포인트 URL
            String apiUrl = "http://15.164.139.103:5000/collaborative-recommend";

            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 본문 생성
            HttpEntity<Long> request = new HttpEntity<>(idx, headers);

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

            // JSON 문자열에서 레시피 목록을 역직렬화합니다.
            List<String> recommendedRecipes = objectMapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
            //System.out.println(recommendedRecipes);

            List<GetRecipeRes> recipeResList = new ArrayList<>();

            for (int i = 0; i < recommendedRecipes.size(); i++) {
                String recipeId = recommendedRecipes.get(i);
                GetRecipeRes recipeRes = recipeService.searchGetRecipeResByRcpId(recipeId);

                if (recipeRes != null) {
                    recipeResList.add(recipeRes);
                } else {
                    // 검색 결과가 null인 경우 로그에 기록
                    System.out.println("Recipe for " + recipeId + " not found.");
                }
            }

            return new BaseResponse<>(recipeResList);

        } catch (Exception e) {
            if (e.getMessage() != null) {
                System.out.println("예외 발생: " + e.getMessage());
            } else {
                System.out.println("예외 발생: " + e.toString());
            }
            return new BaseResponse<>(Collections.emptyList()); // 빈 목록 반환
        }
    }





}
