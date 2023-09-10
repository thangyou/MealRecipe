package doubleni.mealrecipe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Spring Security에서는 비밀번호를 암호화해주는 BCRyptPasswordEncoder가 존재
//이를 spring에 등록해놓고 비밀번호 암호화, 비밀번호 체크할 때 사용하면 된다.
@Configuration
public class BCryptConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
