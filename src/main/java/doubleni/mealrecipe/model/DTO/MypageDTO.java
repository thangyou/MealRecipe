package doubleni.mealrecipe.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MypageDTO {
    private Long id;

    private String email;
    private String password;
    private String status;
    private String imageUrl;
    private String phone;
    private String nickname;

    private String jwt;
    private String resultMessage;
}
