package doubleni.mealrecipe.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "recipe")
@Entity
public class Recipe {
    // 식약처 공공 데이터 레시피
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rcp_id", nullable = false)
    private Long rcpId;

    @Column(name = "rcp_seq", nullable = false)
    private String rcpSeq; // 일련 번호

    @Column(name = "rcp_nm", nullable = false)
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

    @ElementCollection
    @Column(name = "rcp_parts_dtls")
    private List<String> rcpPartsDtls; // 재료 정보
//    private List<String> rcpPartsDtls = new ArrayList<>();

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

    @Column(name = "rcp_na_tip")
    private String rcpNaTip;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recipe")
//    @PrimaryKeyJoinColumn
//    private RcpPartsDtls rcpPartsDtls;


//    @Builder
//    public Recipe(Long rcpId, String rcpSeq, String rcpNm, String rcpWay2, String rcpPat2,
//                  String infoWgt, String infoEng, String infoCar, String infoPro, String infoFat, String infoNa,
//                  List<String> rcpPartsDtls, String manual01, String manualImg01, String manual02, String manualImg02) {
//        this.rcpId = rcpId;
//        this.rcpSeq = rcpSeq;
//        this.rcpNm = rcpNm;
//        this.rcpWay2 = rcpWay2;
//        this.rcpPat2 = rcpPat2;
//        this.infoWgt = infoWgt;
//        this.infoEng = infoEng;
//        this.infoCar = infoCar;
//        this.infoPro = infoPro;
//        this.infoFat = infoFat;
//        this.infoNa = infoNa;
//        this.rcpPartsDtls = rcpPartsDtls;
//        this.manual01 = manual01;
//        this.manualImg01 = manualImg01;
//        this.manual02 = manual02;
//        this.manualImg02 = manualImg02;
//}



}