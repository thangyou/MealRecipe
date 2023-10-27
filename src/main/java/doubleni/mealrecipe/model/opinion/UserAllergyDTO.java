package doubleni.mealrecipe.model.opinion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAllergyDTO {
    private Long userId;
    private Long allergyId;
    private String allergyName;
    private String allergyImage;
}
