package doubleni.mealrecipe.model.dto;

import doubleni.mealrecipe.model.entity.UploadImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RecipeViewResponse {

    private Long rcpId;
    private String rcpSeq; // 일련 번호
    private String rcpNm; // 레시피명
    private String rcpWay2; // 조리 방법
    private String rcpPat2; // 요리 종류
    private String infoWgt; // 중량(1인분)
    private String infoEng; // 열량
    private String infoCar; // 탄수화물
    private String infoPro; // 단백질
    private String infoFat; // 지방
    private String infoNa; // 나트륨
    private String hashTag; // 해시태그
    private String attFileNoMain; // 이미지 경로(소)
    private String attFileNoMk; // 이미지 경로(대)
    private String rcpPartsDtls; // 재료 정보
    private String manual01; // 레시피 설명
    private String manualImg01; // 레시피 이미지

    public RecipeViewResponse(Recipe recipe) {
        this.rcpId = recipe.getRcpId();
        this.rcpSeq = recipe.getRcpSeq();
        this.rcpNm = recipe.getRcpNm();
        this.rcpWay2 = recipe.getRcpWay2();
        this.rcpPat2 = recipe.getRcpPat2();
        this.infoWgt = recipe.getInfoWgt();
        this.infoEng = recipe.getInfoEng();
        this.infoCar = recipe.getInfoCar();
        this.infoPro = recipe.getInfoPro();
        this.infoFat = recipe.getInfoFat();
        this.infoNa = recipe.getInfoNa();
        this.hashTag = recipe.getHashTag();
        this.attFileNoMain = recipe.getAttFileNoMain();
        this.attFileNoMk = recipe.getAttFileNoMk();
        this.rcpPartsDtls = recipe.getRcpPartsDtls();
        this.manual01 = recipe.getManual01();
        this.manualImg01 = recipe.getManualImg01();

    }
}
