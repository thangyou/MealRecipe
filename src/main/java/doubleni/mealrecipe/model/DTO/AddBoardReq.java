package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
public class AddBoardReq { // Board 입력 받아 DB에 저장 DTO

    private String title;
    private String desc;
//    private MultipartFile uploadImage;

    @Builder
//    public Board toEntity(User user) {
    public Board toEntity() {
        return Board.builder()
//        return Board.builder(User user)
//                .user(user)
                .title(title)
                .desc(desc)
//                .likeCnt(0)
//                .commentCnt(0)
//                .createdAt(LocalDateTime.now())
                .build();
    }

}
