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
    private String ingredient; // 재료 정보

    public GetApiRes(Api api) {
        this.id = api.getId();
        this.rcpNm = api.getRcpNm();
        this.ingredient = api.getIngredient();
    }

}
