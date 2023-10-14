package doubleni.mealrecipe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
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

    private String status;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp createAt;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp updateAt;

//    @ManyToMany(mappedBy = "menu", cascade = CascadeType.ALL)
//    private List<Menu> menues = new ArrayList<>();


}
