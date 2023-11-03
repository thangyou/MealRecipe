package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardDetailRes {
    private Long boardId;
    private String nickname;
    private String title;
    private String content;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private FileRes file;

    public BoardDetailRes(Board board) {
        this.boardId = board.getBoardId();
        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.content = board.getContent();
//        this.likeCnt = board.getLikeCnt();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
//        if (board.getFileId() != null) {
//            this.file = new FileRes(board.getFile());
//        }
    }
}
