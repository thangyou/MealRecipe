package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GetBoardRes {
    // 게시판 목록 조회 응답 DTO
    private Long boardId;
    private String email;
    private String nickname;
    private String title;
    private String desc;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GetBoardRes(Board board) {
        this.boardId = board.getBoardId();
//        this.email = board.getUser().getEmail();
//        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.desc = board.getDesc();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }


}
