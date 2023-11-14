package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.BoardLike;
import doubleni.mealrecipe.model.RecipeLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeLikeRes {
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
    private Double reviewAverge;

    public RecipeLikeRes(RecipeLike recipeLike) {
        this.rcpId = recipeLike.getRecipe().getRcpId();
        this.rcpSeq = recipeLike.getRecipe().getRcpSeq();
        this.rcpNm = recipeLike.getRecipe().getRcpNm();
        this.rcpWay2 = recipeLike.getRecipe().getRcpWay2();
        this.rcpPat2 = recipeLike.getRecipe().getRcpPat2();
        this.infoWgt = recipeLike.getRecipe().getInfoWgt();
        this.infoCar = recipeLike.getRecipe().getInfoCar();
        this.infoEng = recipeLike.getRecipe().getInfoEng();
        this.infoPro = recipeLike.getRecipe().getInfoPro();
        this.infoFat = recipeLike.getRecipe().getInfoFat();
        this.infoNa = recipeLike.getRecipe().getInfoNa();
        this.hashTag = recipeLike.getRecipe().getHashTag();
        this.attFileNoMain = recipeLike.getRecipe().getAttFileNoMain();
        this.attFileNoMk = recipeLike.getRecipe().getAttFileNoMk();
        this.rcpPartsDtls = recipeLike.getRecipe().getRcpPartsDtls();
        this.manual01 = recipeLike.getRecipe().getManual01();
        this.manualImg01 = recipeLike.getRecipe().getManualImg01();
        this.manual02 = recipeLike.getRecipe().getManual02();
        this.manualImg02 = recipeLike.getRecipe().getManualImg02();
        this.manual03 = recipeLike.getRecipe().getManual03();
        this.manualImg03 = recipeLike.getRecipe().getManualImg03();
        this.manual04 = recipeLike.getRecipe().getManual04();
        this.manualImg04 = recipeLike.getRecipe().getManualImg04();
        this.manual05 = recipeLike.getRecipe().getManual05();
        this.manualImg05 = recipeLike.getRecipe().getManualImg05();
        this.manual06 = recipeLike.getRecipe().getManual06();
        this.manualImg06 = recipeLike.getRecipe().getManualImg06();
        this.manual07 = recipeLike.getRecipe().getManual07();
        this.manualImg07 = recipeLike.getRecipe().getManualImg07();
        this.manual08 = recipeLike.getRecipe().getManual08();
        this.manualImg08 = recipeLike.getRecipe().getManualImg08();
        this.manual09 = recipeLike.getRecipe().getManual09();
        this.manualImg09 = recipeLike.getRecipe().getManualImg09();
        this.manual10 = recipeLike.getRecipe().getManual10();
        this.manualImg10 = recipeLike.getRecipe().getManualImg10();
        this.manual11 = recipeLike.getRecipe().getManual11();
        this.manualImg11 = recipeLike.getRecipe().getManualImg11();
        this.manual12 = recipeLike.getRecipe().getManual12();
        this.manualImg12 = recipeLike.getRecipe().getManualImg12();
        this.manual13 = recipeLike.getRecipe().getManual13();
        this.manualImg13 = recipeLike.getRecipe().getManualImg13();
        this.manual14 = recipeLike.getRecipe().getManual14();
        this.manualImg14 = recipeLike.getRecipe().getManualImg14();
        this.manual15 = recipeLike.getRecipe().getManual15();
        this.manualImg15 = recipeLike.getRecipe().getManualImg15();
        this.manual16 = recipeLike.getRecipe().getManual16();
        this.manualImg16 = recipeLike.getRecipe().getManualImg16();
        this.manual17 = recipeLike.getRecipe().getManual17();
        this.manualImg17 = recipeLike.getRecipe().getManualImg17();
        this.manual18 = recipeLike.getRecipe().getManual18();
        this.manualImg18 = recipeLike.getRecipe().getManualImg18();
        this.manual19 = recipeLike.getRecipe().getManual19();
        this.manualImg19 = recipeLike.getRecipe().getManualImg19();
        this.manual20 = recipeLike.getRecipe().getManual20();
        this.manualImg20 = recipeLike.getRecipe().getManualImg20();
        this.rcpNaTip = recipeLike.getRecipe().getRcpNaTip();
        this.reviewAverge = recipeLike.getRecipe().getAverageRating();
    }
}
