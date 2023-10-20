package doubleni.mealrecipe.model.Test.entity;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "like")
@Entity
public class Like {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;      // 좋아요를 누른 유저

    @ManyToOne(fetch = FetchType.LAZY)
    private TestBoard board;    // 좋아요가 추가된 게시글

}
