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
@Table(name = "recipes")
@Entity
public class Recipe {
    // 식약처 공공 데이터 레시피
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rcp_id")
    private Long rcpId;

    private String rcpSeq; // 일련 번호
    @Column(name = "rcp_nm")
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

//    @ElementCollection
//    private List<String> rcpPartsDtls;

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

//    // 일대다 관계 설정: 한 개의 Recipe가 여러 개의 Review를 가질 수 있음
//    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
//    private List<Review> reviews;  // Recipe에 대한 리뷰 목록

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 작성자

    // 다대일 관계 설정: 한 개의 Recipe는 여러 개의 Like를 가질 수 있음
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeLike> likes; // 레시피에 달린 좋아요 목록
    private Integer likeCnt; // 레시피 좋아요 수

    public void likeChange(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @Column(name = "review_averge")
    private double reviewAverge;


    public double getAverageRating() {
        return Math.round(reviews.stream()
                .mapToDouble(Review::getReviewRating)
                .average()
                .orElse(0.0) * 10) / 10.0;
    }



}