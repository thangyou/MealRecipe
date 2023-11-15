package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.BoardLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeRes {

    // like
//    private Long likeId;
//
//    // user
//    private Long id;
//    private String user_nickname;

    // board
    private Long board_id;
    private String nickname; // board's writer
    private String title;
    private String content;
    private Integer hits;
    private Integer likeCnt;
    private Integer commentCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long fileId;

    private Long CheckLike;

    public BoardLikeRes(BoardLike boardLike) {
//        this.likeId = boardLike.getLikeId();
//        this.id = boardLike.getUser().getId();
//        this.nickname = boardLike.getUser().getNickname();
        this.board_id = boardLike.getBoard().getBoardId();
        this.nickname = boardLike.getBoard().getUser().getNickname();
        this.title = boardLike.getBoard().getTitle();
        this.content = boardLike.getBoard().getContent();
        this.hits = boardLike.getBoard().getHits();
        this.likeCnt = boardLike.getBoard().getLikeCnt();
        this.commentCnt = boardLike.getBoard().getCommentCnt();
        this.createdAt = boardLike.getBoard().getCreatedAt();
        this.updatedAt = boardLike.getBoard().getUpdatedAt();
        this.fileId = boardLike.getBoard().getFileId();
        this.CheckLike = boardLike.getCheckLike();
    }

}
