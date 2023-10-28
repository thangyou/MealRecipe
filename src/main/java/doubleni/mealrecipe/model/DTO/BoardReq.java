package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Board;
//import doubleni.mealrecipe.model.UploadImage;
import doubleni.mealrecipe.model.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class BoardReq {
    // 게시판 추가, 수정 요청 DTO
    private Long boardId;
    private String email;
    private String nickname;
    private String title;
    private String content;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private MultipartFile newImage;
//    private UploadImage uploadImage;

    @Builder
//    public Board toEntity() {
    public Board toEntity(User user) {
        return Board.builder()
                .user(user)
                .title(this.title)
                .content(this.content)
                .build();
    }

}
