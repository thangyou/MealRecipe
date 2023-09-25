package doubleni.mealrecipe.model.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * @Entity : 실제 DataBase의 테이블과 1 : 1로 Mapping 되는 Class
 * DB의 테이블내에 존재하는 컬럼만을 속성(필드)으로 가져야 한다.
 * 구현 method는 주로 Service Layer에서 사용한다.
 * Entity를 Persistence Tier에서 받아와 DTO로 변환하여
 * Presentation Tier에 전달하는 것이 Business Tier, Service 단에서의 역할
 *
 * Entity, DTO Class 분리 이유
 * Entity와 DTO를 분리해서 관리해야 하는 이유는 DB Layer와 View Layer 사이의 역할을 분리 하기 위해서다.
 * DB Layer = Persistence Tier, View Layer = Presentation Tier
 * Entity는 실제 테이블과 매핑되어 만일 변경되게 되면 여러 다른 Class에 영향을 끼치고,
 * DTO는 View와 통신하며 자주 변경되므로 분리 해주어야 한다.
 * 결국 DTO는 Domain Model 객체(Entity)를 그대로 두고 복사하여,
 * 다양한 Presentation Logic을 추가한 정도로 사용하며 Domain Model 객체(Entity)는 Persistent만을 위해서 사용해야한다.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "recipe")
@ToString
public class Recipe {
    // Data Transfer Object
    // getter/setter 메서드만 가진 클래스
    // DAO에서 데이터를 얻어 Service / Controller 등으로 보냄


    // 1. 필드 선언 - private
    // 레시피 = 메뉴
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rcp_seq", nullable = false)
    private String rcp_seq; // 일련 번호

    @Column(name = "rcp_nm", nullable = false)
    private String rcp_nm; // 레시피명

    @Column(name="rcp_way2", nullable = false)
    private String rcp_way2; // 조리 방법

    @Column(name="rcp_pat2", nullable = false)
    private String rcp_pat2; // 요리 종류
//    private String info_wgt; // 중량(1인분)
//    private String info_eng; // 열량
//    private String info_car; // 탄수화물
//    private String info_pro; // 단백질
//    private String info_fat; // 지방
//    private String info_na; // 나트륨
//    private String hash_tag; // 해시태그
//    private String att_file_no_main; // 이미지 경로(소)
//    private String att_file_no_mk; // 이미지 경로(대)
//    private String rcp_parts_dtls; // 재료 정보
    @Column(name="manual01")
    private String manual01; // 레시피 설명
    @Column(name="manual_img01")
    private String manual_img01; // 레시피 이미지
//    private String manual02;
//    private String manual_img02;
//    private String manual03;
//    private String manual_img03;
//    private String manual04;
//    private String manual_img04;
//    private String manual05;
//    private String manual_img05;
//    private String manual06;
//    private String manual_img06;
//    private String manual07;
//    private String manual_img07;
//    private String manual08;
//    private String manual_img08;
//    private String manual09;
//    private String manual_img09;
//    private String manual10;
//    private String manual_img10;
//    private String manual11;
//    private String manual_img11;
//    private String manual12;
//    private String manual_img12;
//    private String manual13;
//    private String manual_img13;
//    private String manual14;
//    private String manual_img14;
//    private String manual15;
//    private String manual_img15;
//    private String manual16;
//    private String manual_img16;
//    private String manual17;
//    private String manual_img17;
//    private String manual18;
//    private String manual_img18;
//    private String manual19;
//    private String manual_img19;
//    private String manual20;
//    private String manual_img20;
//    private boolean rcp_delete_yn; // 레시피 삭제 확인 (default = 0)

//    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;

//    // 페이징 *************************************************
//    private int pageSize;
//    private int groupSize;
//    private int curPage;
//    private int totalCount;
//
//    // 조건 검색 ***********************************************
//    private String keyfilter;
//    private String keyword;
//    private String startdate;
//    private String enddate;
//
//    // 추천 **************************************************
//    private String rcpHits;

    //******************************************************

    @Builder
    public Recipe(String rcp_seq, String rcp_nm, String rcp_way2, String rcp_pat2,
                  String manual01, String manual_img01) {
        this.rcp_seq = rcp_seq;
        this.rcp_nm = rcp_nm;
        this.rcp_way2 = rcp_way2;
        this.rcp_pat2 = rcp_pat2;
        this.manual01 = manual01;
        this.manual_img01 = manual_img01;
    }


//    public void update(String rcpNm, String rcpWay2, String rcpPat2,
//                       String infoWgt, String hashTag, String attFileNoMain, String attFileNoMk,
//                       String rcpPartsDtls, String manual01, String manualImg01) {
//        this.rcp_nm = rcpNm;
//        this.rcp_way2 = rcpWay2;
//        this.rcp_pat2 = rcpPat2;
//        this.info_wgt = infoWgt;
//        this.hash_tag = hashTag;
//        this.att_file_no_main = attFileNoMain;
//        this.att_file_no_mk = attFileNoMk;
//        this.rcp_parts_dtls = rcpPartsDtls;
//        this.manual01 = manual01;
//        this.manual_img01 = manual_img01;
//    }
    public void update(String rcp_seq, String rcp_nm, String rcp_way2, String rcp_pat2,
                       String manual01, String manual_img01) {
        this.rcp_seq = rcp_seq;
        this.rcp_nm = rcp_nm;
        this.rcp_way2 = rcp_way2;
        this.rcp_pat2 = rcp_pat2;
        this.manual01 = manual01;
        this.manual_img01 = manual_img01;
    }
}