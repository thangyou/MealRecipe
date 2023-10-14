package doubleni.mealrecipe.service;

import doubleni.mealrecipe.utils.JwtService;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    //회원가입
    public JoinRes signUp(JoinRequest joinRequest) throws BaseException {
        try{
            if(userRepository.findByEmail(joinRequest.getEmail()).isPresent()){
                throw new BaseException(POST_USERS_EXISTS_EMAIL);
            }
            if (userRepository.findByNickname(joinRequest.getNickname()).isPresent()){
                throw new BaseException(POST_USERS_EXISTS_NICKNAME);
            }
            if (userRepository.findByPhone(joinRequest.getPhone()).isPresent()){
                throw new BaseException(POST_USERS_EXISTS_TELNUM);
            }

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

    //로그인
    public LoginRes login(LoginRequest loginRequest) throws BaseException {
        try {
            Optional<User> userOptional = userRepository.findByEmailAndAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Long id = user.getId();
                String jwt = jwtService.createJwt(id);
                String resultMessage = "'"+user.getNickname()+"'"+"님 로그인에 성공하였습니다!";
                return new LoginRes(id, user.getEmail(), jwt,resultMessage);

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
                return new LoginRes(id, user.getEmail(), jwt,resultMessage);
            } else {
                throw new BaseException(UPDATE_FAIL_USER);
            }

        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}


