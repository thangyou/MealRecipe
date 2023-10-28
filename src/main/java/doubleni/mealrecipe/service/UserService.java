package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.model.UserImage;
import doubleni.mealrecipe.repository.UserImageRepositorty;
import doubleni.mealrecipe.repository.UserRepository;
import doubleni.mealrecipe.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserImageRepositorty userImageRepositorty;

    //회원가입
    public JoinRes signUp(JoinRequest joinRequest) throws BaseException {
        if(userRepository.findByEmail(joinRequest.getEmail()).isPresent()){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        if (userRepository.findByNickname(joinRequest.getNickname()).isPresent()){
            throw new BaseException(POST_USERS_EXISTS_NICKNAME);
        }
        if (userRepository.findByPhone(joinRequest.getPhone()).isPresent()){
            throw new BaseException(POST_USERS_EXISTS_TELNUM);
        }
        try{

            User user = User.builder()
                    .email(joinRequest.getEmail())
                    .password(joinRequest.getPassword())
                    .nickname(joinRequest.getNickname())
                    .imageUrl(joinRequest.getImageUrl())
                    .phone(joinRequest.getPhone())
                    .createAt(new Timestamp(System.currentTimeMillis()))
                    .type("NORMAL")
                    .build();

            userRepository.save(user);

            Long id = user.getId();
            String jwt = jwtService.createJwt(id);
            String resultMessage = "'"+user.getNickname()+"'"+"님 회원가입을 환영합니다!";
            return new JoinRes(id, user.getEmail(), user.getNickname(), user.getImageUrl(), user.getPhone(),jwt,resultMessage);

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }


    }

    //마이페이지 조회
    public MypageDTO mypageFocus() throws BaseException {

        Long id = jwtService.getUserIdx();

        if (id == 0) {
            throw  new BaseException(USERS_EMPTY_USER_ID);
        }

        try {
            Optional<User> userOptional = userRepository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String jwt = jwtService.createJwt(id);
                String resultMessage = "'"+user.getNickname()+"'"+"님 조회 성공하였습니다!";

                MypageDTO mypageDTO = new MypageDTO();
                mypageDTO.setId(user.getId());
                mypageDTO.setEmail(user.getEmail());
                mypageDTO.setPassword(user.getPassword());
                mypageDTO.setNickname(user.getNickname());
                mypageDTO.setImageUrl(user.getImageUrl());
                mypageDTO.setStatus(user.getStatus());
                mypageDTO.setJwt(jwt);
                mypageDTO.setResultMessage(resultMessage);
                mypageDTO.setPhone(user.getPhone());


                return mypageDTO;

            } else {
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




    //마이페이지 수정
    @Transactional
    public MypageDTO mypagefixInfo(String password, String nickname, String phone, String status, MultipartFile imageFile) throws BaseException{
        Long idx = jwtService.getUserIdx();

        if (idx == 0) {
            throw  new BaseException(USERS_EMPTY_USER_ID);
        }

        try{

            Optional<User> userOptional=userRepository.findById(idx);

            if (userOptional.isPresent()){
                User user = userOptional.get();
                String jwt=jwtService.createJwt(idx);

                if(password!=null){

                    user.setPassword(password);
                }

                if(phone!=null){
                    if (userRepository.findByPhone(phone).isPresent()){
                        throw new BaseException(POST_USERS_EXISTS_TELNUM);
                    }
                    user.setPhone(phone);
                }

                if(status!=null){
                    user.setStatus(status);
                }

                if(nickname!=null){
                    if (nickname.length()<2 || nickname.length()>20){
                        throw  new BaseException(POST_USERS_INVALID_NICKNAME);
                    }
                    if (userRepository.findByNickname(nickname).isPresent()){
                        throw new BaseException(POST_USERS_EXISTS_NICKNAME);
                    }
                    user.setNickname(nickname);
                }

                if(imageFile!=null){
                    // 이미지 파일 저장 경로
                    String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\templates\\image\\";
                    UUID uuid = UUID.randomUUID();
                    String originalFileName = uuid + "_" + imageFile.getOriginalFilename();
                    File saveFile = new File(projectPath +originalFileName);

                    // 이미지 URL 정보를 리스트에 추가
                    String imageUrl = "http://localhost:8080/mypage/images/" + originalFileName;

                    try{
                        Optional<UserImage> existingImage =userImageRepositorty.findByUser(user);
                        if (existingImage.isPresent()) {
                            UserImage image = existingImage.get();
                            imageFile.transferTo(saveFile);
                            image.setImgName(originalFileName);
                            image.setImgOriName(imageFile.getOriginalFilename());
                            image.setImgPath(saveFile.getAbsolutePath());
                            image.setUser(user);
                            userImageRepositorty.save(image);
                        }
                        else{
                            imageFile.transferTo(saveFile);
                            UserImage userImage = new UserImage();
                            userImage.setImgName(originalFileName);
                            userImage.setImgOriName(imageFile.getOriginalFilename());
                            userImage.setImgPath(saveFile.getAbsolutePath());
                            userImage.setUser(user);
                            userImageRepositorty.save(userImage);

                        }

                        user.setImageUrl(imageUrl);


                    } catch (IOException e){
                        throw new RuntimeException("이미지 저장에 실패하였습니다.", e);
                    }

                }

                user.setUpdateAt(new Timestamp(System.currentTimeMillis()));

                String resultMessage = "'"+user.getNickname()+"'"+"님 마이페이지 수정 성공하였습니다!";

                userRepository.save(user);

                MypageDTO mypageDTO = new MypageDTO();
                mypageDTO.setId(user.getId());
                mypageDTO.setEmail(user.getEmail());
                mypageDTO.setPassword(user.getPassword());
                mypageDTO.setNickname(user.getNickname());
                mypageDTO.setImageUrl(user.getImageUrl());
                mypageDTO.setStatus(user.getStatus());
                mypageDTO.setJwt(jwt);
                mypageDTO.setResultMessage(resultMessage);
                mypageDTO.setPhone(user.getPhone());


                return mypageDTO;
            } else {
                throw new BaseException(UPDATE_FAIL_USER);
            }

        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }





    //로그인
    public LoginRes login(LoginRequest loginRequest) throws BaseException {
        try {
            Optional<User> userOptional = userRepository.findByEmailAndAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Long id = user.getId();
                String jwt = jwtService.createJwt(id);
                String resultMessage = "'"+user.getNickname()+"'"+"님 로그인에 성공하였습니다!";
                return new LoginRes(id, user.getEmail(),user.getNickname(), jwt,resultMessage);

            } else {
                throw new BaseException(FAILED_TO_LOGIN);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //소셜로그인 추가 정보
    public LoginRes saveUserSNSInfo(PostExtraReq postExtraReq) throws BaseException{
        try{
            Long id = postExtraReq.getId();
            Optional<User> userOptional=userRepository.findById(id);

            if (userOptional.isPresent()){
                User user = userOptional.get();
                String jwt=jwtService.createJwt(id);

                user.setNickname(postExtraReq.getNickname());
                user.setPhone(postExtraReq.getPhone());
                user.setImageUrl(postExtraReq.getImageUrl());
                user.setUpdateAt(new Timestamp(System.currentTimeMillis()));

                String resultMessage = "'"+user.getNickname()+"'"+"님 소셜로그인에 성공하였습니다!";
                userRepository.save(user);
                return new LoginRes(id, user.getEmail(), user.getNickname(), jwt,resultMessage);
            } else {
                throw new BaseException(UPDATE_FAIL_USER);
            }

        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }



}


