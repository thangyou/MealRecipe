package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.auth.jwt.JwtService;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.config.exception.BaseResponseStatus;
import doubleni.mealrecipe.model.DTO.JoinRequest;
import doubleni.mealrecipe.model.DTO.PostExtraReq;
import doubleni.mealrecipe.model.DTO.PostUserRes;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;
import static doubleni.mealrecipe.utils.ValidationRegex.isRegexPhone;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @ResponseBody
    @Transactional
    @PostMapping("/users/sign-up")
    public BaseResponse<PostUserRes> joinUser(@RequestBody JoinRequest joinRequest) throws BaseException {
        try{
            PostUserRes postUserRes = userService.signUp(joinRequest);
            return new BaseResponse<>(postUserRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    /**
     * 소셜 로그인 추가 정보 API
     * [POST] /users/sns
     *
     * @return BaseResponse<PostUserRes>
     *
     * */

    @ResponseBody
    @Transactional
    @PostMapping("/users/sns")
    public BaseResponse<PostUserRes> saveUserAfterInfo (@RequestBody PostExtraReq postExtraReq){
        if (postExtraReq.getNickname()==null){
            return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
        }
        if (postExtraReq.getNickname().length()<2 || postExtraReq.getNickname().length()>20){
            return new BaseResponse<>(POST_USERS_INVALID_NICKNAME);
        }
        if (postExtraReq.getPhone() ==null){
            return new BaseResponse<>(POST_USERS_EMPTY_TELNUM);
        }
        if(!isRegexPhone(postExtraReq.getPhone())){
            return new BaseResponse<>(POST_USERS_INVALID_TELNUM);
        }

        try{
            Long id_jwt = jwtService.getUserIdx();
            Long id = postExtraReq.getId();

            if(id_jwt != id){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PostUserRes postUserRes = userService.saveUserSNSInfo(postExtraReq);
            return new BaseResponse<>(postUserRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
