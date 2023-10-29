package doubleni.mealrecipe.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostExtraReq {

    private String nickname;
    private String imageUrl; //프로필 이미지
    private String phone; //사용자 전화번호
}
