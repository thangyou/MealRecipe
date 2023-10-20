package doubleni.mealrecipe.model.Test.entity;

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
@Table(name = "comment")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;      // 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    private TestBoard board;    // 댓글이 달린 게시판

    public void update(String newBody) {
        this.body = newBody;
    }
}
