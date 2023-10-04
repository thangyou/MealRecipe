package doubleni.mealrecipe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeJSON {
    private String total_count;
    private String RESULT;
    private String row;

    static class RESULT {}
    static class row {}

    private String CODE;
    private String MSG;

    private String RCP_SEQ; // 일련 번호
    private String RCP_NM; // 레시피명
    private String RCP_WAY2; // 조리 방법
    private String RCP_PAT2; // 요리 종류
    private String MANUAL01; // 레시피 설명
    private String MANUAL_IMG01; // 레시피 이미지
}

class RESULT {
    private String CODE;
    private String MSG;
}

class row {
    private String RCP_SEQ; // 일련 번호
    private String RCP_NM; // 레시피명
    private String RCP_WAY2; // 조리 방법
    private String RCP_PAT2; // 요리 종류
    private String MANUAL01; // 레시피 설명
    private String MANUAL_IMG01; // 레시피 이미지
}
