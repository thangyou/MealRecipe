package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.GetRecipeRes;
import doubleni.mealrecipe.model.opinion.Allergy;
import doubleni.mealrecipe.model.opinion.OpinionReq;
import doubleni.mealrecipe.service.OpinionService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@RequiredArgsConstructor
@Controller
@Api(tags = "Opinion", description = "취향, 알레르기 조사 API")
@RequestMapping("/opinion")
public class OpinionController {

    private final OpinionService opinionService;
    private final JwtService jwtService;

    /**
     * 취향, 알레르기 저장 api
     * [POST] /opinion/save/{id}
     *
     * @return BaseResponse<String>
     */
    @PostMapping("/save/{id}")
    @ApiOperation(value = "음식 취향, 알레르기 저장", notes = "음식 취향과 알레르기를 선택했을 때 각각 string을 list로 받아옴")
    @ApiResponses(value={@ApiResponse(code =2003,message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code=4004,message = "파일 저장 실패하였습니다.")
    })
    public BaseResponse<String> saveOpinion(@RequestBody OpinionReq opinionReq, @PathVariable Long id) {
        try {
            Long idx = jwtService.getUserIdx();

            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            opinionService.saveOpinion(opinionReq, id);
            return new BaseResponse<>("음식 취향, 알레르기 저장했습니다!");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 선별해둔 좋아하는 음식 리스트 전달 api
     * [GET] /opinion/food
     *
     * @return BaseResponse<List<GetRecipeIdRes>>
     */
    @GetMapping("/food")
    @ApiOperation(value = "선별해둔 음식 취향 리스트 전달",notes = "좋아하는 음식을 선택할 수 있도록 미리 선별해둔 레시피 전달")
    public BaseResponse<List<GetRecipeRes>> foodList(){
        try{
            List<GetRecipeRes> getRecipeIdRes = opinionService.gotofoodList();
            return new BaseResponse<>(getRecipeIdRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 알레르기 리스트 전달 api
     * [GET] /opinion/allergy
     *
     * @return BaseResponse<List<Allergy>>
     */
    @GetMapping("/allergy")
    @ApiOperation(value = "알레르기 리스트 전달",notes = "모든 알레르기 리스트를 전달")
    public BaseResponse<List<Allergy>> allergyList(){
        try{
            List<Allergy> allergies = opinionService.allergyList();
            return new BaseResponse<>(allergies);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 사용자 알레르기 리스트 전달 api
     * [GET] /opinion/allergy/{id}
     *
     * @return BaseResponse<List<String>>
     */
    @GetMapping("/allergy/{id}")
    @ApiOperation(value="사용자에 해당되는 알레르기의 재료 정보 전달")
    @ApiResponses(value={@ApiResponse(code =2003,message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다.")
    })
    public BaseResponse<List<String>> allergyIngredient(@PathVariable Long id){
        try{
            Long idx = jwtService.getUserIdx();

            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            List<String> stringList = opinionService.allergyIngredient(id);
            return new BaseResponse<>(stringList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }




}
