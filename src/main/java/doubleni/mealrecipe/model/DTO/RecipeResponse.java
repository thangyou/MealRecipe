package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Recipe;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class RecipeResponse {
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
    private List<String> rcpPartsDtls; // 재료 정보
    private String manual01; // 레시피 설명
    private String manualImg01; // 레시피 이미지
    private String manual02;
    private String manualImg02;
    private String manual03;
    private String manualImg03;
    private String manual04;
    private String manualImg04;
    private String manual05;
    private String manualImg05;
    private String manual06;
    private String manualImg06;
    private String manual07;
    private String manualImg07;
    private String manual08;
    private String manualImg08;
    private String manual09;
    private String manualImg09;
    private String manual10;
    private String manualImg10;
    private String manual11;
    private String manualImg11;
    private String manual12;
    private String manualImg12;
    private String manual13;
    private String manualImg13;
    private String manual14;
    private String manualImg14;
    private String manual15;
    private String manualImg15;
    private String manual16;
    private String manualImg16;
    private String manual17;
    private String manualImg17;
    private String manual18;
    private String manualImg18;
    private String manual19;
    private String manualImg19;
    private String manual20;
    private String manualImg20;
    private String rcpNaTip;

    public RecipeResponse(Recipe recipe) {
        this.rcpSeq = recipe.getRcpSeq();
        this.rcpNm = recipe.getRcpNm();
        this.rcpWay2 = recipe.getRcpWay2();
        this.rcpPat2 = recipe.getRcpPat2();
        this.infoWgt = recipe.getInfoWgt();
        this.hashTag = recipe.getHashTag();
        this.attFileNoMain = recipe.getAttFileNoMain();
        this.attFileNoMk = recipe.getAttFileNoMk();
        this.rcpPartsDtls = recipe.getRcpPartsDtls();
        this.manual01 = recipe.getManual01();
        this.manualImg01 = recipe.getManualImg01();
        this.rcpNaTip = recipe.getRcpNaTip();
    }


}
