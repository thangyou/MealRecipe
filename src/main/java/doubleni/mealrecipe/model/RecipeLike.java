package doubleni.mealrecipe.model;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "recipelikes")
@Entity
public class RecipeLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    // 다대일 관계 설정: 한 개의 Like는 하나의 User와 관련됨
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    // 다대일 관계 설정: 한 개의 Like는 하나의 Recipe와 관련됨
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rcp_id")
    private Recipe recipe;

    private Long checkLike; // 레시피 좋아요

}