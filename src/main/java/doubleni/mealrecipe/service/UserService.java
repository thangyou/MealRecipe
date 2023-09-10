package doubleni.mealrecipe.service;

import doubleni.mealrecipe.model.DTO.JoinRequest;
import doubleni.mealrecipe.model.DTO.LoginRequest;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //Spring Security를 사용해서 로그인 구현시 사용해야 함
    //private final BCryptPasswordEncoder encoder;

    /**
     * email 중복체크
     * 회원가입 기능 구현시 사용
     * 중복되면 true return
     */
    public boolean checkLoginEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * userName 중복체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkUserNameDuplicate(String userName) {
        return userRepository.existsByUserName(userName);
    }

    /**
     * 회원가입 기능1
     */
    public void join(JoinRequest req) {
        userRepository.save(req.toEntity());
    }

    /**
     * 회원가입 기능2
     * */
//    public void join2(JoinRequest req){
//        userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
//    }

    /**
     * 로그인 기능
     */
    public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByEmail(req.getEmail());


        //email와 일치하는 User가 없으면 null return
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        //찾아온 User의 password와 입력된 password가 다르면 null return
        if (!user.getPassword().equals(req.getPassword())) {
            return null;
        }

        return user;
    }

    /**
     * email를 입력받아 User을 return 해주는 기능
     * 인증,인가 시 사용
     */
    public User getLoginUserByEmail(String email) {
        if (email == null) return null;

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

    /**
     * userId를 입력받아서 User를 retrun 해주는 기능
     * 인증,인가 시 사용
     */
    public User getLoginUserById(Long userId){
        if(userId == null) return null;

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

}


