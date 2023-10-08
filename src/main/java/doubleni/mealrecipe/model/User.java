package doubleni.mealrecipe.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "USERS")
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String email; //이메일
    private String password; //비밀번호
    private String nickname; //사용자 이름
    private String imageUrl; //프로필 이미지
    private String phone; //사용자 전화번호

    @Enumerated(EnumType.STRING)
    private UserRole role; //접속한 유저

    @CreationTimestamp
    private Timestamp enrollDate; //회원가입 일자

    @CreationTimestamp
    private Timestamp lastUpdate; //회원정보 수정 일자

//    @ManyToMany(mappedBy = "menu", cascade = CascadeType.ALL)
//    private List<Menu> menues = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private SocialType socialType; //KAKAO, NAVER, GOOGLE

    //로그인한 소셜 타입의 식별자 값 (일반 로그인의 경우 null)
    private String socialId;

    private String refreshToken; //리프레시 토큰

    //유저 권한 설정 메소드
    public void authorizeUser() {
        this.role=UserRole.USER;
    }

    //비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken){
        this.refreshToken=updateRefreshToken;
    }



}
