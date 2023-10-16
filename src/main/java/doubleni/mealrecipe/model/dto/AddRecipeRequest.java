package doubleni.mealrecipe.model.dto;

import doubleni.mealrecipe.model.entity.UploadImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddRecipeRequest {

    private String rcpSeq; // 일련 번호
    private String rcpNm; // 레시피명
    private String rcpWay2; // 조리 방법
    private String rcpPat2; // 요리 종류
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
    private String manual01; // 레시피 설명
    private String manualImg01; // 레시피 이미지


    public Recipe toEntity() { // 생성자를 사용해 객체 생성
        return Recipe.builder()
                .rcpSeq(rcpSeq)
                .rcpNm(rcpNm)
                .rcpWay2(rcpWay2)
                .rcpPat2(rcpPat2)
                .manual01(manual01)
                .manualImg01(manualImg01)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
