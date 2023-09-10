package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    @NotBlank(message = "로그인 이메일이 비었습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 비었습니다.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "사용자 이름이 비었습니다.")
    private String userName;
    @NotBlank(message = "사용자의 전화번호가 비었습니다.")
    private String phone;

    //비밀번호 암호화X
    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .userName(this.userName)
                .role(UserRole.USER)
                .phone(this.phone)
                .enrollDate(new Timestamp(System.currentTimeMillis()))
                .lastUpdate(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    //비밀번호 암호화
    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(this.email)
                .password(encodedPassword)
                .userName(this.userName)
                .role(UserRole.USER)
                .phone(this.phone)
                .enrollDate(new Timestamp(System.currentTimeMillis()))
                .lastUpdate(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
