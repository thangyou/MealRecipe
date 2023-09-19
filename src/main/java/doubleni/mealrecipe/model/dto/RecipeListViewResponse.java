package doubleni.mealrecipe.model.dto;

import lombok.Getter;

@Getter
public class RecipeListViewResponse {
    private Long id;

    private String rcp_seq; // 일련 번호
    private String rcp_nm; // 레시피명
    private String rcp_way2; // 조리 방법
    private String rcp_pat2; // 요리 종류
    private String info_wgt; // 중량(1인분)
    private String info_eng; // 열량
    private String info_car; // 탄수화물
    private String info_pro; // 단백질
    private String info_fat; // 지방
    private String info_na; // 나트륨
    private String hash_tag; // 해시태그
    private String att_file_no_main; // 이미지 경로(소)
    private String att_file_no_mk; // 이미지 경로(대)
    private String rcp_parts_dtls; // 재료 정보
    private String manual01; // 레시피 설명
    private String manual_img01; // 레시피 이미지

    public RecipeListViewResponse(Recipe recipe) {
        this.id = recipe.getId();
        this.rcp_seq = recipe.getRcp_seq();
        this.rcp_nm = recipe.getRcp_nm();
        this.rcp_way2 = recipe.getRcp_way2();
        this.rcp_pat2 = recipe.getRcp_pat2();
//        this.info_wgt = recipe.getInfo_wgt();
//        this.info_eng = recipe.getInfo_eng();
//        this.info_car = recipe.getInfo_car();
//        this.info_pro = recipe.getInfo_pro();
//        this.info_fat = recipe.getInfo_fat();
//        this.info_na = recipe.getInfo_na();
//        this.hash_tag = recipe.getHash_tag();
//        this.att_file_no_main = recipe.getAtt_file_no_main();
//        this.att_file_no_mk = recipe.getAtt_file_no_mk();
//        this.rcp_parts_dtls = recipe.getRcp_parts_dtls();
        this.manual01 = recipe.getManual01();
        this.manual_img01 = recipe.getManual_img01();
    }

}
