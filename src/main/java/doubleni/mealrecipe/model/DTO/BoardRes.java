package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.BoardImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BoardRes {
    // 게시판 목록 조회 응답 DTO
    private Long boardId;
    private String email;
    private String nickname;
    private String title;
    private String content;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<String> imageURL;

    public BoardRes(Board board) {
        this.boardId = board.getBoardId();
//        this.email = board.getUser().getEmail();
//        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();

        this.imageURL = board.getBoardImages().stream()
                .map(BoardImage::getUrl)
                .collect(Collectors.toList());
    }


}
