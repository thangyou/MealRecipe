package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetApiRes {

    private Long id;
    private String rcpNm; // 레시피명
    private String rcpSeq; // 일련 번호
    private int infoPro; // 단백질
    private int infoFat; // 지방
    private String ingredient; // 재료 정보

    public GetApiRes(Api api) {
        this.id = api.getId();
        this.rcpNm = api.getRcpNm();
        this.rcpSeq = api.getRcpSeq();
        this.infoPro = api.getInfoPro();
        this.infoFat = api.getInfoFat();
        this.ingredient = api.getIngredient();
    }

}
