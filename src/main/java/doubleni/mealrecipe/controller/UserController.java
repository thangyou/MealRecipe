package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.service.UserService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "User", description = "사용자")
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
    @PostMapping(value = "/users")
    @ApiOperation(value="회원가입", notes="이메일, 비밀번호, 전화번호, 프로필이미지 등으로 회원 가입한다.")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),@ApiResponse(code=2017,message = "이미 가입된 이메일입니다."),
            @ApiResponse(code=2020,message = "이미 가입된 휴대폰 번호입니다."),@ApiResponse(code=2024,message = "중복된 닉네임입니다."),
            @ApiResponse(code=2015,message = "이메일을 입력해주세요."),@ApiResponse(code=2021,message = "비밀번호를 입력해주세요."),
            @ApiResponse(code=2018,message = "휴대폰 번호를 입력해주세요.")
    })
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
    @PostMapping(value = "/users/login")
    @ApiOperation(value="로그인", notes="이메일, 비밀번호로 로그인하기")
    @ApiResponses(value={@ApiResponse(code =2015,message = "이메일을 입력해주세요."),@ApiResponse(code=2021,message = "비밀번호를 입력해주세요."),
            @ApiResponse(code=3014,message = "없는 아이디거나 비밀번호가 틀렸습니다."),@ApiResponse(code=4000,message = "데이터베이스 연결에 실패하였습니다.")})
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
    @PostMapping(value = "/users/sns")
    @ApiOperation(value="소셜로그인 추가", notes="닉네임, 전화번호, 프로필이미지 추가 \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    @ApiResponses(value={@ApiResponse(code =2023,message = "닉네임을 입력해주세요."),@ApiResponse(code=2025,message = "닉네임은 2 ~ 20자 사이로 입력해주세요."),
            @ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code=4014,message = "유저 정보를 수정하는데 실패했습니다."),
            @ApiResponse(code=4000,message = "데이터베이스 연결에 실패하였습니다."),@ApiResponse(code=2012,message = "이미 저장한 소셜로그인 유저입니다.")
    })
    public BaseResponse<LoginRes> saveUserAfterInfo (@RequestBody PostExtraReq postExtraReq){
        if (postExtraReq.getNickname()==null){
            return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
        }
        if (postExtraReq.getNickname().length()<2 || postExtraReq.getNickname().length()>20){
            return new BaseResponse<>(POST_USERS_INVALID_NICKNAME);
        }


        try{
            Long id_jwt = jwtService.getUserIdx();

            if(id_jwt != 0){
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            LoginRes loginRes = userService.saveUserSNSInfo(postExtraReq,id_jwt);
            return new BaseResponse<>(loginRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 마이페이지 수정 API
     * [PUT] /mypage
     * @return BaseResponse<MypageDTO>
     */

    @ResponseBody
    @PutMapping(value = "/mypage")
    @ApiOperation(value="마이페이지 수정", notes="비밀번호, 전화번호, 프로필이미지, 소개글 등을 수정한다. \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),@ApiResponse(code=2025,message = "닉네임은 2 ~ 20자 사이로 입력해주세요."),
            @ApiResponse(code=2020,message = "이미 가입된 휴대폰 번호입니다."), @ApiResponse(code=2024,message = "중복된 닉네임입니다."), @ApiResponse(code=4014,message = "유저 정보를 수정하는데 실패했습니다."),
            @ApiResponse(code=2003,message = "권한이 없는 유저의 접근입니다."),@ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요.")
    })
    public BaseResponse<MypageDTO> mypagefix(@RequestParam(required = false) String password, @RequestParam(required = false) String phone,
                                             @RequestParam(required = false) String status, @RequestParam(required = false) String nickname, @RequestPart(value = "images" ,required = false) MultipartFile imageFile)  {

        try{
            MypageDTO mypageDTO = userService.mypagefixInfo(password,nickname,phone,status, imageFile);
            return new BaseResponse<>(mypageDTO);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



    /**
     * 마이페이지 조회 API
     * [GET] /mypage/{id}
     * @return BaseResponse<MypageDTO>
     */

    @ResponseBody
    @Transactional
    @GetMapping(value = "/mypage")
    @ApiOperation(value="마이페이지 조회", notes=" headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code=2003,message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=2010,message = "유저 아이디 값을 확인해주세요.")
    })
    public BaseResponse<MypageDTO> mypageget()   {

        try{
            MypageDTO mypageDTO = userService.mypageFocus();
            return new BaseResponse<>(mypageDTO);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    //저장된 이미지 조회
    //이미지 네임을 알 수 있음 그 거를 기반으로 이미지 조회하기
    //http://15.164.139.103:8080/mypage/images/7f658d91-ef68-4b59-a381-af5bc9938768_fighting.png
    @ResponseBody
    @GetMapping("mypage/images/{imageName}")
    @ApiOperation(value="마이페이지 이미지 조회", notes="이미지 조회할 때 url을 여기다가 붙여서 get 보내셈")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\templates\\image\\";
        String imagePath = projectPath + imageName;

        try {
            FileInputStream imageStream = new FileInputStream(imagePath);
            byte[] imageBytes = imageStream.readAllBytes();
            imageStream.close();

            String contentType = determineContentType(imageName); // 이미지 파일 확장자에 따라 MIME 타입 결정

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String determineContentType(String imageName) {
        String extension = FilenameUtils.getExtension(imageName); // Commons IO 라이브러리 사용
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            // 다른 이미지 타입 추가 가능
            default:
                return "application/octet-stream"; // 기본적으로 이진 파일로 다룸
        }
    }

    /**
     * 회원탈퇴 API
     * [DELETE] /users
     *
     * @return BaseResponse<St>
     *
     * */
    @ResponseBody
    @DeleteMapping("/users")
    @ApiOperation(value="회원 탈퇴", notes="회원 탈퇴")
    public BaseResponse<String> deleteUsers() {
        try{
            Long id_jwt = jwtService.getUserIdx();

            if(id_jwt != 0){
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            //회원 이미지 삭제
            userService.deleteByUserImage(id_jwt);

            //회원 삭제
            userService.deleteByUser(id_jwt);

            return new BaseResponse<>("회원 탈퇴를 설공했습니다!");

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }






}