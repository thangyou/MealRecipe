package doubleni.mealrecipe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recipe")
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

    @Column(nullable = true)
    private String rcpWay2; // 조리 방법
    private String rcpPat2; // 요리 종류

    private String infoWgt; // 중량(1인분)
    private String infoEng; // 열량
    private String infoCar; // 탄수화물
    private String infoPro; // 단백질
    private String infoFat; // 지방
    private String infoNa; // 나트륨
//    private double info_wgt; // 중량(1인분)
//    private double info_eng; // 열량
//    private double info_car; // 탄수화물
//    private double info_pro; // 단백질
//    private double info_fat; // 지방
//    private double info_na; // 나트륨

    private String hash_tag; // 해시태그
    private String att_file_no_main; // 이미지 경로(소)
    private String att_file_no_mk; // 이미지 경로(대)
    private String rcp_parts_dtls; // 재료 정보
    private String manual01; // 레시피 설명
    private String manual_img01; // 레시피 이미지
    private String manual02;
    private String manual_img02;
    private String manual03;
    private String manual_img03;
    private String manual04;
    private String manual_img04;
    private String manual05;
    private String manual_img05;
    private String manual06;
    private String manual_img06;
    private String manual07;
    private String manual_img07;
    private String manual08;
    private String manual_img08;
    private String manual09;
    private String manual_img09;
    private String manual10;
    private String manual_img10;
    private String manual11;
    private String manual_img11;
    private String manual12;
    private String manual_img12;
    private String manual13;
    private String manual_img13;
    private String manual14;
    private String manual_img14;
    private String manual15;
    private String manual_img15;
    private String manual16;
    private String manual_img16;
    private String manual17;
    private String manual_img17;
    private String manual18;
    private String manual_img18;
    private String manual19;
    private String manual_img19;
    private String manual20;
    private String manual_img20;
    private String rcp_na_tip;

}