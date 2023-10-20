package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.GetApiRes;
import doubleni.mealrecipe.service.ApiService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping("/api")
public class ApiController {
    /**
     * 조회 - get
     * 검색 - search(Controller & Service) / find(Repository)
     * 생성 - save
     * 수정 - update
     * 삭제 - delete
     */

    private final ApiService apiService;

    /* 추가 */
    @PostMapping("/read-json")
    @ApiOperation(value="json 저장 API", notes="식약처 API 사용")
    public ResponseEntity<String> readRecipes() {
        try {
            apiService.read();
            return ResponseEntity.ok("레시피를 성공적으로 저장했습니다!");
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /* 조회 */
    @GetMapping("/list")
    @ApiOperation(value = "레시피 조회 API", notes = "레시피 리스트 조회")
    public ResponseEntity<List<GetApiRes>> getAllApi() {
        try {
            List<GetApiRes> getRecipeRes  = apiService.getAllApi();
            return ResponseEntity.ok(getRecipeRes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/id={id}")
    @ApiOperation(value="레시피 조회 API", notes="id로 레시피 조회")
    public BaseResponse<GetApiRes> getApiById(@PathVariable Long id) {
        try{
            GetApiRes response = apiService.getApiById(id);
            return new BaseResponse<>(response);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /* 조건 정렬 조회 */
    @GetMapping("/list-order-by-protein")
    @ApiOperation(value="레시피 조회 API", notes="고단백 레시피 정렬")
    public ResponseEntity<List<GetApiRes>> getApiByOrderByInfoProDesc() {
        try {
            List<GetApiRes> getRecipeRes  = apiService.getApiByOrderByInfoProDesc();
            return ResponseEntity.ok(getRecipeRes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list-order-by-fat")
    @ApiOperation(value="레시피 조회 API", notes="저단백 레시피 정렬")
    public ResponseEntity<List<GetApiRes>> getApiByOrderByInfoFatAsc() {
        try {
            List<GetApiRes> getRecipeRes  = apiService.getApiByOrderByInfoFatAsc();
            return ResponseEntity.ok(getRecipeRes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ===============================================================================================

    /* 검색 */
    // 키워드로 검색 - 키워드가 레시피명에 속한 레시피 리스트 출력
    @GetMapping("/search-recipe-of")
    @ApiOperation(value="레시피 검색 API", notes="키워드로 레시피 검색")
//    public BaseResponse<?> getRecipesByKeyword (@RequestParam("keyword") String keyword){
//        try{
//            List<GetApiRes> response = apiService.getRecipesByName(keyword);
//            return new BaseResponse<>(response);
//        }catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
    public ResponseEntity<?> searchApiByKeyword (@RequestParam("keyword") String keyword){
        try{
            List<GetApiRes> response = apiService.searchApiByName(keyword);
//            List<GetApiRes> response = apiService.searchApiByKeyword(keyword);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    // 재료로 검색 - 재료가 속한 레시피 리스트 출력
    @GetMapping("/search-recipe-of-ingredient")
    @ApiOperation(value = "레시피 검색 API", notes = "재료로 레시피 검색")
    public ResponseEntity<?> searchApiByIngredient(@RequestParam("ingredient") String ingredient) {
        try {
            return ResponseEntity.ok().body(apiService.searchApiByIngredient(ingredient));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 레시피명 검색 - 레시피의 재료 출력
    @GetMapping("/search-ingredient-of-recipe")
    @ApiOperation(value = "재료 검색 API", notes = "레시피의 재료 검색")
    public ResponseEntity<?> searchIngredientById(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok().body(apiService.searchIngredientByRcpNm(keyword));
    }



}

