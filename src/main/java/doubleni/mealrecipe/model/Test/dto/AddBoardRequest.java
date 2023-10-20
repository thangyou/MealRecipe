package doubleni.mealrecipe.model.Test.dto;

import doubleni.mealrecipe.model.Test.entity.TestBoard;
import doubleni.mealrecipe.model.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
public class AddBoardRequest {
    // Board 입력 받아 DB에 저장 DTO

    private String title;
    private String content;
    private MultipartFile uploadImage;

    @Builder
    public TestBoard toEntity(User user) {
        return TestBoard.builder()
                .user(user)
                .title(title)
                .content(content)
//                .likeCnt(0)
//                .commentCnt(0)
//                .createdAt(LocalDateTime.now())
                .build();
    }

}
