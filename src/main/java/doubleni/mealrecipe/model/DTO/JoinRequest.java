package doubleni.mealrecipe.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class JoinRequest {
    private String email;
    private String password;
    private String nickname; //사용자 이름
    private String imageUrl; //프로필 이미지
    private String phone; //사용자 전화번호
}
