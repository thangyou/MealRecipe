package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.BoardLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeRes {

    private Long likeId;
    private Long id;
    private String nickname;
    private Long board_id;
    private String writer;
    private String title;

    public BoardLikeRes(BoardLike boardLike) {
        this.likeId = boardLike.getLikeId();
        this.id = boardLike.getUser().getId();
        this.nickname = boardLike.getUser().getNickname();
        this.writer = boardLike.getBoard().getUser().getNickname();
        this.board_id = boardLike.getBoard().getBoardId();
        this.title = boardLike.getBoard().getTitle();
    }

}
