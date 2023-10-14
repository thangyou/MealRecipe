package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.utils.JwtService;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;
import static doubleni.mealrecipe.utils.ValidationRegex.isRegexPhone;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<JoinRes>
     */

    @ResponseBody
    @Transactional
    @PostMapping("/users")
    public BaseResponse<JoinRes> joinUser(@RequestBody JoinRequest joinRequest)  {
        //회원가입 시 이메일을 입력하지 않았을 때
        if (joinRequest.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }

        //회원가입 시 비밀번호를 입력하지 않았을 때
        if (joinRequest.getPassword() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PWD);
        }

        //회원가입 시 휴대폰번호를 입력하지 않았을 때
        if (joinRequest.getPhone() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_TELNUM);
        }


        try{
            JoinRes joinRes = userService.signUp(joinRequest);
            return new BaseResponse<>(joinRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그인 API
     * [POST] /users/login
     *
     * @return BaseResponse<LoginRes>
     *
     * */
    @ResponseBody
    @Transactional
    @PostMapping("/users/login")
    public BaseResponse<LoginRes> Login(@RequestBody LoginRequest loginRequest){
        //로그인 시 이메일 입력하지 않았을 때
        if(loginRequest.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }

        //로그인 시 비밀번호를 입력하지 않았을 때
        if(loginRequest.getPassword() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PWD);
        }

        try{
            LoginRes loginRes = userService.login(loginRequest);
            return new BaseResponse<>(loginRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
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
    public BaseResponse<LoginRes> saveUserAfterInfo (@RequestBody PostExtraReq postExtraReq){
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

            LoginRes loginRes = userService.saveUserSNSInfo(postExtraReq);
            return new BaseResponse<>(loginRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
