package doubleni.mealrecipe.service;

import doubleni.mealrecipe.auth.jwt.JwtService;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.DTO.JoinRequest;
import doubleni.mealrecipe.model.DTO.PostExtraReq;
import doubleni.mealrecipe.model.DTO.PostUserRes;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.model.UserRole;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public PostUserRes signUp(JoinRequest joinRequest) throws BaseException {
        try{
            if(userRepository.findByEmail(joinRequest.getEmail()).isPresent()){
                throw new BaseException(POST_USERS_EXISTS_EMAIL);
            }
            if (userRepository.findByNickname(joinRequest.getNickname()).isPresent()){
                throw new BaseException(POST_USERS_EXISTS_NICKNAME);
            }

            User user = User.builder()
                    .email(joinRequest.getEmail())
                    .password(joinRequest.getPassword())
                    .nickname(joinRequest.getNickname())
                    .imageUrl(joinRequest.getImageUrl())
                    .phone(joinRequest.getPhone())
                    .role(UserRole.USER)
                    .build();

            user.passwordEncode(passwordEncoder);
            userRepository.save(user);

            Long id = user.getId();
            String jwt = jwtService.createJwt(id);
            return new PostUserRes(id,jwt);

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }


    }

    public PostUserRes saveUserSNSInfo(PostExtraReq postExtraReq) throws BaseException{
        try{
            Long id = postExtraReq.getId();
            String jwt=jwtService.createJwt(id);
            return new PostUserRes(id,jwt);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}


