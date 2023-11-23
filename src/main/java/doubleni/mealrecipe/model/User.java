package doubleni.mealrecipe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Recipe> recipes;     // 레시피 게시판도 생성? 하면!
//    private Integer receivedRCPLikeCnt; // 유저가 받은 좋아요 개수 (본인 제외)

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<RecipeLike> recipelikes;       // 유저가 누른 레시피 좋아요 목록

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Board> boards;     // 작성글

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BoardLike> boardLikes; // 유저가 누른 게시판 좋아요 목록

    private Integer receivedLikeCnt; // 유저가 받은 좋아요 개수 (본인 제외)

    public void likeChange(Integer receivedLikeCnt) {
        this.receivedLikeCnt = receivedLikeCnt;
    }

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments; // 댓글

    /*
    @ManyToMany(mappedBy = "recipes", cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();
    */

}
