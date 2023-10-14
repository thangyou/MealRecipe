package doubleni.mealrecipe.auth;

import doubleni.mealrecipe.auth.model.PostKakaoReq;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.PostUserRes;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService OAuthService;

    public OAuthController(OAuthService OAuthService){
        this.OAuthService=OAuthService;
    }

    /**
     * 카카오 소셜 로그인 api (CALLBACK)
     * [POST] /oauth/kakao
     *
     * @return BaseResponse<PostUserRes>
     */

    @PostMapping("/kakao")
    @Transactional
    @ResponseBody
    public BaseResponse<PostUserRes> kakaoLogin (@RequestBody PostKakaoReq postKakaoReq) {
        try{
            PostUserRes postUserRes = OAuthService.saveUser(postKakaoReq.getAccess_token());
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
