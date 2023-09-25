package doubleni.mealrecipe.model.dto;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
public class RecipeResponse {
    private final String rcp_seq; // 일련 번호
    private final String rcp_nm; // 레시피명
    private final String rcp_way2; // 조리 방법
    private final String rcp_pat2; // 요리 종류
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
    private final String manual01; // 레시피 설명
    private final String manual_img01; // 레시피 이미지
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


    public RecipeResponse(Recipe recipe) {
        this.rcp_seq = recipe.getRcp_seq();
        this.rcp_nm = recipe.getRcp_nm();
        this.rcp_way2 = recipe.getRcp_way2();
        this.rcp_pat2 = recipe.getRcp_pat2();
        this.manual01 = recipe.getManual01();
        this.manual_img01 = recipe.getManual_img01();
    }

}
