package doubleni.mealrecipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String email; //이메일
    private String password; //비밀번호
    private String userName; //사용자 이름
    private String phone; //사용자 전화번호
    private char userState; //사용자 활동 상태
    private UserRole role; //접속한 유저

    @CreationTimestamp
    private Timestamp enrollDate; //회원가입 일자

    @CreationTimestamp
    private Timestamp lastUpdate; //회원정보 수정 일자

//    @ManyToMany(mappedBy = "menu", cascade = CascadeType.ALL)
//    private List<Menu> menues = new ArrayList<>();



}
