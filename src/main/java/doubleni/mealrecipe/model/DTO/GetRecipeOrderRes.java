package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRecipeOrderRes {
    private Long rcpId;
    private String rcpNm; // 레시피명
    private int infoPro; // 단백질
    private int infoFat; // 지방

    public GetRecipeOrderRes(Recipe r) {
        this.rcpId = r.getRcpId();
        this.rcpNm = r.getRcpNm();
        this.infoPro = r.getInfoPro();
        this.infoFat = r.getInfoFat();
    }
}
