package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardRes {
    // 게시판 목록 조회 응답 DTO
    private Long boardId;
    private String nickname;
    private String title;
    private String content;
    private Integer likeCnt;
    private Integer hits;
    private Integer commentCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long fileId;

    public BoardRes(Board board) {
        this.boardId = board.getBoardId();
        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.likeCnt = board.getLikeCnt();
        this.hits = board.getHits();
        this.commentCnt = board.getCommentCnt();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.fileId = board.getFileId();
    }


}
