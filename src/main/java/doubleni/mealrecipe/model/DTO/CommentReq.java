package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Comment;
import doubleni.mealrecipe.model.User;
import lombok.Data;

@Data
public class CommentReq {

    private String body;

    public Comment toEntity(Board board, User user) {
        return Comment.builder()
                .user(user)
                .board(board)
                .body(body)
                .build();
    }
}
