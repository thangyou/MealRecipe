package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Comment;
import doubleni.mealrecipe.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentRes {
    private Long id;
    private Long boardId;
    private String nickname;
    private String body;
    private LocalDateTime createdAt;

    public CommentRes(Comment comment) {
        this.id = comment.getId();
        this.boardId = comment.getBoard().getBoardId();
        this.nickname = comment.getUser().getNickname();
        this.body = comment.getBody();
        this.createdAt = comment.getBoard().getCreatedAt();
    }


}
