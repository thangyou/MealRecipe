package doubleni.mealrecipe.model.Test.dto;

import doubleni.mealrecipe.model.Test.entity.TestBoard;
import doubleni.mealrecipe.model.Test.entity.UploadImage;
import doubleni.mealrecipe.model.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class TestBoardReq {
    // 게시판 추가, 수정 요청 DTO
    private Long boardId;
    private String email;
    private String nickname;
    private String title;
    private String content;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MultipartFile newImage;
    private UploadImage uploadImage;

//    @Builder
//    public TestBoard toEntity(User user) {
//        return TestBoard.builder()
//                .boardId(this.boardId)
//                .user(user)
//                .title(this.title)
//                .content(this.content)
//                .likeCnt(this.likeCnt)
//                .createdAt(this.createdAt)
//                .updatedAt(this.updatedAt)
//                .build();
//    }
}
