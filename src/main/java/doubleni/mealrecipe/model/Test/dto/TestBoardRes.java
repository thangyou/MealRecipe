package doubleni.mealrecipe.model.Test.dto;

import doubleni.mealrecipe.model.Test.entity.TestBoard;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TestBoardRes {
    // 게시판 목록 조회 응답 DTO

    private Long boardId;
    private String email;
    private String nickname;
    private String title;
    private String content;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TestBoardRes(TestBoard board) {
        this.boardId = board.getBoardId();
        this.email = board.getUser().getEmail();
        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.likeCnt = board.getLikeCnt();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }


}
