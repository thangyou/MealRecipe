package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.GetRecipeIdRes;
import doubleni.mealrecipe.model.opinion.Allergy;
import doubleni.mealrecipe.model.opinion.OpinionReq;
import doubleni.mealrecipe.service.OpinionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Api(tags = "Opinion", description = "취향, 알레르기 조사 API")
@RequestMapping("/opinion")
public class OpinionController {

    private final OpinionService opinionService;

    /**
     * 취향, 알레르기 저장 api
     * [POST] /opinion/save/{id}
     *
     * @return BaseResponse<String>
     */
    @PostMapping("/save/{id}")
    @ApiOperation(value = "음식 취향, 알레르기 저장",notes = "음식 취향과 알레르기를 선택했을 때 각각 string을 list로 받아옴")
    public ResponseEntity<String> saveOpinion(@RequestBody OpinionReq opinionReq, @PathVariable Long id){
        try{
            opinionService.saveOpinion(opinionReq,id);
            return ResponseEntity.ok("음식 취향, 알레르기 저장했습니다!");
        } catch (BaseException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }


    /**
     * 선별해둔 좋아하는 음식 리스트 전달 api
     * [GET] /opinion/food/{id}
     *
     * @return BaseResponse<List<GetRecipeIdRes>>
     */
    @GetMapping("/food")
    @ApiOperation(value = "선별해둔 음식 취향 리스트 전달",notes = "좋아하는 음식을 선택할 수 있도록 미리 선별해둔 레시피 전달")
    public BaseResponse<List<GetRecipeIdRes>> foodList(@PathVariable Long id){
        try{
            List<GetRecipeIdRes> getRecipeIdRes = opinionService.gotofoodList();
            return new BaseResponse<>(getRecipeIdRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 알레르기 리스트 전달 api
     * [GET] /opinion/allergy/{id}
     *
     * @return BaseResponse<List<Allergy>>
     */
    @GetMapping("/allergy")
    @ApiOperation(value = "알레르기 리스트 전달",notes = "모든 알레르기 리스트를 전달")
    public BaseResponse<List<Allergy>> allergyList(@PathVariable Long id){
        try{
            List<Allergy> allergies = opinionService.allergyList();
            return new BaseResponse<>(allergies);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



}
