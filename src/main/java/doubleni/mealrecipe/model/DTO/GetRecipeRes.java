package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRecipeRes {
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

//    private int infoEng; // 열량
//    private int infoCar; // 탄수화물
//    private int infoPro; // 단백질
//    private int infoFat; // 지방
//    private int infoNa; // 나트륨

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

    private double reviewAverge;

    public GetRecipeRes(Recipe r) {
        this.rcpId = r.getRcpId();
        this.rcpSeq = r.getRcpSeq();
        this.rcpNm = r.getRcpNm();
        this.rcpWay2 = r.getRcpWay2();
        this.rcpPat2 = r.getRcpPat2();
        this.infoWgt = r.getInfoWgt();
        this.infoCar = r.getInfoCar();
        this.infoEng = r.getInfoEng();
        this.infoPro = r.getInfoPro();
        this.infoFat = r.getInfoFat();
        this.infoNa = r.getInfoNa();
        this.hashTag = r.getHashTag();
        this.attFileNoMain = r.getAttFileNoMain();
        this.attFileNoMk = r.getAttFileNoMk();
        this.rcpPartsDtls = r.getRcpPartsDtls();
        this.manual01 = r.getManual01();
        this.manualImg01 = r.getManualImg01();
        this.manual02 = r.getManual02();
        this.manualImg02 = r.getManualImg02();
        this.manual03 = r.getManual03();
        this.manualImg03 = r.getManualImg03();
        this.manual04 = r.getManual04();
        this.manualImg04 = r.getManualImg04();
        this.manual05 = r.getManual05();
        this.manualImg05 = r.getManualImg05();
        this.manual06 = r.getManual06();
        this.manualImg06 = r.getManualImg06();
        this.manual07 = r.getManual07();
        this.manualImg07 = r.getManualImg07();
        this.manual08 = r.getManual08();
        this.manualImg08 = r.getManualImg08();
        this.manual09 = r.getManual09();
        this.manualImg09 = r.getManualImg09();
        this.manual10 = r.getManual10();
        this.manualImg10 = r.getManualImg10();
        this.manual11 = r.getManual11();
        this.manualImg11 = r.getManualImg11();
        this.manual12 = r.getManual12();
        this.manualImg12 = r.getManualImg12();
        this.manual13 = r.getManual13();
        this.manualImg13 = r.getManualImg13();
        this.manual14 = r.getManual14();
        this.manualImg14 = r.getManualImg14();
        this.manual15 = r.getManual15();
        this.manualImg15 = r.getManualImg15();
        this.manual16 = r.getManual16();
        this.manualImg16 = r.getManualImg16();
        this.manual17 = r.getManual17();
        this.manualImg17 = r.getManualImg17();
        this.manual18 = r.getManual18();
        this.manualImg18 = r.getManualImg18();
        this.manual19 = r.getManual19();
        this.manualImg19 = r.getManualImg19();
        this.manual20 = r.getManual20();
        this.manualImg20 = r.getManualImg20();
        this.rcpNaTip = r.getRcpNaTip();
        this.reviewAverge = r.getReviewAverge();
    }





}
